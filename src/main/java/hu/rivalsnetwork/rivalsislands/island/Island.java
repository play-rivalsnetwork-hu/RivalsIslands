package hu.rivalsnetwork.rivalsislands.island;

import hu.rivalsnetwork.rivalsapi.users.User;
import org.jetbrains.annotations.NotNull;

public class Island {
    private User owner;
    private int x_max = 0;
    private int x_min = 0;
    private int z_max = 0;
    private int z_min = 0;

    public Island(@NotNull final User owner) {
        this.owner = owner;
    }
}
