package gg.habibi.worlds.command;

import gg.habibi.worlds.Worlds;
import gg.habibi.worlds.config.MessageConf;
import me.lucko.helper.Commands;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import me.lucko.helper.text3.Text;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WorldCommand implements TerminableModule {

    private final MessageConf messageConf;

    public WorldCommand(MessageConf messageConf) {
        this.messageConf = messageConf;
    }

    @Override public void setup(@NotNull TerminableConsumer consumer) {
        Commands.create().assertPlayer().handler(context -> {
            final Player player = context.sender();
            Worlds.getWorldRegistry().get(player.getUniqueId()).ifPresent(world -> {
                player.sendMessage(Text.colorize(messageConf.playerTeleportWorldSpawn));
                player.teleport(world.getSpawn().toLocation());
            });
        }).registerAndBind(consumer, "world", "realm");
    }
}
