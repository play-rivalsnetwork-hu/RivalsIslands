package hu.rivalsnetwork.rivalsislands.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.BooleanArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import hu.rivalsnetwork.rivalsapi.commands.Command;
import hu.rivalsnetwork.rivalsapi.schematic.Schematic;
import hu.rivalsnetwork.rivalsislands.RivalsIslandsPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class SchematicCommand {

    public String[] getSchematicFiles() {
        final File[] files = new File(RivalsIslandsPlugin.getInstance().getDataFolder(), "/schematics").listFiles();
        ArrayList<String> schematics = new ArrayList<>();

        if (files == null) return schematics.toArray(new String[0]);
        for (File file : files) {
            schematics.add(file.getName());
        }

        return schematics.toArray(new String[0]);
    }

    @Command
    public void register() {
        new CommandTree("schematic")
                .withPermission("rivalsisland.schematic")
                .then(new StringArgument("file")
                        .replaceSuggestions(ArgumentSuggestions.stringsAsync(async -> CompletableFuture.supplyAsync(this::getSchematicFiles)))
                        .executesPlayer(info -> {
                            Schematic schematic = new Schematic(new File(RivalsIslandsPlugin.getInstance().getDataFolder(), "schematics/" + info.args().getOrDefault("file", "island.schem")));
                            schematic.paste(info.sender().getLocation(), false);
                        })
                        .then(new BooleanArgument("air")
                                .executesPlayer(info -> {
                                    Schematic schematic = new Schematic(new File(RivalsIslandsPlugin.getInstance().getDataFolder(), "schematics/" + info.args().getOrDefault("file", "island.schem")));
                                    schematic.paste(info.sender().getLocation(), (boolean) info.args().getOrDefault("air", true));
                                })
                        )
                )
                .register();
    }
}
