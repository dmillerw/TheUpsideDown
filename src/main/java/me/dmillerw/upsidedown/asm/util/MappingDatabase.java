package me.dmillerw.upsidedown.asm.util;

import com.google.common.collect.Maps;
import me.dmillerw.upsidedown.asm.CoreLoader;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;

import java.util.Map;

/**
 * Created by dmillerw
 */
public class MappingDatabase {

    /* THIS MAPPING DATABASE HAS BEEN BASED OFF OF 'snapshot_20160518' */

    private static Map<String, String> methodMappings = Maps.newHashMap();
    private static Map<String, String> fieldMappings = Maps.newHashMap();

    static {
        methodMappings.put("getSunBrightness", "func_72971_b");
        methodMappings.put("updateLightmap", "func_78472_g");
    }

    public static String getMethod(String deobf) {
        if (!CoreLoader.obfuscated)
            return deobf;

        if (!methodMappings.containsKey(deobf)) {
            throw new IllegalStateException("Cannot find util for method '" + deobf + "'!");
        }
        return methodMappings.get(deobf);
    }

    public static String getField(String deobf) {
        if (!CoreLoader.obfuscated)
            return deobf;

        if (!fieldMappings.containsKey(deobf)) {
            throw new IllegalStateException("Cannot find util for method '" + deobf + "'!");
        }
        return fieldMappings.get(deobf);
    }

    public static String getClass(String deobf) {
        if (!CoreLoader.obfuscated)
            return deobf;

        return FMLDeobfuscatingRemapper.INSTANCE.map(deobf);
    }
}
