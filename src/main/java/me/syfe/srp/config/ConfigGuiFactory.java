package me.syfe.srp.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import me.syfe.srp.SRP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.GuiConfigEntries.CategoryEntry;
import net.minecraftforge.fml.client.config.IConfigElement;

public class ConfigGuiFactory implements IModGuiFactory {

    /**
     * Used to initialize values from the user's minecraft instance
     * We don't use this
     */
    @Override
    public void initialize(Minecraft minecraftInstance) {
    }

    /**
     * The actual class which is the gui
     */
    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return ConfigHandlerGui.class;
    }

    /**
     * Ggets the runtime gui categories which change in game
     */
    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }

    /**
     * Used to change the colour of the properties
     */
    @Override
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
        return null;
    }

    /**
     * The gui for the config
     * @author CJMinecraft
     *
     */
    public static class ConfigHandlerGui extends GuiConfig {

        /**
         * Settting up the screen
         * @param parentScreen The prior screen
         */
        public ConfigHandlerGui(GuiScreen parentScreen) {
            super(parentScreen, getConfigElements(), SRP.MODID, false, false, I18n.format("SRP Config"));
        }

        /**
         * Get all of the different categories
         * @return a list of the different categories
         */
        private static List<IConfigElement> getConfigElements() {
            List<IConfigElement> list = new ArrayList<IConfigElement>();
            list.add(new DummyCategoryElement(I18n.format("General Settings"), "General Settings", CategoryGeneral.class)); //Add another one of these for any other categories
            return list;
        }

        /**
         * The category for the blocks
         * @author CJMinecraft
         *
         */
        public static class CategoryGeneral extends CategoryEntry {

            /**
             * Default constructor
             */
            public CategoryGeneral(GuiConfig owningScreen, GuiConfigEntries owningEntryList,
                                       IConfigElement configElement) {
                super(owningScreen, owningEntryList, configElement);
            }

            /**
             * Puts all of the properties on the screen from the category
             */
            @Override
            protected GuiScreen buildChildScreen() {
                Configuration config = ConfigHandler.getConfig();
                ConfigElement categoryBlocks = new ConfigElement(config.getCategory(Configuration.CATEGORY_GENERAL));
                List<IConfigElement> propertiesOnScreen = categoryBlocks.getChildElements();
                String windowTitle = I18n.format("General Settings");
                return new GuiConfig(owningScreen, propertiesOnScreen, owningScreen.modID, this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart, this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart, windowTitle);
            }

        }

    }

    public boolean hasConfigGui() {
        return true;
    }

    public GuiScreen createConfigGui(GuiScreen parentScreen) {
        return new ConfigHandlerGui(parentScreen);
    }

}