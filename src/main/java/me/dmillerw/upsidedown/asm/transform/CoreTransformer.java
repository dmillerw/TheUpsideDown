package me.dmillerw.upsidedown.asm.transform;

import com.google.common.collect.Lists;
import net.minecraft.launchwrapper.IClassTransformer;

import java.util.List;

/**
 * Created by dmillerw
 */
public class CoreTransformer implements IClassTransformer {

    private static List<ITransformer> transformers = Lists.newArrayList();
    static {
        transformers.add(new TransformWorld());
        transformers.add(new TransformEntityRenderer());
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] data) {
        for (ITransformer transformer : transformers) {
            for (String clazz : transformer.getClasses()) {
                if (transformedName.equals(clazz)) {
                    return transformer.transform(transformedName, data);
                }
            }
        }
        return data;
    }
}
