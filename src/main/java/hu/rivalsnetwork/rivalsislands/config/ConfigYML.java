package hu.rivalsnetwork.rivalsislands.config;

import hu.rivalsnetwork.rivalsapi.config.Config;
import hu.rivalsnetwork.rivalsislands.RivalsIslandsPlugin;

public class ConfigYML extends Config {

    public ConfigYML() {
        super(RivalsIslandsPlugin.getInstance(), "config.yml");
    }
}
