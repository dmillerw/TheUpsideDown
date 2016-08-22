package me.dmillerw.upsidedown.client.particle;

import me.dmillerw.upsidedown.lib.ModInfo;
import me.dmillerw.upsidedown.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * Created by dmillerw
 */
public class ParticleSpeck extends Particle {

    private static final ResourceLocation PARTICLE_TEXTURE = new ResourceLocation(ModInfo.ID, "textures/particles/speck.png");
    private static final ResourceLocation DEFAULT_PARTICLE_TEXTURES = new ResourceLocation("textures/particle/particles.png");

    public static int count = 0;
    public static boolean killAll = false;

    private float ageMid;
    private int type;

    public ParticleSpeck(World world, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed) {
        super(world, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed);

        ParticleSpeck.count++;

        this.motionX = xSpeed;
        this.motionY = ySpeed;
        this.motionZ = zSpeed;

        this.particleRed = 1.0F;
        this.particleGreen = 1.0F;
        this.particleBlue = 1.0F;
        this.setParticleTextureIndex(32);
        this.setSize(
                ClientProxy.atmosphericState.particleSize,
                ClientProxy.atmosphericState.particleSize);

        this.particleMaxAge = (int) (ClientProxy.atmosphericState.particleMaxLifespan / (Math.random() * 0.8D + 0.2D));

        this.ageMid = particleMaxAge / 2F;
        this.particleAlpha = 0F;

        this.type = world.rand.nextInt(3);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (!ClientProxy.inUpsideDown) {
            ParticleSpeck.count--;
            setExpired();
            return;
        }

        if (ParticleSpeck.killAll) {
            ParticleSpeck.count--;
            setExpired();
            return;
        }

        if (!isAlive()) {
            ParticleSpeck.count--;
            return;
        }

        if (particleAge <= ageMid) {
            particleAlpha = 0.5F * (float) particleAge / ageMid;
        } else {
            particleAlpha = 0.5F - (0.5F * ((particleAge - ageMid) / ageMid));
        }
    }

    @Override
    public void renderParticle(VertexBuffer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        final Minecraft mc = Minecraft.getMinecraft();
        mc.renderEngine.bindTexture(PARTICLE_TEXTURE);

        float uMin = (float) this.type * 0.0625F;
        float uMax = uMin + 0.0625F;
        float vMin = 0F;
        float vMax = vMin + 0.0625F;

        float scale = 0.1F * this.particleScale;

        float x = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) partialTicks - interpPosX);
        float y = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) partialTicks - interpPosY);
        float z = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) partialTicks - interpPosZ);

        int brightness = this.getBrightnessForRender(partialTicks);

        int lightU = brightness >> 16 & 65535;
        int lightV = brightness & 65535;

        Vec3d[] vector = new Vec3d[]{new Vec3d((double) (-rotationX * scale - rotationXY * scale), (double) (-rotationZ * scale), (double) (-rotationYZ * scale - rotationXZ * scale)), new Vec3d((double) (-rotationX * scale + rotationXY * scale), (double) (rotationZ * scale), (double) (-rotationYZ * scale + rotationXZ * scale)), new Vec3d((double) (rotationX * scale + rotationXY * scale), (double) (rotationZ * scale), (double) (rotationYZ * scale + rotationXZ * scale)), new Vec3d((double) (rotationX * scale - rotationXY * scale), (double) (-rotationZ * scale), (double) (rotationYZ * scale - rotationXZ * scale))};

        if (this.field_190014_F != 0.0F) {
            float f8 = this.field_190014_F + (this.field_190014_F - this.field_190015_G) * partialTicks;
            float f9 = MathHelper.cos(f8 * 0.5F);

            float bx = MathHelper.sin(f8 * 0.5F) * (float) field_190016_K.xCoord;
            float by = MathHelper.sin(f8 * 0.5F) * (float) field_190016_K.yCoord;
            float bz = MathHelper.sin(f8 * 0.5F) * (float) field_190016_K.zCoord;

            Vec3d billboardVec = new Vec3d((double) bx, (double) by, (double) bz);

            for (int l = 0; l < 4; ++l) {
                vector[l] = billboardVec.scale(2.0D * vector[l].dotProduct(billboardVec)).add(vector[l].scale((double) (f9 * f9) - billboardVec.dotProduct(billboardVec))).add(billboardVec.crossProduct(vector[l]).scale((double) (2.0F * f9)));
            }
        }

        mc.entityRenderer.disableLightmap();

        worldRendererIn.pos((double) x + vector[0].xCoord, (double) y + vector[0].yCoord, (double) z + vector[0].zCoord).tex((double) uMax, (double) vMax).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(lightU, lightV).endVertex();
        worldRendererIn.pos((double) x + vector[1].xCoord, (double) y + vector[1].yCoord, (double) z + vector[1].zCoord).tex((double) uMax, (double) vMin).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(lightU, lightV).endVertex();
        worldRendererIn.pos((double) x + vector[2].xCoord, (double) y + vector[2].yCoord, (double) z + vector[2].zCoord).tex((double) uMin, (double) vMin).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(lightU, lightV).endVertex();
        worldRendererIn.pos((double) x + vector[3].xCoord, (double) y + vector[3].yCoord, (double) z + vector[3].zCoord).tex((double) uMin, (double) vMax).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(lightU, lightV).endVertex();

        Tessellator.getInstance().draw();
        mc.entityRenderer.enableLightmap();
        worldRendererIn.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);

        mc.renderEngine.bindTexture(DEFAULT_PARTICLE_TEXTURES);
    }
}
