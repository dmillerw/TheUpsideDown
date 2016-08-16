package me.dmillerw.upsidedown.asm.redirect;

import net.minecraft.world.World;

/**
 * Created by dmillerw
 */
public class StaticMethodHandler {

    public static float getSunBrightness(World world, float f) {
        return world.provider.getSunBrightness(f);
    }
}
