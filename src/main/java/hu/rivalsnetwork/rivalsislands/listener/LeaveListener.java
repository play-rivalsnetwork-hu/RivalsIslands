package hu.rivalsnetwork.rivalsislands.listener;

import hu.rivalsnetwork.rivalsislands.RivalsIslandsPlugin;
import hu.rivalsnetwork.rivalsislands.aswm.SlimeWorldManager;
import hu.rivalsnetwork.rivalsislands.database.Executor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LeaveListener implements Listener {
    private static final List<String> serializing = new ArrayList<>(256);

    public static boolean contains(@NotNull final String name) {
        return serializing.contains(name);
    }

    // Ensure being called last so every other listener has finished their serialisation
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuitEvent(@NotNull final PlayerQuitEvent event) {
        serializing.add(event.getPlayer().getName());
        RivalsIslandsPlugin.scheduler().runLater(() -> {
            Bukkit.unloadWorld(event.getPlayer().getName() + "-profile-" + Executor.getCurrentIsland(event.getPlayer()), true);
            try {
                SlimeWorldManager.getLoader().unlockWorld(event.getPlayer().getName() + "-profile-1");
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            serializing.remove(event.getPlayer().getName());
        }, 20);
    }
}
