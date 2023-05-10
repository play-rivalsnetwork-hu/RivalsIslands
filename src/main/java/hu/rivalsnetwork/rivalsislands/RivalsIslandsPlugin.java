package hu.rivalsnetwork.rivalsislands;

import com.infernalsuite.aswm.api.world.SlimeWorld;
import hu.rivalsnetwork.rivalsapi.RivalsAPI;
import hu.rivalsnetwork.rivalsapi.RivalsAPIPlugin;
import hu.rivalsnetwork.rivalsapi.config.Config;
import hu.rivalsnetwork.rivalsapi.schematic.Schematic;
import hu.rivalsnetwork.rivalsapi.utils.Scheduler;
import hu.rivalsnetwork.rivalsislands.aswm.SlimeWorldManager;
import hu.rivalsnetwork.rivalsislands.commands.IslandDeleteCommand;
import hu.rivalsnetwork.rivalsislands.commands.NewIslandCommand;
import hu.rivalsnetwork.rivalsislands.commands.SchematicCommand;
import hu.rivalsnetwork.rivalsislands.config.ConfigYML;
import hu.rivalsnetwork.rivalsislands.config.LangYML;
import hu.rivalsnetwork.rivalsislands.listener.JoinListener;
import hu.rivalsnetwork.rivalsislands.listener.LeaveListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class RivalsIslandsPlugin extends JavaPlugin {
    private static RivalsIslandsPlugin plugin;
    private static RivalsAPI api;
    private static Scheduler scheduler;
    private static Config config;
    private static Config lang;
    private static Schematic islandSchematic;

    @Override
    public void onEnable() {
        plugin = this;
        api = RivalsAPIPlugin.getApi();
        scheduler = new Scheduler(this);

        config = new ConfigYML();
        lang = new LangYML();
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

    public static RivalsAPI getApi() {
        return api;
    }

    public static Scheduler scheduler() {
        return scheduler;
    }

    public static RivalsIslandsPlugin getInstance() {
        return plugin;
    }

    public static Config config() {
        return config;
    }

    public static Config lang() {
        return lang;
    }

    public static Schematic getIslandSchematic() {
        return islandSchematic;
    }
}
