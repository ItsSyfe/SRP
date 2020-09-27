package me.syfe.srp;

import me.syfe.srp.commands.SRPCommand;
import me.syfe.srp.config.ConfigHandler;
import me.syfe.srp.events.SRPEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = SRP.MODID, version = SRP.VERSION, guiFactory = SRP.GUI_FACTORY)
public class SRP
{
    public static final String MODID = "srp";
    public static final String VERSION = "1.2";
    public static final String GUI_FACTORY = "me.syfe.srp.config.ConfigGuiFactory";
    public static Minecraft mc = Minecraft.getMinecraft();

    @Mod.Instance(SRP.MODID)
    public static SRP instance;

    @Mod.EventHandler
    public void preLoad(FMLPreInitializationEvent event){
        ConfigHandler.preInit();
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event){
        FMLCommonHandler.instance().bus().register(new SRPEventHandler());
        FMLCommonHandler.instance().bus().register(new ConfigHandler.ConfigEventHandler());

        ClientCommandHandler.instance.registerCommand(new SRPCommand());
    }
}