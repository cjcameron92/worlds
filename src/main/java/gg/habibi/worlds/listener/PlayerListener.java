package gg.habibi.worlds.listener;

import gg.habibi.worlds.Worlds;
import gg.habibi.worlds.api.WorldApi;
import gg.habibi.worlds.config.MessageConf;
import gg.habibi.worlds.config.SettingConf;
import gg.habibi.worlds.event.WorldEnterEvent;
import gg.habibi.worlds.util.BorderUtil;
import me.lucko.helper.Events;
import me.lucko.helper.Schedulers;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import me.lucko.helper.text3.Text;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.*;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static gg.habibi.worlds.Worlds.*;

public class  PlayerListener implements TerminableModule {

    private final WorldApi worldApi;
    private final MessageConf messageConf;
    private final SettingConf settingConf;

    public PlayerListener(WorldApi worldApi, MessageConf messageConf, SettingConf settingConf) {
        this.worldApi = worldApi;
        this.messageConf = messageConf;
        this.settingConf = settingConf;
    }

    @Override public void setup(@NotNull TerminableConsumer consumer) {
        Events.subscribe(AsyncPlayerPreLoginEvent.class).handler(event -> {
            final UUID uuid = event.getUniqueId();
            final String name = event.getName();

            if (Worlds.getWorldRegistry().get(uuid).isEmpty()) {
                worldApi.createWorld(uuid, name).thenAcceptSync(world -> worldApi.getCreationQueue().add(world)).bindWith(consumer);
            }

        }).bindWith(consumer);

        Events.subscribe(PlayerRespawnEvent.class).handler(event -> {
            final Player player = event.getPlayer();
            from(player.getUniqueId()).ifPresent(world -> event.setRespawnLocation(world.getSpawn().toLocation()));
            Schedulers.sync().runLater(() -> from(player).ifPresent(world -> BorderUtil.sendBorder(world, player)), 1L);
        }).bindWith(consumer);

        Events.subscribe(PlayerJoinEvent.class).handler(event -> {
            final Player player = event.getPlayer();
            Schedulers.sync().runLater(() -> from(player).ifPresent(world -> BorderUtil.sendBorder(world, player)), 1L);
        }).bindWith(consumer);

        Events.subscribe(PlayerTeleportEvent.class).handler(event -> {
            final Player player = event.getPlayer();
            final Location to = event.getTo();

            if (to.getWorld().getName().equalsIgnoreCase(settingConf.defaultWorld)) {
                from(to).ifPresent(world -> Bukkit.getPluginManager().callEvent(new WorldEnterEvent(world, player)));
            }
        }).bindWith(consumer);

        Events.subscribe(PlayerInteractEvent.class).handler(event -> {
            if(event.hasBlock() && event.getClickedBlock() != null) {
                from(event.getClickedBlock()).ifPresent(world -> {
                    final Player player = event.getPlayer();
                    if (!world.hasAccess(player)) {
                        event.setCancelled(true);
                        player.sendMessage(Text.colorize(messageConf.noInteractionAccess));
                    }
                });
            }
        }).bindWith(consumer);

        Events.subscribe(BlockPlaceEvent.class).handler(event -> {
            final Block block = event.getBlock();
            from(block).ifPresent(world -> {
                final Player player = event.getPlayer();
                if (!world.hasAccess(player)) {
                    event.setCancelled(true);
                    player.sendMessage(Text.colorize(messageConf.noInteractionAccess));
                }
            });
        }).bindWith(consumer);

        Events.subscribe(BlockBreakEvent.class).handler(event -> {
            final Block block = event.getBlock();
            from(block).ifPresent(world -> {
                final Player player = event.getPlayer();
                if (!world.hasAccess(player)) {
                    event.setCancelled(true);
                    player.sendMessage(Text.colorize(messageConf.noInteractionAccess));
                }
            });
        }).bindWith(consumer);
    }
}
