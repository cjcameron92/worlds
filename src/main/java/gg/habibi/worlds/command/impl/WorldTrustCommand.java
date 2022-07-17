package gg.habibi.worlds.command.impl;

import gg.habibi.worlds.command.AbstractWorldSubCommand;
import me.lucko.helper.Commands;
import me.lucko.helper.command.Command;
import me.lucko.helper.command.CommandInterruptException;
import me.lucko.helper.text3.Text;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static gg.habibi.worlds.Worlds.*;

public class WorldTrustCommand extends AbstractWorldSubCommand {

    private final Command command;

    public WorldTrustCommand() {
        super("trust", "add");
        this.command = Commands.create().assertPlayer().handler(context -> {
            final Player player = context.sender();

            from(player.getUniqueId()).ifPresent(world -> {
                try {
                    final Player target = context.arg(1).parseOrFail(Player.class);
                    if (player.equals(target)) {
                        context.reply("&cYou cannot trust yourself!");
                        return;
                    }

                    if (world.getTrusted().contains(target.getUniqueId())) {
                        context.reply("&cThis player is already trusted!");
                        return;
                    }

                    world.trust(target.getUniqueId());
                    context.reply("&aYou have trusted " + target.getName() + " to your world.");
                    target.sendMessage(Text.colorize("&aYou have been trusted to " + player.getName() + "'s world."));

                } catch (CommandInterruptException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    @NotNull @Override public Command getCommand() {
        return command;
    }
}
