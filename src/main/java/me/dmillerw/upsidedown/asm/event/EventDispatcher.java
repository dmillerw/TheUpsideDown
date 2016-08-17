package me.dmillerw.upsidedown.asm.event;

import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created by dmillerw
 */
public class EventDispatcher {

    public static float getSunBrightness(World world, float partialTicks, float brightness) {
        SunBrightnessEvent event = new SunBrightnessEvent(world, partialTicks, brightness);
        MinecraftForge.EVENT_BUS.post(event);
        return event.getBrightness();
    }

    public static UpdateLightmapEvent updateLightmap(int position, int red, int green, int blue) {
        UpdateLightmapEvent event = new UpdateLightmapEvent(position, red, green, blue);
        MinecraftForge.EVENT_BUS.post(event);
        return event;
    }

    private static void test() {
        int a = 1;
        int b = 2;
        int v = 3;
        int d = 4;

        UpdateLightmapEvent event = updateLightmap(a, b, v, d);

        a = event.getRed();
        b = event.getGreen();
    }
}
