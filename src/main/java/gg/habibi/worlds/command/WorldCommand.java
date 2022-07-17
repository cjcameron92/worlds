package gg.habibi.worlds.command;

import me.lucko.helper.command.Command;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface WorldCommand {

    @NotNull Command getCommand();

    @NotNull String[] getAliases();

    @NotNull String[] getHelpMessage();

    @NotNull Set<WorldSubCommand> getSubCommands();

    default void add(@NotNull WorldSubCommand command) {
        getSubCommands().add(command);
    }
}
