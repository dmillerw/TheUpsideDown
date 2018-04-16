package me.dmillerw.upsidedown.proxy;

import me.dmillerw.upsidedown.TheUpsideDown;
import me.dmillerw.upsidedown.item.ModItems;
import me.dmillerw.upsidedown.network.GuiHandler;
import me.dmillerw.upsidedown.network.PacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

/**
 * Created by dmillerw
 */
public class CommonProxy implements IProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        PacketHandler.initialize();
        NetworkRegistry.INSTANCE.registerGuiHandler(TheUpsideDown.INSTANCE, new GuiHandler());
    }

    @Override
    public void init(FMLInitializationEvent event) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Override
    public void onStateChanged(EntityPlayer player, boolean state) {
        // NOOP (probably)
    }
}
