package hu.rivalsnetwork.rivalsislands;

import com.infernalsuite.aswm.api.world.SlimeWorld;
import hu.rivalsnetwork.rivalsapi.config.Config;
import hu.rivalsnetwork.rivalsapi.config.ConfigType;
import hu.rivalsnetwork.rivalsapi.config.Configuration;
import hu.rivalsnetwork.rivalsapi.plugin.RivalsPluginImpl;
import hu.rivalsnetwork.rivalsapi.schematic.Schematic;
import hu.rivalsnetwork.rivalsislands.aswm.SlimeWorldManager;
import hu.rivalsnetwork.rivalsislands.commands.IslandDeleteCommand;
import hu.rivalsnetwork.rivalsislands.commands.NewIslandCommand;
import hu.rivalsnetwork.rivalsislands.commands.SchematicCommand;
import hu.rivalsnetwork.rivalsislands.listener.JoinListener;
import hu.rivalsnetwork.rivalsislands.listener.LeaveListener;
import org.bukkit.Bukkit;

import java.io.File;

public class RivalsIslandsPlugin extends RivalsPluginImpl {
    private static RivalsIslandsPlugin plugin;
    @Configuration(configType = ConfigType.YAML, name = "config")
    public static Config CONFIG;
    @Configuration(configType = ConfigType.YAML, name = "lang")
    public static Config LANG;
    private static Schematic islandSchematic;

    @Override
    public void onEnable() {
        plugin = this;
        SlimeWorldManager.load();

        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new LeaveListener(), this);

        islandSchematic = new Schematic(new File(this.getDataFolder(), "schematics/island.schem"));
        new IslandDeleteCommand().register();
        new SchematicCommand().register();
        new NewIslandCommand().register();
    }

    @Override
    public void onDisable() {
        for (SlimeWorld world : SlimeWorldManager.getWorlds()) {
            Bukkit.unloadWorld(world.getName(), true);
            try {
                SlimeWorldManager.getLoader().unlockWorld(world.getName());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public static RivalsIslandsPlugin getInstance() {
        return plugin;
    }

    public static Schematic getIslandSchematic() {
        return islandSchematic;
    }
}
