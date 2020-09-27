package me.syfe.srp.config;


import java.io.File;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Loader;

public class ConfigHandler {
    private static Configuration config = null;


    public static boolean renderPlayers;
    public static String playersToRender;
    public static String whitelistedPlayers;

    public static void preInit() {
        File configFile = new File(Loader.instance().getConfigDir(), "SRP.cfg");
        config = new Configuration(configFile);
        syncFromFiles();
    }

    public static Configuration getConfig() {
        return config;
    }

    public static void syncFromFiles() {
        syncConfig(true, true);
    }

    public static void syncFromGui() {
        syncConfig(false, true);
    }

    public static void syncFromFields() {
        syncConfig(false, false);
    }

    private static void syncConfig(boolean loadFromConfigFile, boolean readFieldsFromConfig) {
        if(loadFromConfigFile)
            config.load();

        Property propertyRenderPlayers = config.get(Configuration.CATEGORY_GENERAL, "renderPlayers", true);
        Property propertyPlayersToRender = config.get(Configuration.CATEGORY_GENERAL, "playersToRender", "");
        Property propertyWhitelistedPlayers = config.get(Configuration.CATEGORY_GENERAL, "whitelistedPlayers", "");

        if(readFieldsFromConfig) {
            renderPlayers = propertyRenderPlayers.getBoolean();
            playersToRender = propertyPlayersToRender.getString();
            whitelistedPlayers = propertyWhitelistedPlayers.getString();
        }

        propertyRenderPlayers.set(renderPlayers);
        propertyPlayersToRender.set(playersToRender);
        propertyWhitelistedPlayers.set(whitelistedPlayers);

        if(config.hasChanged())
            config.save();
    }
}
