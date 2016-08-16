package me.dmillerw.upsidedown.asm.redirect;

import me.dmillerw.upsidedown.proxy.ClientProxy;
import net.minecraft.world.World;

/**
 * Created by dmillerw
 */
public class StaticMethodHandler {

    public static enum LightmapColor {
        RED, GREEN, BLUE;
    }

    public static float getSunBrightness(World world, float f) {
        if (ClientProxy.inUpsideDown) {
            return world.provider.getSunBrightness(f) / 2;
        } else {
            return world.provider.getSunBrightness(f);
        }
    }

    public static int modifyLightmap(int color, int position, int originalValue) {
        if (ClientProxy.lightR == -1 || ClientProxy.lightG == -1 || ClientProxy.lightB == -1)
            return originalValue;

        if (ClientProxy.inUpsideDown) {
            switch (color) {
                case 2: return (int)((float)originalValue * ClientProxy.lightB);
                case 1: return (int)((float)originalValue * ClientProxy.lightG);
                case 0:
                default: return (int)((float)originalValue * ClientProxy.lightR);
            }
        } else {
            return originalValue;
        }

//        if (ClientProxy.inUpsideDown) {
//            return originalValue / 2 + (color == 2 ? 25 : 0);
//        } else {
//            return originalValue;
//        }
    }
}
