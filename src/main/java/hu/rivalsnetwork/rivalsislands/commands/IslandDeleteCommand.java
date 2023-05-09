package hu.rivalsnetwork.rivalsislands.commands;

import dev.jorel.commandapi.CommandTree;
import hu.rivalsnetwork.rivalsapi.commands.Command;
import hu.rivalsnetwork.rivalsislands.database.Executor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class IslandDeleteCommand extends Command {
    public IslandDeleteCommand() {
        super("deleteis");
    }

    @Override
    public void register() {
        new CommandTree("deleteis")
                .executesPlayer(executionInfo -> {
                    World world = Bukkit.getWorld(executionInfo.sender().getName() + "-profile-" + Executor.getCurrentIsland(executionInfo.sender()));
                    for (Player player : world.getPlayers()) {
                        player.teleport(new Location(Bukkit.getWorld("world"), 0, 100, 0));
                    }

                    Bukkit.unloadWorld(world, false);
                    Executor.deleteIslands(executionInfo.sender());
                })
                .register();
    }
}
