package gg.habibi.worlds.command.impl;

import gg.habibi.worlds.command.AbstractWorldCommand;
import gg.habibi.worlds.config.MessageConf;
import org.jetbrains.annotations.NotNull;

public class DefaultWorldCommand extends AbstractWorldCommand {

    private final String[] helpMessage = new String[] {
            "&a&lWorld Commands",
            "&7/world home",
            "&7/world sethome"
    };

    public DefaultWorldCommand(@NotNull MessageConf messageConf) {
        super("world", "realm");

        add(new WorldSethomeCommand(messageConf));
        add(new WorldHomeCommand(messageConf));
        add(new WorldTrustCommand());
        add(new WorldUntrustCommand());
        add(new WorldSettingCommand());
        add(new WorldChatCommand());
    }

    @Override public @NotNull String[] getHelpMessage() {
        return helpMessage;
    }
}
