package me.syfe.srp.events;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.syfe.srp.gui.SRPGui;
import me.syfe.srp.util.TickScheduler;
import me.syfe.srp.util.Utils;
import me.syfe.srp.config.ConfigHandler;
import me.syfe.srp.keybinds.Keybinds;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class SRPEventHandler {
    @SubscribeEvent
    public void onPrePlayerRender(RenderPlayerEvent.Pre e){
        if(!ConfigHandler.renderPlayers) {
            String[] localPlayersToRender = ConfigHandler.playersToRender.split(",");
            String[] whitelistedPlayers = ConfigHandler.whitelistedPlayers.split(",");
            EntityPlayer enPlayer = e.entityPlayer;
            if(!Utils.isNPC(enPlayer)){
                e.setCanceled(true);
                for (int i = 0; i < localPlayersToRender.length; i++) {
                    if(localPlayersToRender[i].equals(enPlayer.getGameProfile().getName()) || enPlayer.equals(Minecraft.getMinecraft().thePlayer)) {
                        e.setCanceled(false);
                    }
                }

                for (int i = 0; i < whitelistedPlayers.length; i++) {
                    if(whitelistedPlayers[i].equals(enPlayer.getGameProfile().getName()) || enPlayer.equals(Minecraft.getMinecraft().thePlayer)) {
                        e.setCanceled(false);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent e){
        if(Keybinds.togglesrp.isPressed()){
            if(ConfigHandler.renderPlayers){
                ConfigHandler.renderPlayers = false;
                ConfigHandler.syncFromFields();
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ChatFormatting.GREEN + "[SRP]" + ChatFormatting.RED + " Rendering players is now " + ChatFormatting.BOLD + "off"));
            } else {
                ConfigHandler.renderPlayers = true;
                ConfigHandler.syncFromFields();
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ChatFormatting.GREEN + "[SRP]" + ChatFormatting.GREEN + " Rendering players is now " + ChatFormatting.BOLD + "on"));
            }
        }
        if(Keybinds.opengui.isPressed()){
            TickScheduler.INSTANCE.schedule(0, () -> Minecraft.getMinecraft().displayGuiScreen(new SRPGui()));
        }
    }
}
