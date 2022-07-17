package gg.habibi.worlds.config;

import me.lucko.helper.toml.TomlConfig;

public class MessageConf extends TomlConfig {

    public String noInteractionAccess = "&cYou can only interact with worlds you own or trusted in.";
    public String noPickupAccess = "&cYou can only pickup items in worlds you own or trusted in.";
    public String noDamageAnimalsAccess = "&cYou can only damage animals in worlds you own or trusted in.";
    public String pvpDisabled = "&cYou can't damage other players.";



    public String worldCreationMessage = "&aYour world has been created.";

    public String playerTeleportWorldSpawn = "&aTeleporting you to your world...";

}
