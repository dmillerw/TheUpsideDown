package me.dmillerw.upsidedown.event.client;

import me.dmillerw.upsidedown.client.particle.ParticleSpeck;
import me.dmillerw.upsidedown.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * Created by dmillerw
 */
public class RenderEventHandler {

    private static final ResourceLocation END_SKY_TEXTURES = new ResourceLocation("textures/environment/end_sky.png");
    public static final IRenderHandler ENDER_SKY_RENDERER = new IRenderHandler() {
        @Override
        public void render(float partialTicks, WorldClient world, Minecraft mc) {
            GlStateManager.disableFog();
            GlStateManager.disableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.depthMask(false);
            Minecraft.getMinecraft().renderEngine.bindTexture(END_SKY_TEXTURES);
            Tessellator tessellator = Tessellator.getInstance();
            VertexBuffer vertexbuffer = tessellator.getBuffer();

            for (int i = 0; i < 6; ++i)
            {
                GlStateManager.pushMatrix();

                if (i == 1)
                {
                    GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                }

                if (i == 2)
                {
                    GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
                }

                if (i == 3)
                {
                    GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
                }

                if (i == 4)
                {
                    GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                }

                if (i == 5)
                {
                    GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
                }

                vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                vertexbuffer.pos(-100.0D, -100.0D, -100.0D).tex(0.0D, 0.0D).color(40, 40, 40, 255).endVertex();
                vertexbuffer.pos(-100.0D, -100.0D, 100.0D).tex(0.0D, 16.0D).color(40, 40, 40, 255).endVertex();
                vertexbuffer.pos(100.0D, -100.0D, 100.0D).tex(16.0D, 16.0D).color(40, 40, 40, 255).endVertex();
                vertexbuffer.pos(100.0D, -100.0D, -100.0D).tex(16.0D, 0.0D).color(40, 40, 40, 255).endVertex();
                tessellator.draw();
                GlStateManager.popMatrix();
            }

            GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();
            GlStateManager.enableAlpha();
        }
    };

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
            final int MAX_PARTICLES = 500;

            if (ParticleSpeck.count < MAX_PARTICLES) {
                for (int i=0; i<world.rand.nextInt(50); i++) {
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
