package hu.rivalsnetwork.rivalsislands;

import hu.rivalsnetwork.rivalsapi.RivalsAPI;
import hu.rivalsnetwork.rivalsapi.RivalsAPIPlugin;
import hu.rivalsnetwork.rivalsapi.config.Config;
import hu.rivalsnetwork.rivalsapi.utils.Scheduler;
import hu.rivalsnetwork.rivalsislands.aswm.SlimeWorldManager;
import hu.rivalsnetwork.rivalsislands.config.ConfigYML;
import hu.rivalsnetwork.rivalsislands.listener.JoinListener;
import hu.rivalsnetwork.rivalsislands.listener.LeaveListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class RivalsIslandsPlugin extends JavaPlugin {
    private static RivalsIslandsPlugin plugin;
    private static RivalsAPI api;
    private static Scheduler scheduler;
    private static Config config;

    @Override
    public void onEnable() {
        plugin = this;
        api = RivalsAPIPlugin.getApi();
        scheduler = new Scheduler(this);

        config = new ConfigYML();
        SlimeWorldManager.load();

        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new LeaveListener(), this);
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
}
