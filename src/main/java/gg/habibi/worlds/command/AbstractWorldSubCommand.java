package gg.habibi.worlds.command;

import org.jetbrains.annotations.NotNull;

public abstract class AbstractWorldSubCommand implements WorldSubCommand {

    private final String[] aliases;

    public AbstractWorldSubCommand(String... aliases) {
        this.aliases = aliases;
    }

    @NotNull @Override public String[] getAliases() {
        return aliases;
    }
}
