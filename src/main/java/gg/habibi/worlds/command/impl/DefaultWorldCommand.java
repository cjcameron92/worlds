package gg.habibi.worlds.command.impl;

import gg.habibi.worlds.command.AbstractWorldCommand;
import gg.habibi.worlds.config.MessageConf;
import org.jetbrains.annotations.NotNull;

public class DefaultWorldCommand extends AbstractWorldCommand {

    private final String[] helpMessage = new String[] {
            "&a&lWorld Commands"
    };

    public DefaultWorldCommand(@NotNull MessageConf messageConf) {
        super("world", "realm");

        add(new WorldSethomeCommand(messageConf));
    }

    @Override public @NotNull String[] getHelpMessage() {
        return helpMessage;
    }
}
