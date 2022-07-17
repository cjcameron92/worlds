package gg.habibi.worlds.command.impl;

import gg.habibi.worlds.command.AbstractWorldSubCommand;
import me.lucko.helper.Commands;
import me.lucko.helper.command.Command;
import me.lucko.helper.text3.Text;
import me.lucko.helper.utils.Players;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static gg.habibi.worlds.Worlds.*;

public class WorldChatCommand extends AbstractWorldSubCommand {

    private final Command command;

    public WorldChatCommand() {
        super("chat");
        this.command = Commands.create().assertPlayer().handler(context -> {

            if (context.args().size() < 1) {
                context.reply("&cYou cannot send an empty message.");
                return;
            }

            from(context.sender().getUniqueId()).ifPresent(world -> {
                final String message = getMessage(context.args());
                if (message.isEmpty()) {
                    context.reply("&cYou cannot send an empty message.");
                    return;
                }
                final String format = String.format("&a[%s World] %s: %s", world.getOwnerName(), context.sender().getName(), message);
                context.reply(format);
                world.getTrusted().forEach(uuid -> {
                    if (!uuid.equals(context.sender().getUniqueId())) {
                        Players.get(uuid).ifPresent(player -> player.sendMessage(Text.colorize(format)));
                    }
                });
            });
        });
    }

    public String getMessage(List<String> args) {
        if (args.size() < 1) return "";
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < args.size(); i++) {
            stringBuilder.append(args.get(i)).append(" ");
        }
        return stringBuilder.toString();
    }

    @Override public @NotNull Command getCommand() {
        return command;
    }
}
