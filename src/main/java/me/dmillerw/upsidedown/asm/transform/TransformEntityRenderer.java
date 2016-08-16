package me.dmillerw.upsidedown.asm.transform;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;

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

        MethodNode updateLightmap = null;
        for (MethodNode node : classNode.methods) {
            if (node.name.equals("updateLightmap")) {
                updateLightmap = node;
                break;
            }
        }

        if (updateLightmap != null) {
            boolean insertedHook = false;
            for (int i = 0; i < updateLightmap.instructions.size(); i++) {
                AbstractInsnNode an = updateLightmap.instructions.get(i);
                if (an instanceof VarInsnNode && !insertedHook) {
                    VarInsnNode iin = (VarInsnNode) an;
                    if (iin.getOpcode() == ISTORE && iin.var == 22) {
                        InsnList list = new InsnList();

                        /* RED */
//                        list.add(new FieldInsnNode(
//                                GETSTATIC,
//                                "me/dmillerw/upsidedown/asm/redirect/StaticMethodHandler$LightmapColor",
//                                "RED",
//                                "me/dmillerw/upsidedown/asm/redirect/StaticMethodHandler$LightmapColor"
//                        ));
                        list.add(new InsnNode(ICONST_0));
                        list.add(new VarInsnNode(ILOAD, 5));
                        list.add(new VarInsnNode(ILOAD, 20));
                        list.add(new MethodInsnNode(
                                INVOKESTATIC,
                                "me/dmillerw/upsidedown/asm/redirect/StaticMethodHandler",
                                "modifyLightmap",
//                                "(Lme/dmillerw/upsidedown/asm/redirect/StaticMethodHandler$LightmapColor;II)I",
                                "(III)I",
                                false
                        ));
                        list.add(new VarInsnNode(ISTORE, 20));

                        /* GREEN */
//                        list.add(new FieldInsnNode(
//                                GETSTATIC,
//                                "me/dmillerw/upsidedown/asm/redirect/StaticMethodHandler$LightmapColor",
//                                "GREEN",
//                                "me/dmillerw/upsidedown/asm/redirect/StaticMethodHandler$LightmapColor"
//                        ));
                        list.add(new InsnNode(ICONST_1));
                        list.add(new VarInsnNode(ILOAD, 5));
                        list.add(new VarInsnNode(ILOAD, 21));
                        list.add(new MethodInsnNode(
                                INVOKESTATIC,
                                "me/dmillerw/upsidedown/asm/redirect/StaticMethodHandler",
                                "modifyLightmap",
//                                "(Lme/dmillerw/upsidedown/asm/redirect/StaticMethodHandler$LightmapColor;II)I",
                                "(III)I",
                                false
                        ));
                        list.add(new VarInsnNode(ISTORE, 21));

                        /* BLUE */
//                        list.add(new FieldInsnNode(
//                                GETSTATIC,
//                                "me/dmillerw/upsidedown/asm/redirect/StaticMethodHandler$LightmapColor",
//                                "BLUE",
//                                "me/dmillerw/upsidedown/asm/redirect/StaticMethodHandler$LightmapColor"
//                        ));
                        list.add(new InsnNode(ICONST_2));
                        list.add(new VarInsnNode(ILOAD, 5));
                        list.add(new VarInsnNode(ILOAD, 22));
                        list.add(new MethodInsnNode(
                                INVOKESTATIC,
                                "me/dmillerw/upsidedown/asm/redirect/StaticMethodHandler",
                                "modifyLightmap",
//                                "(Lme/dmillerw/upsidedown/asm/redirect/StaticMethodHandler$LightmapColor;II)I",
                                "(III)I",
                                false
                        ));
                        list.add(new VarInsnNode(ISTORE, 22));

                        updateLightmap.instructions.insert(iin, list);
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
