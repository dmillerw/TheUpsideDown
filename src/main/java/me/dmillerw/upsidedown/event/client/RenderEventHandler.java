package me.dmillerw.upsidedown.event.client;

import me.dmillerw.upsidedown.client.particle.ParticleSpeck;
import me.dmillerw.upsidedown.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * Created by dmillerw
 */
public class RenderEventHandler {

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
            final int MAX_PARTICLES = 1000;

            if (ParticleSpeck.count < MAX_PARTICLES) {
                for (int i=0; i<200; i++) {
                    int x = (int)player.posX + (world.rand.nextBoolean() ? world.rand.nextInt(16) : -world.rand.nextInt(16));
                    int y = (int)player.posY + (world.rand.nextBoolean() ? world.rand.nextInt(16) : -world.rand.nextInt(16));
                    int z = (int)player.posZ + (world.rand.nextBoolean() ? world.rand.nextInt(16) : -world.rand.nextInt(16));

                    float fx = world.rand.nextBoolean() ? (0.0125F * world.rand.nextFloat()) : (-0.0125F * world.rand.nextFloat());
                    float fy = world.rand.nextBoolean() ? (0.0125F * world.rand.nextFloat()) : (-0.0125F * world.rand.nextFloat());
                    float fz = world.rand.nextBoolean() ? (0.0125F * world.rand.nextFloat()) : (-0.0125F * world.rand.nextFloat());

                    mc.effectRenderer.addEffect(new ParticleSpeck(world, x, y, z, fx, fy, fz));
                }
            }
        }
    }
}
