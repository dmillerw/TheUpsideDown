package me.dmillerw.upsidedown.asm.transform;

import me.dmillerw.upsidedown.asm.util.MappingDatabase;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;

import java.util.Iterator;

import static org.objectweb.asm.Opcodes.*;

/**
 * Created by dmillerw
 */
public class TransformEntityRenderer implements ITransformer {

    @Override
    public String[] getClasses() {
        return new String[]{"net.minecraft.client.renderer.EntityRenderer"};
    }

    @Override
    public byte[] transform(String name, byte[] data) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(data);
        classReader.accept(classNode, 0);

        CoreTransformer.info("Found EntityRenderer.class, starting to patch");

        MethodNode updateLightmap = null;
        for (MethodNode node : classNode.methods) {
            if (node.name.equals(MappingDatabase.getMethod("updateLightmap"))) {
                updateLightmap = node;
                break;
            }
        }

        if (updateLightmap != null) {
            CoreTransformer.info("Found updateLightmap method. Inserting method calls");

            boolean insertedHook = false;

            Iterator<AbstractInsnNode> iterator = updateLightmap.instructions.iterator();
            while (iterator.hasNext()) {
                AbstractInsnNode node = iterator.next();

                if (node instanceof VarInsnNode && !insertedHook) {
                    if (node.getOpcode() == ISTORE && ((VarInsnNode) node).var == 22) {
                        InsnList list = new InsnList();

                        list.add(new VarInsnNode(ILOAD, 5));
                        list.add(new VarInsnNode(ILOAD, 20));
                        list.add(new VarInsnNode(ILOAD, 21));
                        list.add(new VarInsnNode(ILOAD, 22));

                        list.add(new MethodInsnNode(
                                INVOKESTATIC,
                                "me/dmillerw/upsidedown/asm/event/EventDispatcher",
                                "updateLightmap",
                                "(IIII)Lme/dmillerw/upsidedown/asm/event/UpdateLightmapEvent;",
                                false
                        ));

                        list.add(new VarInsnNode(ASTORE, 23));

                        // LOAD RED
                        list.add(new VarInsnNode(ALOAD, 23));
                        list.add(new MethodInsnNode(
                                INVOKEVIRTUAL,
                                "me/dmillerw/upsidedown/asm/event/UpdateLightmapEvent",
                                "getRed",
                                "()I",
                                false
                        ));
                        list.add(new VarInsnNode(ISTORE, 20));

                        // LOAD GREEN
                        list.add(new VarInsnNode(ALOAD, 23));
                        list.add(new MethodInsnNode(
                                INVOKEVIRTUAL,
                                "me/dmillerw/upsidedown/asm/event/UpdateLightmapEvent",
                                "getGreen",
                                "()I",
                                false
                        ));
                        list.add(new VarInsnNode(ISTORE, 21));

                        // LOAD BLUE
                        list.add(new VarInsnNode(ALOAD, 23));
                        list.add(new MethodInsnNode(
                                INVOKEVIRTUAL,
                                "me/dmillerw/upsidedown/asm/event/UpdateLightmapEvent",
                                "getBlue",
                                "()I",
                                false
                        ));
                        list.add(new VarInsnNode(ISTORE, 22));

                        updateLightmap.instructions.insert(node, list);
                        insertedHook = true;
                    }
                }
            }
        }

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classNode.accept(writer);

        return writer.toByteArray();
    }
}
