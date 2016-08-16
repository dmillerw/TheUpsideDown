package me.dmillerw.upsidedown.client.particle;

import me.dmillerw.upsidedown.lib.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.VertexBuffer;
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

    public static int count = 0;

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
        this.setSize(0.02F, 0.02F);

        this.particleMaxAge = (int) (32.0D / (Math.random() * 0.8D + 0.2D));

        this.ageMid = particleMaxAge / 2F;
        this.particleAlpha = 0F;

        this.type = world.rand.nextInt(3);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (!isAlive()) {
            ParticleSpeck.count--;
            return;
        }

        if (particleAge <= ageMid) {
            particleAlpha = (float) particleAge / ageMid;
        } else {
            particleAlpha = 1F - ((float) (particleAge - ageMid) / ageMid);
        }
    }

    @Override
    public int getBrightnessForRender(float partialTicks) {
        return Integer.MAX_VALUE;
    }

    @Override
    public void renderParticle(VertexBuffer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        Minecraft.getMinecraft().renderEngine.bindTexture(PARTICLE_TEXTURE);

        float f = (float) this.type * 0.0625F;
        float f1 = f + 0.0625F;
        float f2 = 0F;
        float f3 = f2 + 0.0625F;

        float f4 = 0.1F * this.particleScale;

        float f5 = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) partialTicks - interpPosX);
        float f6 = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) partialTicks - interpPosY);
        float f7 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) partialTicks - interpPosZ);
        int i = this.getBrightnessForRender(partialTicks);
        int j = i >> 16 & 65535;
        int k = i & 65535;
        Vec3d[] avec3d = new Vec3d[]{new Vec3d((double) (-rotationX * f4 - rotationXY * f4), (double) (-rotationZ * f4), (double) (-rotationYZ * f4 - rotationXZ * f4)), new Vec3d((double) (-rotationX * f4 + rotationXY * f4), (double) (rotationZ * f4), (double) (-rotationYZ * f4 + rotationXZ * f4)), new Vec3d((double) (rotationX * f4 + rotationXY * f4), (double) (rotationZ * f4), (double) (rotationYZ * f4 + rotationXZ * f4)), new Vec3d((double) (rotationX * f4 - rotationXY * f4), (double) (-rotationZ * f4), (double) (rotationYZ * f4 - rotationXZ * f4))};

        if (this.field_190014_F != 0.0F) {
            float f8 = this.field_190014_F + (this.field_190014_F - this.field_190015_G) * partialTicks;
            float f9 = MathHelper.cos(f8 * 0.5F);
            float f10 = MathHelper.sin(f8 * 0.5F) * (float) field_190016_K.xCoord;
            float f11 = MathHelper.sin(f8 * 0.5F) * (float) field_190016_K.yCoord;
            float f12 = MathHelper.sin(f8 * 0.5F) * (float) field_190016_K.zCoord;
            Vec3d vec3d = new Vec3d((double) f10, (double) f11, (double) f12);

            for (int l = 0; l < 4; ++l) {
                avec3d[l] = vec3d.scale(2.0D * avec3d[l].dotProduct(vec3d)).add(avec3d[l].scale((double) (f9 * f9) - vec3d.dotProduct(vec3d))).add(vec3d.crossProduct(avec3d[l]).scale((double) (2.0F * f9)));
            }
        }

        worldRendererIn.pos((double) f5 + avec3d[0].xCoord, (double) f6 + avec3d[0].yCoord, (double) f7 + avec3d[0].zCoord).tex((double) f1, (double) f3).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        worldRendererIn.pos((double) f5 + avec3d[1].xCoord, (double) f6 + avec3d[1].yCoord, (double) f7 + avec3d[1].zCoord).tex((double) f1, (double) f2).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        worldRendererIn.pos((double) f5 + avec3d[2].xCoord, (double) f6 + avec3d[2].yCoord, (double) f7 + avec3d[2].zCoord).tex((double) f, (double) f2).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        worldRendererIn.pos((double) f5 + avec3d[3].xCoord, (double) f6 + avec3d[3].yCoord, (double) f7 + avec3d[3].zCoord).tex((double) f, (double) f3).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
    }
}
