package me.dmillerw.upsidedown.item;

import me.dmillerw.upsidedown.lib.ModInfo;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by dmillerw
 */
@GameRegistry.ObjectHolder(ModInfo.ID)
public class ModItems {

    @GameRegistry.ObjectHolder(ModInfo.ID + ":debug")
    public static ItemDebug debug;

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            event.getRegistry().registerAll(
                    new ItemDebug().setRegistryName(ModInfo.ID, "itemizer")
            );
        }
    }
}
