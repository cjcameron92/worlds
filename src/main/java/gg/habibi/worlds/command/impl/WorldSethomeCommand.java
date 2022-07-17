package gg.habibi.worlds.command.impl;

import gg.habibi.worlds.command.AbstractWorldSubCommand;
import gg.habibi.worlds.command.WorldSubCommand;
import gg.habibi.worlds.config.MessageConf;
import me.lucko.helper.Commands;
import me.lucko.helper.command.Command;
import me.lucko.helper.serialize.Point;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static gg.habibi.worlds.Worlds.*;

public class WorldSethomeCommand extends AbstractWorldSubCommand {

    private final Command command;

    public WorldSethomeCommand(MessageConf messageConf) {
        super("sethome", "setspawn");
        this.command = Commands.create().assertPlayer().handler(context -> {
           final Player player = context.sender();
            from(player.getUniqueId()).ifPresent(world -> {
                world.setSpawn(Point.of(player.getLocation()));
                context.reply(messageConf.worldSetSpawnMessage);
            });
        });
    }

    @Override public @NotNull Command getCommand() {
        return command;
    }
}
