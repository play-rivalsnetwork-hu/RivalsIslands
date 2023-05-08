package hu.rivalsnetwork.rivalsislands.listener;

import hu.rivalsnetwork.rivalsislands.aswm.SlimeWorldManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class LeaveListener implements Listener {

    @EventHandler
    public void onPlayerQuitEvent(@NotNull final PlayerQuitEvent event) {
        Bukkit.unloadWorld(event.getPlayer().getName() + "-profile-1", true);
        try {
            SlimeWorldManager.getLoader().unlockWorld(event.getPlayer().getName() + "-profile-1");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
