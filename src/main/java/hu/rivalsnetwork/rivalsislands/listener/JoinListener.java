package hu.rivalsnetwork.rivalsislands.listener;

import hu.rivalsnetwork.rivalsapi.utils.StringUtils;
import hu.rivalsnetwork.rivalsislands.RivalsIslandsPlugin;
import hu.rivalsnetwork.rivalsislands.creator.WorldCreator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(@NotNull final PlayerJoinEvent event) throws Exception {
        WorldCreator.load(event.getPlayer().getName() + "-profile-1", world -> {
            event.getPlayer().teleportAsync(world.getSpawnLocation());
        });
    }

    @EventHandler
    public void onAsyncPlayerPreLoginEvent(@NotNull final AsyncPlayerPreLoginEvent event) {
        if (LeaveListener.contains(event.getName())) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, StringUtils.formatToComponent(RivalsIslandsPlugin.lang().getString("error.save-in-progress")));
        }
    }
}
