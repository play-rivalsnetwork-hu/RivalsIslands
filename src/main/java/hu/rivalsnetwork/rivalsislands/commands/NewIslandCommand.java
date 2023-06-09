package hu.rivalsnetwork.rivalsislands.commands;

import dev.jorel.commandapi.CommandTree;
import hu.rivalsnetwork.rivalsapi.commands.Command;
import hu.rivalsnetwork.rivalsislands.aswm.SlimeWorldManager;
import hu.rivalsnetwork.rivalsislands.creator.WorldCreator;
import hu.rivalsnetwork.rivalsislands.database.Executor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class NewIslandCommand {

    @Command
    public void register() {
        new CommandTree("newis")
                .executesPlayer(executionInfo -> {
                    World world = Bukkit.getWorld(executionInfo.sender().getName() + "-profile-" + Executor.getCurrentIsland(executionInfo.sender()));
                    for (Player player : world.getPlayers()) {
                        player.teleport(new Location(Bukkit.getWorld("world"), 0, 100, 0));
                    }

                    Bukkit.unloadWorld(world, false);
                    Executor.deleteIslands(executionInfo.sender());
                    try {
                        SlimeWorldManager.getLoader().deleteWorld(world.getName());
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                    WorldCreator.load(executionInfo.sender(), newWorld -> {
                        executionInfo.sender().teleportAsync(newWorld.getSpawnLocation());
                    });
                })
                .register();
    }
}
