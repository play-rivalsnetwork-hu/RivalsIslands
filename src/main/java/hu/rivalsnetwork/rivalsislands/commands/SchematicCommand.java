package hu.rivalsnetwork.rivalsislands.commands;

import dev.jorel.commandapi.CommandTree;
import hu.rivalsnetwork.rivalsapi.commands.Command;
import hu.rivalsnetwork.rivalsapi.schematic.Schematic;
import hu.rivalsnetwork.rivalsislands.RivalsIslandsPlugin;

import java.io.File;

public class SchematicCommand extends Command {
    public SchematicCommand() {
        super("schematic");
    }

    @Override
    public void register() {
        new CommandTree("schematic")
                .executesPlayer(info -> {
                    Schematic schematic = new Schematic(new File(RivalsIslandsPlugin.getInstance().getDataFolder(), "island.schem"));
                    schematic.paste(info.sender().getLocation());
                })
                .register();
    }
}
