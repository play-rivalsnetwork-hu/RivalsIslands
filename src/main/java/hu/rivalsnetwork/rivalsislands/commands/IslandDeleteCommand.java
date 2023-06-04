package hu.rivalsnetwork.rivalsislands.commands;

import hu.rivalsnetwork.rivalsapi.commands.Command;

public class IslandDeleteCommand {


    @Command
    public void register() {
//        new CommandTree("deleteis")
//                .executesPlayer(executionInfo -> {
//                    Menu menu = new Menu(StringUtils.format("&cSziget törlése"), 27);
//                    menu.setItem(13, new GuiItem(new ItemStack(Material.GREEN_DYE), event -> {
//                        event.setCancelled(true);
//                        event.getInventory().close();
//                        World world = Bukkit.getWorld(executionInfo.sender().getName() + "-profile-" + Executor.getCurrentIsland(executionInfo.sender()));
//                        for (Player player : world.getPlayers()) {
//                            player.teleport(new Location(Bukkit.getWorld("world"), 0, 100, 0));
//                        }
//
//                        Bukkit.unloadWorld(world, false);
//                        Executor.deleteIslands(executionInfo.sender());
//                        try {
//                            SlimeWorldManager.getLoader().deleteWorld(world.getName());
//                        } catch (Exception exception) {
//                            exception.printStackTrace();
//                        }
//                    }));
//
//                    menu.open(executionInfo.sender());
//                })
//                .register();
    }
}
