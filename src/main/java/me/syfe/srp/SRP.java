package me.syfe.srp;

import me.syfe.srp.commands.SRPCommand;
import me.syfe.srp.config.ConfigHandler;
import me.syfe.srp.events.SRPEventHandler;
import me.syfe.srp.keybinds.Keybinds;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = SRP.MODID, name = SRP.NAME, version = SRP.VERSION)
public class SRP
{
    public static final String MODID = "srp";
    public static final String NAME = "Select Player Renderer";
    public static final String VERSION = "2.0";
    public static Minecraft mc = Minecraft.getMinecraft();

    public static final Logger LOGGER = LogManager.getLogger("SelectPlayerRenderer");

    @Mod.Instance(SRP.MODID)
    public static SRP instance;

    @Mod.EventHandler
    public void preLoad(FMLPreInitializationEvent event){
        ConfigHandler.preInit();
        Keybinds.register();
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event){
        MinecraftForge.EVENT_BUS.register(new SRPEventHandler());
        FMLCommonHandler.instance().bus().register(new SRPEventHandler());

        ClientCommandHandler.instance.registerCommand(new SRPCommand());
    }
}