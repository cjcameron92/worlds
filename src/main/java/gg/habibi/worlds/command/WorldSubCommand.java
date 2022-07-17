package gg.habibi.worlds.command;

import me.lucko.helper.command.Command;
import org.jetbrains.annotations.NotNull;

public interface WorldSubCommand {

    @NotNull Command getCommand();

    @NotNull String[] getAliases();
}
