package me.dmillerw.upsidedown.asm.util;

import com.google.common.collect.Maps;
import me.dmillerw.upsidedown.asm.CoreLoader;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;

import java.util.Map;

/**
 * Created by dmillerw
 */
public class Mappings {

    /* THIS MAPPING DATABASE HAS BEEN BASED OFF OF 'snapshot_20160518' */

    private static Map<String, String> methodMappings = Maps.newHashMap();
    private static Map<String, String> fieldMappings = Maps.newHashMap();

    static {
        methodMappings.put("func_72971_b", "getSunBrightness");
        methodMappings.put("func_78472_g", "updateLightmap");
        methodMappings.put("func_184389_a", "getModelForState"); // BlockModelShapes
        methodMappings.put("func_174954_c", "getModelForState"); // BlockRendererDispatcher

        fieldMappings.put("field_175028_a", "blockModelShapes");
    }

    public static String getMethod(String obf) {
        if (CoreLoader.obfuscated)
            return obf;

        if (!methodMappings.containsKey(obf)) {
            throw new IllegalStateException("Cannot find util for method '" + obf + "'!");
        }

        return methodMappings.get(obf);
    }

    public static String getField(String obf) {
        if (CoreLoader.obfuscated)
            return obf;

        if (!fieldMappings.containsKey(obf)) {
            throw new IllegalStateException("Cannot find util for method '" + obf + "'!");
        }
        return fieldMappings.get(obf);
    }

    public static String getClass(String deobf) {
        if (!CoreLoader.obfuscated)
            return deobf;

        return FMLDeobfuscatingRemapper.INSTANCE.map(deobf);
    }
}
