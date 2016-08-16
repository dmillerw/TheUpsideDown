package me.dmillerw.upsidedown.asm.transform;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

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

        MethodNode target = null;
        for (MethodNode methodNode : classNode.methods) {
            if (methodNode.name.equals("getSunBrightness")) {
                target = methodNode;
            }
        }

        if (target != null) {
            target.instructions.clear();

            target.instructions.add(new VarInsnNode(ALOAD, 0));
            target.instructions.add(new VarInsnNode(FLOAD, 1));

            target.instructions.add(new MethodInsnNode(
                    INVOKESTATIC,
                    "me/dmillerw/upsidedown/asm/redirect/StaticMethodHandler",
                    "getSunBrightness",
                    "(Lnet/minecraft/world/World;F)F",
                    false
            ));

            target.instructions.add(new InsnNode(FRETURN));
        }

        ClassWriter classWriter = new ClassWriter(0);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}
