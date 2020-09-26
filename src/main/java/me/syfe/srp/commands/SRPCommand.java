package me.syfe.srp.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.syfe.srp.OnlinePlayers;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import me.syfe.srp.SRP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import java.util.List;

public class SRPCommand extends CommandBase {

    /**
     * Gets the name of the command
     */
    public String getCommandName() {
        return "srp";
    }

    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel()
    {
        return 0;
    }

    /**
     * Gets the usage string for the command.
     */
    public String getCommandUsage(ICommandSender sender) {

        return ChatFormatting.DARK_BLUE + "------------" + ChatFormatting.GREEN + "[SRP]" + ChatFormatting.DARK_BLUE + "------------" + "\n" +
                ChatFormatting.RED + "+" + ChatFormatting.BLUE + " /srp edit (PLAYERNAME) - Edit rendered player" + "\n" +
                ChatFormatting.RED + "+" + ChatFormatting.BLUE + " /srp off - Turns the mod off" + "\n" +
                ChatFormatting.RED + "+" + ChatFormatting.BLUE + " /srp on - Turns the mod on" + "\n" +
                ChatFormatting.RED + "+" + ChatFormatting.BLUE + " /srp current - Shows current rendered player" + "\n" +
                ChatFormatting.DARK_BLUE + "------------------------------------------";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("edit")) {
                SRP.playerToRender = args[1];
                player.addChatMessage(new ChatComponentText(ChatFormatting.GREEN + "[SRP]" + ChatFormatting.BLUE + " Player being rendered is now " + ChatFormatting.BOLD + args[1]));
            } else if (args[0].equalsIgnoreCase("off")) {
                SRP.renPlayerBool = false;
                player.addChatMessage(new ChatComponentText(ChatFormatting.GREEN + "[SRP]" + ChatFormatting.RED + " Rendering players is now " + ChatFormatting.BOLD + "off"));
            } else if (args[0].equalsIgnoreCase("on")) {
                SRP.renPlayerBool = true;
                player.addChatMessage(new ChatComponentText(ChatFormatting.GREEN + "[SRP]" + ChatFormatting.GREEN + " Rendering players is now " + ChatFormatting.BOLD + "on!"));
            } else if (args[0].equalsIgnoreCase("current")) {
                player.addChatMessage(new ChatComponentText(ChatFormatting.GREEN + "[SRP]" + ChatFormatting.BLUE + " Current player being rendered is " + ChatFormatting.BOLD + SRP.playerToRender));
            } else {
                player.addChatMessage(new ChatComponentText(getCommandUsage(sender)));
            }
        } else {
            player.addChatMessage(new ChatComponentText(getCommandUsage(sender)));
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
    {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, "edit","on","off","current");
        } else if (args.length >= 2) {
            return getListOfStringsMatchingLastWord(args, OnlinePlayers.getListOfPlayerUsernames());
        }
        return null;
    }
}
