package me.dmillerw.upsidedown.event.server;

import me.dmillerw.upsidedown.util.StateHelper;
import me.dmillerw.upsidedown.network.PacketHandler;
import me.dmillerw.upsidedown.network.client.C01StateUpdate;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

/**
 * Created by dmillerw
 */
public class NetworkEventHandler {

    @SubscribeEvent
    public void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        C01StateUpdate packet = new C01StateUpdate();
        packet.inUpsideDown = StateHelper.getState(event.player);
        packet.otherPlayers = StateHelper.getPlayersInState(packet.inUpsideDown);
        PacketHandler.INSTANCE.sendTo(packet, (EntityPlayerMP) event.player);
    }

    @SubscribeEvent
    public void playerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        PacketHandler.INSTANCE.sendTo(new C01StateUpdate(), (EntityPlayerMP) event.player);
    }
}
