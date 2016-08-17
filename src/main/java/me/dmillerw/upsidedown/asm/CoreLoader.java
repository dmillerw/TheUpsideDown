package me.dmillerw.upsidedown.asm;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

/**
 * Created by dmillerw
 */
@IFMLLoadingPlugin.SortingIndex(1001)
public class CoreLoader implements IFMLLoadingPlugin {

    public static boolean obfuscated = true;

    @Override
    public String[] getASMTransformerClass() {
        return new String[] {"me.dmillerw.upsidedown.asm.transform.CoreTransformer"};
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        CoreLoader.obfuscated = (Boolean) data.get("runtimeDeobfuscationEnabled");
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
