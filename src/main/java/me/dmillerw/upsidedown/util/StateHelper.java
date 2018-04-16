package me.dmillerw.upsidedown.util;

import com.google.common.collect.Sets;
import me.dmillerw.upsidedown.TheUpsideDown;
import me.dmillerw.upsidedown.network.PacketHandler;
import me.dmillerw.upsidedown.network.client.C01StateUpdate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.server.FMLServerHandler;

import java.util.Set;
import java.util.UUID;

/**
 * Created by dmillerw
 */
public class StateHelper {

    private static final String KEY = "upsidedown:state";

    public static boolean getState(EntityPlayer player) {
        NBTTagCompound data = player.getEntityData();
        if (!data.hasKey(KEY)) {
            setState(player, false);
        }
        return data.getBoolean(KEY);
    }

    public static void setState(EntityPlayer player, boolean state) {
        player.getEntityData().setBoolean(KEY, state);

        C01StateUpdate packet = new C01StateUpdate();
        packet.inUpsideDown = state;

        PacketHandler.INSTANCE.sendTo(packet, (EntityPlayerMP) player);

        TheUpsideDown.PROXY.onStateChanged(player, state);
    }

    public static void swapState(EntityPlayer player) {
        setState(player, !getState(player));
    }

    public static Set<UUID> getPlayersInState(boolean state) {
        Set<UUID> set = Sets.newHashSet();

        MinecraftServer server = FMLServerHandler.instance().getServer();
        for (EntityPlayerMP player : server.getPlayerList().getPlayers()) {
            if (getState(player) == state) {
                set.add(player.getGameProfile().getId());
            }
        }

        return set;
    }
}
