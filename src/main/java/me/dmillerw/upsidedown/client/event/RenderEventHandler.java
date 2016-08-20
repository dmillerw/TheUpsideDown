package me.dmillerw.upsidedown.client.event;

import me.dmillerw.upsidedown.asm.event.SunBrightnessEvent;
import me.dmillerw.upsidedown.asm.event.UpdateLightmapEvent;
import me.dmillerw.upsidedown.client.particle.ParticleSpeck;
import me.dmillerw.upsidedown.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * Created by dmillerw
 */
public class RenderEventHandler {

    @SubscribeEvent
    public void sunBrightness(SunBrightnessEvent event) {
        float brightness = event.getBrightness();
        if (ClientProxy.inUpsideDown)
            event.setBrightness(brightness * ClientProxy.atmosphericState.lightingIntensity);
    }

    @SubscribeEvent
    public void updateLightmap(UpdateLightmapEvent event) {
        if (ClientProxy.inUpsideDown) {
            event.setRed((int)((float)event.getRed() * ClientProxy.atmosphericState.lightingRed));
            event.setGreen((int)((float)event.getGreen() * ClientProxy.atmosphericState.lightingGreen));
            event.setBlue((int)((float)event.getBlue() * ClientProxy.atmosphericState.lightingBlue));
        }
    }

    @SubscribeEvent
    public void fogDensity(EntityViewRenderEvent.FogDensity event) {
        if (ClientProxy.inUpsideDown) {
            GlStateManager.setFog(GlStateManager.FogMode.EXP);
            event.setDensity(ClientProxy.atmosphericState.fogDensity);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void fogColor(EntityViewRenderEvent.FogColors event) {
        event.setRed(ClientProxy.atmosphericState.fogRed);
        event.setGreen(ClientProxy.atmosphericState.fogGreen);
        event.setBlue(ClientProxy.atmosphericState.fogBlue);

//        event.setRed(0.71F);
//        event.setGreen(0.71F);
//        event.setBlue(0.85F);
    }

    private BlockPos.MutableBlockPos tempPos = new BlockPos.MutableBlockPos();

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if (event.phase != TickEvent.Phase.START)
            return;

        if (!ClientProxy.inUpsideDown)
            return;

        Minecraft mc = Minecraft.getMinecraft();
        World world = mc.theWorld;
        EntityPlayer player = mc.thePlayer;
        if (world != null) {
            final int MAX_PARTICLES = 500;

            if (ParticleSpeck.count < MAX_PARTICLES) {
                for (int i=0; i<100; i++) {
                    int x = (int)player.posX + (world.rand.nextBoolean() ? world.rand.nextInt(8) : -world.rand.nextInt(8));
                    int y = (int)player.posY + (world.rand.nextBoolean() ? world.rand.nextInt(4) : -world.rand.nextInt(4));
                    int z = (int)player.posZ + (world.rand.nextBoolean() ? world.rand.nextInt(8) : -world.rand.nextInt(8));

                    if (!world.isAirBlock(tempPos.setPos(x, y, z)))
                        continue;

                    float fx = world.rand.nextBoolean() ? (0.0125F * world.rand.nextFloat()) : (-0.0125F * world.rand.nextFloat());
                    float fy = world.rand.nextBoolean() ? (0.0125F * world.rand.nextFloat()) : (-0.0125F * world.rand.nextFloat());
                    float fz = world.rand.nextBoolean() ? (0.0125F * world.rand.nextFloat()) : (-0.0125F * world.rand.nextFloat());

                    mc.effectRenderer.addEffect(new ParticleSpeck(world, x, y, z, fx, fy, fz));
                }
            }
        }
    }
}
