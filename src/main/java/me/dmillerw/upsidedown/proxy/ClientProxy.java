package me.dmillerw.upsidedown.proxy;

import com.google.common.collect.Sets;
import me.dmillerw.upsidedown.client.event.RenderEventHandler;
import me.dmillerw.upsidedown.client.state.AtmosphericState;
import me.dmillerw.upsidedown.lib.ModInfo;
import me.dmillerw.upsidedown.lib.SkyRenderers;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.Set;
import java.util.UUID;

/**
 * Created by dmillerw
 */
public class ClientProxy extends CommonProxy {

    // Client self cached state, reset on each login to 'server'
    public static boolean inUpsideDown = false;
    // List of all Player's in the same state as this player, set on each sync
    public static Set<UUID> playersSharingState = Sets.newHashSet();

    // Client state management
    public static AtmosphericState atmosphericState = new AtmosphericState();

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        MinecraftForge.EVENT_BUS.register(new RenderEventHandler());
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @Override
    public void onStateChanged(EntityPlayer player, boolean state) {
        super.onStateChanged(player, state);

        if (state) {
            player.worldObj.provider.setCloudRenderer(SkyRenderers.EMPTY);
            Minecraft.getMinecraft().addScheduledTask(() -> Minecraft.getMinecraft().entityRenderer.loadShader(new ResourceLocation(ModInfo.ID, "shaders/post/desaturate.json")));
        } else {
            player.worldObj.provider.setCloudRenderer(null);
            Minecraft.getMinecraft().addScheduledTask(() -> Minecraft.getMinecraft().entityRenderer.stopUseShader());
        }
    }
}
