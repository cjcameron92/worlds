package gg.habibi.worlds.command.impl;

import gg.habibi.worlds.command.AbstractWorldSubCommand;
import gg.habibi.worlds.config.MessageConf;
import me.lucko.helper.Commands;
import me.lucko.helper.command.Command;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static gg.habibi.worlds.Worlds.*;

public class WorldHomeCommand extends AbstractWorldSubCommand {

    private final Command command;

    public WorldHomeCommand(@NotNull MessageConf messageConf) {
        super("home", "spawn");
        this.command = Commands.create().assertPlayer().handler(context -> {
            final Player player = context.sender();
            from(player.getUniqueId()).ifPresent(world -> {
                player.teleport(world.getSpawn().toLocation());
                context.reply(messageConf.playerTeleportWorldSpawn);
            });
        });
    }

    @NotNull @Override public Command getCommand() {
        return command;
    }
}
