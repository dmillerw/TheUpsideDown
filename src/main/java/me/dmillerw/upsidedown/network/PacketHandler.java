package me.dmillerw.upsidedown.network;

import me.dmillerw.upsidedown.lib.ModInfo;
import me.dmillerw.upsidedown.network.client.C01StateUpdate;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by dmillerw
 */
public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ModInfo.ID);

    public static void initialize() {
        INSTANCE.registerMessage(C01StateUpdate.Handler.class, C01StateUpdate.class, -1, Side.CLIENT);
    }
}
