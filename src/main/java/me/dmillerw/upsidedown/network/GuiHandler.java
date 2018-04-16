package me.dmillerw.upsidedown.network;

import me.dmillerw.upsidedown.TheUpsideDown;
import me.dmillerw.upsidedown.client.gui.GuiDebug;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by dmillerw
 */
public class GuiHandler implements IGuiHandler {

    public static enum GuiType {
        DEBUG;

        public void open(EntityPlayer player) {
            player.openGui(TheUpsideDown.INSTANCE, this.ordinal(), player.world, 0, 0, 0);
        }

        public void open(EntityPlayer player, World world, int x, int y, int z) {
            player.openGui(TheUpsideDown.INSTANCE, this.ordinal(), world, x, y, z);
        }
    }

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        return getGuiElement(GuiType.values()[id], Side.SERVER, player, world, x, y, z);
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        return getGuiElement(GuiType.values()[id], Side.CLIENT, player, world, x, y, z);
    }

    private Object getGuiElement(GuiType type, Side side, EntityPlayer player, World world, int x, int y, int z) {
        if (side == Side.CLIENT) {
            switch (type) {
                case DEBUG: return new GuiDebug();
            }
            return null;
        } else {
            return null;
        }
    }
}
