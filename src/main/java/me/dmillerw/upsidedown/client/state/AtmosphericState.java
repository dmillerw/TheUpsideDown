package me.dmillerw.upsidedown.client.state;

/**
 * Created by dmillerw
 */
public class AtmosphericState {

    public float fogRed = 1F;
    public float fogGreen = 1F;
    public float fogBlue = 1F;

    public float fogDensity = 0.01F;

    public float lightingRed = 1F;
    public float lightingGreen = 1F;
    public float lightingBlue = 1F;

    public float lightingIntensity = 1F;

    public float particleSize = 0.01F;
    public int particleMaxLifespan = 32;
    public int particleMaxDistance = 8;
    public int particleMaxAmount = 500;
    public int particleSpawnPerTick = 100;
}
