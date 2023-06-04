package hu.rivalsnetwork.rivalsislands.database;

import hu.rivalsnetwork.rivalsapi.users.Key;
import hu.rivalsnetwork.rivalsapi.users.User;
import hu.rivalsnetwork.rivalsislands.RivalsIslandsPlugin;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Executor {
    private static final List<String> profileNames = Arrays.asList("Blueberry", "Mango", "Kiwi", "Lemon", "Watermelon", "Strawberry");

    public static String newIsland(Player player) {
        List<String> profiles = new ArrayList<>(20);
        String name = profileNames.get(ThreadLocalRandom.current().nextInt(profileNames.size()));
        User user = RivalsIslandsPlugin.getInstance().getUser(player);

        if (hasIsland(player)) {
            profiles = (List<String>) user.read(Key.of("island-data", "islands"), User.DataType.MONGODB);
        }
        profiles.add(name);
        user.write("island-data", User.DataType.MONGODB, Key.of("islands", profiles), Key.of("current-island", name), Key.of("name", player.getName()));

        return name;
    }

    @NotNull
    public static String getCurrentIsland(Player player) {
        User user = RivalsIslandsPlugin.getInstance().getUser(player);
        String islandName = (String) user.read(Key.of("island-data", "current-island"), User.DataType.MONGODB);

        if (islandName == null) {
            islandName = "Banana";
        }

        return islandName;
    }

    public static boolean hasIsland(Player player) {
        boolean hasIsland = false;
        User user = RivalsIslandsPlugin.getInstance().getUser(player);
        String uuid = (String) user.read(Key.of("island-data", "uuid"), User.DataType.MONGODB);
        if (uuid != null) {
            hasIsland = true;
        }

        return hasIsland;
    }

    public static void deleteIslands(Player player) {
        User user = RivalsIslandsPlugin.getInstance().getUser(player);
        user.delete("island-data", User.DataType.MONGODB);
        user.delete("inventory-data", User.DataType.MONGODB, List.of(Key.of("profile", getCurrentIsland(player))));
    }
}
