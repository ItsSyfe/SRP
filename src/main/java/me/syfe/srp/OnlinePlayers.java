package me.syfe.srp;

import net.minecraft.client.network.NetworkPlayerInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OnlinePlayers {
    public static String[] getListOfPlayerUsernames() {
        String[] users = null;
        Collection<NetworkPlayerInfo> players = SRP.mc.getNetHandler().getPlayerInfoMap();
        List<String> list = new ArrayList<String>();
        for (NetworkPlayerInfo info : players)
            list.add(info.getGameProfile().getName());
        return list.<String>toArray(new String[0]);
    }
}
