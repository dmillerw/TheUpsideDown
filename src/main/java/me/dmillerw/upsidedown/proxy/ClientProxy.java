package me.dmillerw.upsidedown.proxy;

import com.google.common.collect.Sets;
import me.dmillerw.upsidedown.event.client.RenderEventHandler;
import net.minecraft.entity.player.EntityPlayer;
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

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
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
            player.worldObj.provider.setSkyRenderer(RenderEventHandler.ENDER_SKY_RENDERER);
        } else {
            player.worldObj.provider.setCloudRenderer(null);
        }
    }
}
