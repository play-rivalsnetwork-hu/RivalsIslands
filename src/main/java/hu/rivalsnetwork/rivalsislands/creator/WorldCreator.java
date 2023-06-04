package hu.rivalsnetwork.rivalsislands.creator;

import com.infernalsuite.aswm.api.world.SlimeWorld;
import com.infernalsuite.aswm.api.world.properties.SlimeProperties;
import com.infernalsuite.aswm.api.world.properties.SlimePropertyMap;
import hu.rivalsnetwork.rivalsislands.RivalsIslandsPlugin;
import hu.rivalsnetwork.rivalsislands.aswm.SlimeWorldManager;
import hu.rivalsnetwork.rivalsislands.database.Executor;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WorldCreator {

    public static void load(@NotNull Player player, @NotNull WorldCallback callback) {
        if (Executor.hasIsland(player)) {
            String worldName = player.getName() + "-profile-" + Executor.getCurrentIsland(player);

            RivalsIslandsPlugin.getInstance().scheduler().runAsync(() -> {
                SlimePropertyMap propertyMap = new SlimePropertyMap();
                propertyMap.setValue(SlimeProperties.PVP, false);
                propertyMap.setValue(SlimeProperties.ALLOW_ANIMALS, false);
                propertyMap.setValue(SlimeProperties.ALLOW_MONSTERS, false);
                propertyMap.setValue(SlimeProperties.DIFFICULTY, "normal");
                SlimeWorld world;
                try {
                    world = SlimeWorldManager.getPlugin().loadWorld(SlimeWorldManager.getLoader(), worldName, false, propertyMap);
                    SlimeWorldManager.add(world);
                } catch (Exception exception) {
                    exception.printStackTrace();
                    return;
                }

                RivalsIslandsPlugin.getInstance().scheduler().run(() -> {
                    SlimeWorldManager.getPlugin().loadWorld(world);
                    setGameRules(Bukkit.getWorld(worldName));
                    callback.accept(Bukkit.getWorld(worldName));
                });
            });
        } else {
            String islandName = Executor.newIsland(player);
            String worldName = player.getName() + "-profile-" + islandName;

            RivalsIslandsPlugin.getInstance().scheduler().runAsync(() -> {
                SlimePropertyMap propertyMap = new SlimePropertyMap();
                propertyMap.setValue(SlimeProperties.PVP, false);
                propertyMap.setValue(SlimeProperties.ALLOW_ANIMALS, false);
                propertyMap.setValue(SlimeProperties.ALLOW_MONSTERS, false);
                propertyMap.setValue(SlimeProperties.DIFFICULTY, "normal");
                SlimeWorld world;
                try {
                    world = SlimeWorldManager.getPlugin().createEmptyWorld(SlimeWorldManager.getLoader(), worldName, false, propertyMap);
                    SlimeWorldManager.add(world);
                } catch (Exception exception) {
                    exception.printStackTrace();
                    return;
                }

                RivalsIslandsPlugin.getInstance().scheduler().run(() -> {
                    SlimeWorldManager.getPlugin().loadWorld(world);
                    setGameRules(Bukkit.getWorld(worldName));
                    RivalsIslandsPlugin.getIslandSchematic().paste(new Location(Bukkit.getWorld(worldName), 0, 20, 0), false);
                    callback.accept(Bukkit.getWorld(worldName));
                });
            });
        }
    }

    private static void setGameRules(@Nullable World world) {
        if (world == null) return;
        world.setAutoSave(false);
        world.setKeepSpawnInMemory(false);
        world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        world.setGameRule(GameRule.DO_TRADER_SPAWNING, false);
        world.setGameRule(GameRule.DISABLE_RAIDS, true);
        world.setGameRule(GameRule.DO_INSOMNIA, false);
        world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
        world.setGameRule(GameRule.DO_FIRE_TICK, false);
        world.setGameRule(GameRule.MOB_GRIEFING, false);
        world.setGameRule(GameRule.MAX_ENTITY_CRAMMING, 1);
    }

    public interface WorldCallback {
        void accept(World world);
    }
}
