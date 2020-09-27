package me.syfe.srp.gui;

import me.syfe.srp.config.ConfigHandler;
import me.syfe.srp.gui.helpers.AbstractScreen;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;

public class SRPGui extends AbstractScreen {

    @Override
    public void initGui() {
        buttonList.add(new GuiButton(0, getCenter() - 155, getRowPos(1), 150, 20,
                getSuffix(ConfigHandler.renderPlayers, "Render Players")));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawCenteredString(mc.fontRendererObj, "SRP", getCenter(), getRowPos(0), -1);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0:
                ConfigHandler.renderPlayers = !ConfigHandler.renderPlayers;
                button.displayString = getSuffix(ConfigHandler.renderPlayers, "Render Players");
                break;
        }
    }

    @Override
    protected String getButtonTooltip(int buttonId) {
        switch (buttonId) {
            case 0:
                return I18n.format("gui.description.renderplayers");
        }

        return null;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void onGuiClosed() {
        ConfigHandler.syncFromFields();
    }
}
