package me.dmillerw.upsidedown.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by dmillerw
 */
public interface IProxy {

    public void preInit(FMLPreInitializationEvent event);
    public void init(FMLInitializationEvent event);
    public void postInit(FMLPostInitializationEvent event);

    public void onStateChanged(EntityPlayer player, boolean state);
}
