package gg.habibi.worlds.command.impl;

import gg.habibi.worlds.command.AbstractWorldSubCommand;
import gg.habibi.worlds.menu.SettingMenu;
import me.lucko.helper.Commands;
import me.lucko.helper.command.Command;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WorldSettingCommand extends AbstractWorldSubCommand {

    private final Command command;

    public WorldSettingCommand() {
        super("setting", "settings");
        this.command = Commands.create().assertPlayer().handler(context -> {
            final Player player = context.sender();
            new SettingMenu(player).open();
        });
    }

    @NotNull @Override public Command getCommand() {
        return command;
    }
}
