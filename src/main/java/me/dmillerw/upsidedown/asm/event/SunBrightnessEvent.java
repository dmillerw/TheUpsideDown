package me.dmillerw.upsidedown.asm.event;

import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;

/**
 * Created by dmillerw
 */
public class SunBrightnessEvent extends WorldEvent {

    private final float partialTicks;
    private float brightness;

    public SunBrightnessEvent(World world, float partialTicks, float brightness) {
        super(world);

        this.partialTicks = partialTicks;
        this.brightness = brightness;
    }

    public float getPartialTicks() {
        return partialTicks;
    }

    public float getBrightness() {
        return brightness;
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;
    }
}
