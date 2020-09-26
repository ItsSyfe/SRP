package me.syfe.srp;

import me.syfe.srp.commands.SRPCommand;
import me.syfe.srp.events.SRPEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = SRP.MODID, version = SRP.VERSION)
public class SRP
{
    public static final String MODID = "srp";
    public static final String VERSION = "1.0";
    public static Minecraft mc = Minecraft.getMinecraft();

    public static String playerToRender = "4tz";
    public static Boolean renPlayerBool = false;

    @Mod.EventHandler
    public void load(FMLInitializationEvent event){
        FMLCommonHandler.instance().bus().register(new SRPEventHandler());

        ClientCommandHandler.instance.registerCommand(new SRPCommand());
    }
}