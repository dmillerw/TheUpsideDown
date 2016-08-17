package me.dmillerw.upsidedown.asm.transform;

import me.dmillerw.upsidedown.asm.util.ASMUtils;
import me.dmillerw.upsidedown.asm.util.MappingDatabase;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;

import java.util.Iterator;

import static org.objectweb.asm.Opcodes.*;

/**
 * Created by dmillerw
 */
public class TransformWorld implements ITransformer {

    @Override
    public String[] getClasses() {
        return new String[] {"net.minecraft.world.World"};
    }

    @Override
    public byte[] transform(String name, byte[] data) {
        ClassReader classReader = new ClassReader(data);
        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, 0);

        CoreTransformer.info("Found World.class, starting to patch");

        MethodNode target = null;
        for (MethodNode methodNode : classNode.methods) {
            if (methodNode.name.equals(MappingDatabase.getMethod("getSunBrightness"))) {
                target = methodNode;
            }
        }

        if (target != null) {
            CoreTransformer.info("Found getSunBrightness method. Inserting event dispatch");

            AbstractInsnNode insertionTargetAload = null;
            AbstractInsnNode insertionTargetMethod = null;
            Iterator<AbstractInsnNode> iterator = target.instructions.iterator();
            while(iterator.hasNext()) {
                AbstractInsnNode node = iterator.next();
                if (insertionTargetAload == null && node.getOpcode() == ALOAD && ((VarInsnNode)node).var == 0) {
                    insertionTargetAload = node;
                }

                if (insertionTargetMethod == null && node.getOpcode() == INVOKEVIRTUAL && ((MethodInsnNode)node).desc.equals("(F)F")) {
                    insertionTargetMethod = node;
                }
            }

            if (insertionTargetAload != null) {
                target.instructions.insert(insertionTargetAload, new VarInsnNode(ALOAD, 0));
                target.instructions.insert(insertionTargetAload, new VarInsnNode(FLOAD, 1));
            }

            if (insertionTargetMethod != null) {
                InsnList list = new InsnList();
                list.add(new MethodInsnNode(
                        INVOKESTATIC,
                        "me/dmillerw/upsidedown/asm/event/EventDispatcher",
                        "getSunBrightness",
                        ASMUtils.buildDescription(MappingDatabase.getClass("net/minecraft/world/World"), "F", "F", "F"),
                        false
                ));
                target.instructions.insert(insertionTargetMethod, list);
            }
        }

        ClassWriter classWriter = new ClassWriter(0);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}
