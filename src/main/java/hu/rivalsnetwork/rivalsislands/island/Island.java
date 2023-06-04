package hu.rivalsnetwork.rivalsislands.island;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Island {
    @BsonProperty(value = "owner_name")
    private String ownerName;
    @BsonProperty(value = "island_name")
    private String islandName;
    @BsonProperty(value = "owner_uuid")
    private UUID ownerUUID;
    private int x_max = 150;
    private int x_min = 150;
    private int z_max = 150;
    private int z_min = 150;

    public Island(@NotNull final Player owner) {
        this.ownerName = owner.getName();
        this.ownerUUID = owner.getUniqueId();
    }
}
