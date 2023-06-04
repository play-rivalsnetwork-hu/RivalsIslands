package hu.rivalsnetwork.rivalsislands.aswm;

import com.infernalsuite.aswm.api.SlimePlugin;
import com.infernalsuite.aswm.api.loaders.SlimeLoader;
import com.infernalsuite.aswm.api.world.SlimeWorld;
import hu.rivalsnetwork.rivalsislands.RivalsIslandsPlugin;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class SlimeWorldManager {
    private static final List<SlimeWorld> worlds = new ArrayList<>(128);
    private static SlimePlugin swmPlugin;
    private static SlimeLoader loader;

    public static void load() {
        swmPlugin = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");
        loader = swmPlugin.getLoader(RivalsIslandsPlugin.CONFIG.getString("loader"));
    }

    public static SlimeLoader getLoader() {
        return loader;
    }

    public static void add(SlimeWorld world) {
        worlds.add(world);
    }

    public static void remove(SlimeWorld world) {
        worlds.remove(world);
    }

    public static List<SlimeWorld> getWorlds() {
        return worlds;
    }

    public static SlimePlugin getPlugin() {
        return swmPlugin;
    }
}
