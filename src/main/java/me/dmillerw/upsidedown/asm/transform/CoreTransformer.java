package me.dmillerw.upsidedown.asm.transform;

import com.google.common.collect.Lists;
import me.dmillerw.upsidedown.lib.ModInfo;
import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Created by dmillerw
 */
public class CoreTransformer implements IClassTransformer {

    public static final Logger LOGGER = LogManager.getLogger(ModInfo.ID);
    public static void info(String msg) {
        LOGGER.info("[" + ModInfo.NAME + " - ASM]: " + msg);
    }

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
