package me.syfe.srp.gui.helpers;

import net.minecraft.util.EnumChatFormatting;

public interface ScreenHelper {

    default String getSuffix(boolean option, String label) {
        return option ? EnumChatFormatting.GREEN + label : EnumChatFormatting.RED + label;
    }

    default int getRowPos(int rowNumber) {
        return 55 + rowNumber * 23;
    }

}
