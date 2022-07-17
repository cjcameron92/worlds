package gg.habibi.worlds.listener;

import gg.habibi.worlds.api.World;
import gg.habibi.worlds.config.MessageConf;
import gg.habibi.worlds.config.SettingConf;
import gg.habibi.worlds.event.WorldCreateEvent;
import gg.habibi.worlds.event.WorldEnterEvent;
import gg.habibi.worlds.util.BorderUtil;
import me.lucko.helper.Events;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import me.lucko.helper.text3.Text;
import me.lucko.helper.utils.Players;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static gg.habibi.worlds.Worlds.*;

public class WorldListener implements TerminableModule {

    private final MessageConf messageConf;
    private final SettingConf settingConf;

    public WorldListener(MessageConf messageConf, SettingConf settingConf) {
        this.messageConf = messageConf;
        this.settingConf = settingConf;
    }

    @Override public void setup(@NotNull TerminableConsumer consumer) {
        Events.subscribe(WorldCreateEvent.class).handler(event -> {
            final World world = event.getWorld();
            final UUID owner = world.getOwner();

            Players.get(owner).ifPresent(player -> {
                player.sendMessage(Text.colorize(messageConf.worldCreationMessage));
                player.teleport(world.getSpawn().toLocation());
                BorderUtil.sendBorder(world, player);
            });
        }).bindWith(consumer);

        Events.subscribe(WorldEnterEvent.class).handler(event -> BorderUtil.sendBorder(event.getWorld(), event.getEntering())).bindWith(consumer);

        Events.subscribe(BlockPistonExtendEvent.class).handler(event -> {
            final Block block = event.getBlock();
            final Block target = block.getRelative(event.getDirection(), event.getBlocks().size() + 1);
            if (target != null && !target.isEmpty() && !target.isLiquid()) {
                return;
            }

            if (from(target).isEmpty())
                event.setCancelled(true);

        }).bindWith(consumer);

        Events.subscribe(BlockFromToEvent.class).handler(event -> {
            final Block from = event.getBlock();
            if (from(from).isEmpty())
                event.setCancelled(true);
        }).bindWith(consumer);
    }
}
