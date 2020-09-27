package me.syfe.srp.events;

import me.syfe.srp.Utils;
import me.syfe.srp.config.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SRPEventHandler {
    @SubscribeEvent
    public void onPrePlayerRender(RenderPlayerEvent.Pre e){
        if(!ConfigHandler.renderPlayers) {
            String[] localPlayersToRender = ConfigHandler.playersToRender.split(",");
            EntityPlayer enPlayer = e.entityPlayer;
            if(!Utils.isNPC(enPlayer)){
                e.setCanceled(true);
                for (int i = 0; i < localPlayersToRender.length; i++) {
                    if(localPlayersToRender[i].equals(enPlayer.getGameProfile().getName()) || enPlayer.equals(Minecraft.getMinecraft().thePlayer)) {
                        e.setCanceled(false);
                    }
                }
            }
        }
    }
}
