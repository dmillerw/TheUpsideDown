package me.dmillerw.upsidedown.command;

import me.dmillerw.upsidedown.proxy.ClientProxy;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

/**
 * Created by dmillerw
 */
public class CommandDebug extends CommandBase {

    @Override
    public String getCommandName() {
        return "debug";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/debug [dump/fog/light] [r g b]";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        String type = args[0];
        if (type.equalsIgnoreCase("fog")) {
            ClientProxy.fogR = (float) parseDouble(args[1]);
            ClientProxy.fogG = (float) parseDouble(args[2]);
            ClientProxy.fogB = (float) parseDouble(args[3]);
        } else if (type.equalsIgnoreCase("light")) {
            ClientProxy.lightR = (float) parseDouble(args[1]);
            ClientProxy.lightG = (float) parseDouble(args[2]);
            ClientProxy.lightB = (float) parseDouble(args[3]);
        } else if (type.equalsIgnoreCase("dump")) {
            sender.addChatMessage(new TextComponentString("FOG: R: " + ClientProxy.fogR + ", G: " + ClientProxy.fogG + ", B: " + ClientProxy.fogB));
            sender.addChatMessage(new TextComponentString("LIGHT: R: " + ClientProxy.lightR + ", G: " + ClientProxy.lightG + ", B: " + ClientProxy.lightB));
        }
    }
}
