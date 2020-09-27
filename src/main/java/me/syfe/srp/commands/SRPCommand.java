package me.syfe.srp.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.syfe.srp.OnlinePlayers;
import me.syfe.srp.config.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import me.syfe.srp.SRP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
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
                ChatFormatting.RED + "+" + ChatFormatting.BLUE + " /srp add PLAYERNAME - Add player to list of players to be rendered" + "\n" +
                ChatFormatting.RED + "+" + ChatFormatting.BLUE + " /srp remove PLAYERNAME - Remove player to list of players to be rendered" + "\n" +
                ChatFormatting.RED + "+" + ChatFormatting.BLUE + " /srp toggle - Toggles the mod on/off" + "\n" +
                ChatFormatting.RED + "+" + ChatFormatting.BLUE + " /srp current - Shows current rendered player" + "\n" +
                ChatFormatting.DARK_BLUE + "------------------------------------------";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("add")) {
                ConfigHandler.playersToRender += args[1] + ",";
                ConfigHandler.syncFromFields();
                player.addChatMessage(new ChatComponentText(ChatFormatting.GREEN + "[SRP]" + ChatFormatting.BLUE + " Added player to be rendered " + ChatFormatting.BOLD + args[1]));
            } else if (args[0].equalsIgnoreCase("remove")) {
                String[] localNames = ConfigHandler.playersToRender.split(",");
                String namesToSave = "";
                for (int i = 0; i < localNames.length; i++) {
                    if(!args[1].equals(localNames[i])){
                        namesToSave += localNames[i] + ",";
                    }
                }
                ConfigHandler.playersToRender = namesToSave;

                ConfigHandler.syncFromFields();
                player.addChatMessage(new ChatComponentText(ChatFormatting.GREEN + "[SRP]" + ChatFormatting.BLUE + " Removed player from being rendered " + ChatFormatting.BOLD + args[1]));
            } else if (args[0].equalsIgnoreCase("toggle")) {

                if(ConfigHandler.renderPlayers){
                    ConfigHandler.renderPlayers = false;
                    ConfigHandler.syncFromFields();
                    player.addChatMessage(new ChatComponentText(ChatFormatting.GREEN + "[SRP]" + ChatFormatting.GREEN + " Rendering players is now " + ChatFormatting.BOLD + "off"));
                } else {
                    ConfigHandler.renderPlayers = true;
                    ConfigHandler.syncFromFields();
                    player.addChatMessage(new ChatComponentText(ChatFormatting.GREEN + "[SRP]" + ChatFormatting.RED + " Rendering players is now " + ChatFormatting.BOLD + "on"));
                }

            } else if (args[0].equalsIgnoreCase("current")) {
                player.addChatMessage(new ChatComponentText(ChatFormatting.GREEN + "[SRP]" + ChatFormatting.BLUE + " Current player being rendered is " + ChatFormatting.BOLD + ConfigHandler.playersToRender));
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
            return getListOfStringsMatchingLastWord(args, "add","remove","toggle","current");
        } else if (args.length >= 2) {
            String[] inConfig = ConfigHandler.playersToRender.split(",");
            String[] onlinePlayersLobby = OnlinePlayers.getListOfPlayerUsernames();
            if(args[0].equalsIgnoreCase("remove")) {
                return getListOfStringsMatchingLastWord(args, inConfig);
            }
            if (args[0].equalsIgnoreCase("add")) {
                return getListOfStringsMatchingLastWord(args, onlinePlayersLobby);
            }
        }
        return null;
    }
}
