package me.syfe.srp.gui.helpers;

public abstract class AbstractScreen extends ButtonTooltip implements ScreenHelper {
    protected int getCenter() {
        return width / 2;
    }
}
