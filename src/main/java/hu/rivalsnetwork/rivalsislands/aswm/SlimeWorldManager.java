package hu.rivalsnetwork.rivalsislands.aswm;

import com.infernalsuite.aswm.api.SlimePlugin;
import com.infernalsuite.aswm.api.loaders.SlimeLoader;
import hu.rivalsnetwork.rivalsislands.RivalsIslandsPlugin;
import org.bukkit.Bukkit;

public class SlimeWorldManager {
    private static SlimePlugin swmPlugin;
    private static SlimeLoader loader;

    public static void load() {
        swmPlugin = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");
        loader = swmPlugin.getLoader(RivalsIslandsPlugin.config().getString("loader"));
    }

    public static SlimeLoader getLoader() {
        return loader;
    }

    public static SlimePlugin getPlugin() {
        return swmPlugin;
    }
}
