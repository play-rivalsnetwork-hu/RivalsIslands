package hu.rivalsnetwork.rivalsislands.creator;

import com.infernalsuite.aswm.api.world.SlimeWorld;
import com.infernalsuite.aswm.api.world.properties.SlimeProperties;
import com.infernalsuite.aswm.api.world.properties.SlimePropertyMap;
import hu.rivalsnetwork.rivalsislands.RivalsIslandsPlugin;
import hu.rivalsnetwork.rivalsislands.aswm.SlimeWorldManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

public class WorldCreator {

    public static void load(@NotNull String worldName, @NotNull WorldCallback callback) throws Exception {
        if (SlimeWorldManager.getLoader().worldExists(worldName)) {
            if (Bukkit.getWorld(worldName) != null) {
                Bukkit.unloadWorld(worldName, true);
            }

            RivalsIslandsPlugin.scheduler().runAsync(() -> {
                SlimePropertyMap propertyMap = new SlimePropertyMap();
                propertyMap.setValue(SlimeProperties.PVP, false);
                propertyMap.setValue(SlimeProperties.ALLOW_ANIMALS, false);
                propertyMap.setValue(SlimeProperties.ALLOW_MONSTERS, false);
                propertyMap.setValue(SlimeProperties.DIFFICULTY, "normal");

                RivalsIslandsPlugin.scheduler().run(() -> {
                    SlimeWorld world;
                    try {
                        world = SlimeWorldManager.getPlugin().loadWorld(SlimeWorldManager.getLoader(), worldName, false, propertyMap);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        return;
                    }

                    SlimeWorldManager.getPlugin().loadWorld(world);
                    Bukkit.getWorld(worldName).setAutoSave(false);
                    callback.accept(Bukkit.getWorld(worldName));
                });
            });
        } else {
            RivalsIslandsPlugin.scheduler().runAsync(() -> {
                SlimePropertyMap propertyMap = new SlimePropertyMap();
                propertyMap.setValue(SlimeProperties.PVP, false);
                propertyMap.setValue(SlimeProperties.ALLOW_ANIMALS, false);
                propertyMap.setValue(SlimeProperties.ALLOW_MONSTERS, false);
                propertyMap.setValue(SlimeProperties.DIFFICULTY, "normal");

                RivalsIslandsPlugin.scheduler().run(() -> {
                    SlimeWorld world;
                    try {
                        world = SlimeWorldManager.getPlugin().loadWorld(SlimeWorldManager.getLoader(), worldName, false, propertyMap);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        return;
                    }

                    SlimeWorldManager.getPlugin().loadWorld(world);
                    Bukkit.getWorld(worldName).setAutoSave(false);
                    callback.accept(Bukkit.getWorld(worldName));
                });
            });
        }
    }

    public interface WorldCallback {
        void accept(World world);
    }
}
