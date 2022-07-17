package gg.habibi.worlds.config;

import me.lucko.helper.toml.TomlConfig;

public class SettingConf extends TomlConfig {

    public String defaultWorld = "realms";
    public int worldChunkSize = 3;
    public int spacePerWorld = 63;
}
