package me.dmillerw.upsidedown.network.client;

import com.google.common.collect.Sets;
import io.netty.buffer.ByteBuf;
import me.dmillerw.upsidedown.TheUpsideDown;
import me.dmillerw.upsidedown.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.Set;
import java.util.UUID;

/**
 * Created by dmillerw
 */
public class C01StateUpdate implements IMessage {

    public boolean inUpsideDown = false;
    public Set<UUID> otherPlayers = Sets.newHashSet();

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(inUpsideDown);
        buf.writeInt(otherPlayers.size());
        for (UUID uuid : otherPlayers) {
            buf.writeLong(uuid.getMostSignificantBits());
            buf.writeLong(uuid.getLeastSignificantBits());
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        inUpsideDown = buf.readBoolean();
        for (int i=0; i<buf.readInt(); i++) {
            UUID uuid = new UUID(buf.readLong(), buf.readLong());
            otherPlayers.add(uuid);
        }
    }

    public static class Handler implements IMessageHandler<C01StateUpdate, IMessage> {

        @Override
        public IMessage onMessage(C01StateUpdate message, MessageContext ctx) {
            ClientProxy.inUpsideDown = message.inUpsideDown;
            ClientProxy.playersSharingState = message.otherPlayers;

            TheUpsideDown.PROXY.onStateChanged(Minecraft.getMinecraft().player, message.inUpsideDown);

            return null;
        }
    }
}
