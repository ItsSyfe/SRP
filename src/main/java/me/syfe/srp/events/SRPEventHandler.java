package me.syfe.srp.events;

import me.syfe.srp.SRP;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SRPEventHandler {
    @SubscribeEvent
    public void onPrePlayerRender(RenderPlayerEvent.Pre e){
        if(SRP.renPlayerBool) {
            if(!(e.entityPlayer.getDisplayNameString().equalsIgnoreCase(SRP.playerToRender) || e.entityPlayer.equals(Minecraft.getMinecraft().thePlayer))){
                e.setCanceled(true);
            }
        }
    }
}
