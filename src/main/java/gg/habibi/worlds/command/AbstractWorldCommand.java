package gg.habibi.worlds.command;

import me.lucko.helper.Commands;
import me.lucko.helper.command.Command;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractWorldCommand implements WorldCommand {

    private final String[] aliases;
    private final Set<WorldSubCommand> subCommands;
    private final Command command;

    public AbstractWorldCommand(String... aliases) {
        this.aliases = aliases;
        this.subCommands = new HashSet<>();
        this.command = Commands.create().handler(context -> {
            for (WorldSubCommand subCommand : getSubCommands()) {
                for (String name : subCommand.getAliases()) {
                    if (!name.equals(context.rawArg(0))) continue;
                    subCommand.getCommand().call(context);
                    return;
                }
            }
            context.reply(getHelpMessage());
        });
    }

    @NotNull @Override public Command getCommand() {
        return command;
    }

    @NotNull @Override public String[] getAliases() {
        return aliases;
    }

    @NotNull @Override public Set<WorldSubCommand> getSubCommands() {
        return subCommands;
    }
}
