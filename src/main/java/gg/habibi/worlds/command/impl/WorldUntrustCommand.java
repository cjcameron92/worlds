package gg.habibi.worlds.command.impl;

import gg.habibi.worlds.command.AbstractWorldSubCommand;
import me.lucko.helper.Commands;
import me.lucko.helper.command.Command;
import me.lucko.helper.command.CommandInterruptException;
import me.lucko.helper.text3.Text;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static gg.habibi.worlds.Worlds.*;

public class WorldUntrustCommand extends AbstractWorldSubCommand {

    private final Command command;

    public WorldUntrustCommand() {
        super("untrust", "remove");
        this.command = Commands.create().assertPlayer().handler(context -> {
           final Player player = context.sender();
           from(player.getUniqueId()).ifPresent(world -> {
               try {
                   final Player target = context.arg(1).parseOrFail(Player.class);
                   if (player.equals(target)) {
                       context.reply("&cYou cannot untrust yourself!");
                       return;
                   }

                   if (!world.getTrusted().contains(target.getUniqueId())) {
                       context.reply("&cThis player is not trusted.");
                       return;
                   }

                   world.untrust(target.getUniqueId());
                   context.reply("&aYou have untrusted " + target.getName() + " from your world.");
                   target.sendMessage(Text.colorize("&aYou have been untrusted from " + player.getName() + "'s world."));

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
