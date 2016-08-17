package me.dmillerw.upsidedown.asm.event;

import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * Created by dmillerw
 */
public class UpdateLightmapEvent extends Event {

    private final int arrayPosition;

    private int red;
    private int green;
    private int blue;

    public UpdateLightmapEvent(int arrayPosition, int red, int green, int blue) {
        this.arrayPosition = arrayPosition;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getArrayPosition() {
        return arrayPosition;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }
}
