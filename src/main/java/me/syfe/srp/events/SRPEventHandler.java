package me.syfe.srp.events;

import me.syfe.srp.SRP;
import me.syfe.srp.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SRPEventHandler {
    @SubscribeEvent
    public void onPrePlayerRender(RenderPlayerEvent.Pre e){
        if(SRP.renPlayerBool) {
            EntityPlayer enPlayer = e.entityPlayer;
            if(!Utils.isNPC(enPlayer)){
                if(!(enPlayer.getGameProfile().getName().equalsIgnoreCase(SRP.playerToRender) || enPlayer.equals(Minecraft.getMinecraft().thePlayer))){
                    e.setCanceled(true);
                }
            }
        }
    }
}
