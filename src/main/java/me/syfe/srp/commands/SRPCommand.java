package me.syfe.srp.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.syfe.srp.util.OnlinePlayers;
import me.syfe.srp.config.ConfigHandler;
import me.syfe.srp.gui.SRPGui;
import me.syfe.srp.util.TickScheduler;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
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
                ChatFormatting.RED + "+" + ChatFormatting.BLUE + " /srp - Open GUI" + "\n" +
                ChatFormatting.RED + "+" + ChatFormatting.BLUE + " /srp add/remove - Add/remove players" + "\n" +
                ChatFormatting.RED + "+" + ChatFormatting.BLUE + " /srp whitelist add/remove/list - Whitelist cmds" + "\n" +
                ChatFormatting.RED + "+" + ChatFormatting.BLUE + " /srp toggle - Toggles the mod on/off" + "\n" +
                ChatFormatting.RED + "+" + ChatFormatting.BLUE + " /srp list - Shows current rendered players" + "\n" +
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
            } else if(args[0].equalsIgnoreCase("whitelist")){
                try {
                    if(args[1].equalsIgnoreCase("add")){
                        ConfigHandler.whitelistedPlayers += args[2] + ",";
                        ConfigHandler.syncFromFields();
                        player.addChatMessage(new ChatComponentText(ChatFormatting.GREEN + "[SRP]" + ChatFormatting.BLUE + " Added player to whitelist " + ChatFormatting.BOLD + args[2]));
                        return;
                    } else if(args[1].equalsIgnoreCase("remove")) {
                        String[] localNames = ConfigHandler.whitelistedPlayers.split(",");
                        String namesToSave = "";
                        for (int i = 0; i < localNames.length; i++) {
                            if (!args[2].equals(localNames[i])) {
                                namesToSave += localNames[i] + ",";
                            }
                        }
                        ConfigHandler.whitelistedPlayers = namesToSave;

                        ConfigHandler.syncFromFields();

                        player.addChatMessage(new ChatComponentText(ChatFormatting.GREEN + "[SRP]" + ChatFormatting.BLUE + " Removed player from whitelist " + ChatFormatting.BOLD + args[2]));
                        return;
                    } else if(args[1].equalsIgnoreCase("list")) {
                        String str = ConfigHandler.playersToRender;
                        if(!str.isEmpty()){
                            str = str.substring(0, str.length() -1);
                        } else {
                            str = "none";
                        }

                        player.addChatMessage(new ChatComponentText(ChatFormatting.GREEN + "[SRP]" + ChatFormatting.BLUE + " Current whitelisted players are " + ChatFormatting.BOLD + str));
                    } else {
                        String str = ConfigHandler.playersToRender;
                        if(!str.isEmpty()){
                            str = str.substring(0, str.length() -1);
                        } else {
                            str = "none";
                        }

                        player.addChatMessage(new ChatComponentText(ChatFormatting.GREEN + "[SRP]" + ChatFormatting.BLUE + " Current whitelisted players are " + ChatFormatting.BOLD + str));
                    }
                } catch(Exception e){} finally {
                    String str = ConfigHandler.playersToRender;
                    if(!str.isEmpty()){
                        str = str.substring(0, str.length() -1);
                    } else {
                        str = "none";
                    }

                    player.addChatMessage(new ChatComponentText(ChatFormatting.GREEN + "[SRP]" + ChatFormatting.BLUE + " Current whitelisted players are " + ChatFormatting.BOLD + str));
                }

            } else if (args[0].equalsIgnoreCase("toggle")) {

                if(ConfigHandler.renderPlayers){
                    ConfigHandler.renderPlayers = false;
                    ConfigHandler.syncFromFields();
                    player.addChatMessage(new ChatComponentText(ChatFormatting.GREEN + "[SRP]" + ChatFormatting.RED + " Rendering players is now " + ChatFormatting.BOLD + "off"));
                } else {
                    ConfigHandler.renderPlayers = true;
                    ConfigHandler.syncFromFields();
                    player.addChatMessage(new ChatComponentText(ChatFormatting.GREEN + "[SRP]" + ChatFormatting.GREEN + " Rendering players is now " + ChatFormatting.BOLD + "on"));
                }

            } else if (args[0].equalsIgnoreCase("list")) {
                String str = ConfigHandler.playersToRender;
                if(!str.isEmpty()){
                    str = str.substring(0, str.length() -1);
                } else {
                    str = "none";
                }

                player.addChatMessage(new ChatComponentText(ChatFormatting.GREEN + "[SRP]" + ChatFormatting.BLUE + " Current rendered players are " + ChatFormatting.BOLD + str));
            } else if (args[0].equalsIgnoreCase("help")) {
                player.addChatMessage(new ChatComponentText(getCommandUsage(sender)));
            } else {
                player.addChatMessage(new ChatComponentText(getCommandUsage(sender)));
            }
        } else {
            TickScheduler.INSTANCE.schedule(0, () -> Minecraft.getMinecraft().displayGuiScreen(new SRPGui()));
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
    {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, "add","remove","toggle","whitelist","list","help");
        } else if (args.length >= 2) {
            String[] inConfig = ConfigHandler.playersToRender.split(",");
            String[] whitelist = ConfigHandler.whitelistedPlayers.split(",");
            String[] onlinePlayersLobby = OnlinePlayers.getListOfPlayerUsernames();
            if(args[0].equalsIgnoreCase("remove")) {
                return getListOfStringsMatchingLastWord(args, inConfig);
            }
            if (args[0].equalsIgnoreCase("add")) {
                return getListOfStringsMatchingLastWord(args, onlinePlayersLobby);
            }
            if (args[0].equalsIgnoreCase("whitelist")) {
                if(args[1].equalsIgnoreCase("add")){
                    return getListOfStringsMatchingLastWord(args, onlinePlayersLobby);
                } else if(args[1].equalsIgnoreCase("remove")){
                    return getListOfStringsMatchingLastWord(args, whitelist);
                } else {
                    return getListOfStringsMatchingLastWord(args, "add","remove","list");
                }
            }
        }
        return null;
    }
}
