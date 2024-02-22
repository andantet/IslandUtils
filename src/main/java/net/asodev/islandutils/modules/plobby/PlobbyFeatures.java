package net.asodev.islandutils.modules.plobby;

import net.asodev.islandutils.IslandUtilsClient;
import net.asodev.islandutils.IslandUtilsEvents;
import net.asodev.islandutils.state.MccIslandState;
import net.asodev.islandutils.util.ChatUtils;
import net.asodev.islandutils.util.Sidebar;
import net.asodev.islandutils.util.Utils;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlobbyFeatures {
    public static void registerEvents() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!MccIslandState.isOnline()) return;
            if (!IslandUtilsClient.openPlobbyKey.consumeClick() || client.player == null) return;
            client.player.connection.sendCommand("plobby"); // Do /plobby to open the plobby menu
        });
    }

    private static final Pattern plobbySidebarLine = Pattern.compile("PLOBBY.\\(.");
    public static boolean isInPlobby() {
        Component sidebarName = Sidebar.getSidebarName();
        if (!MccIslandState.isOnline() || sidebarName == null) return false;

        int lineNumber = Sidebar.findLine((line) -> plobbySidebarLine.matcher(line.getString()).find());
        boolean nameContainsPlobby = sidebarName.getString().contains("(Plobby)");
        return lineNumber != -1 || nameContainsPlobby;
    }

}
