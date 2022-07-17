package gg.habibi.worlds.listener;

import gg.habibi.worlds.api.WorldApi;
import gg.habibi.worlds.api.WorldSetting;
import gg.habibi.worlds.config.MessageConf;
import gg.habibi.worlds.config.SettingConf;
import me.lucko.helper.Events;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.entity.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import static gg.habibi.worlds.Worlds.*;

public class SettingListener implements TerminableModule {

    private final WorldApi worldApi;
    private final MessageConf messageConf;
    private final SettingConf settingConf;

    public SettingListener(WorldApi worldApi, MessageConf messageConf, SettingConf settingConf) {
        this.worldApi = worldApi;
        this.messageConf = messageConf;
        this.settingConf = settingConf;
    }


    @Override
    public void setup(@NotNull TerminableConsumer consumer) {
        Events.subscribe(BlockBreakEvent.class).handler(event -> {
            final Player player = event.getPlayer();
            final Block block = event.getBlock();

            from(block).ifPresent(world -> {
                if (!world.hasAccess(player, WorldSetting.PLACE_BREAK)) event.setCancelled(true);
            });

        }).bindWith(consumer);

        Events.subscribe(BlockPlaceEvent.class).handler(event -> {
            final Player player = event.getPlayer();
            final Block block = event.getBlock();

            from(block).ifPresent(world -> {
                if (!world.hasAccess(player, WorldSetting.PLACE_BREAK)) event.setCancelled(true);
            });
        }).bindWith(consumer);

        Events.subscribe(PlayerInteractEvent.class).handler(event -> {
            final Player player = event.getPlayer();
            if (event.hasBlock() && event.getClickedBlock() != null) {
                final Block block = event.getClickedBlock();
                from(block).ifPresent(world -> {
                    if (block.getBlockData() instanceof Container && block.getType() != Material.ENDER_CHEST) {
                        if (!world.hasAccess(player, WorldSetting.INTERACT_CONTAINERS)) event.setCancelled(true);
                    }
                    if (block.getBlockData() instanceof Door || block.getBlockData() instanceof TrapDoor) {
                        if (!world.hasAccess(player, WorldSetting.INTERACT_DOOR)) event.setCancelled(true);
                    }
                    if (block.getType() == Material.REDSTONE || block.getType().name().endsWith("_PLATE") || block.getType().name().endsWith("_BUTTON")) {
                        if (!world.hasAccess(player, WorldSetting.INTERACT_REDSTONE)) event.setCancelled(true);
                    }
                });
            }
        }).bindWith(consumer);

        Events.subscribe(PlayerInteractEvent.class).handler(event -> {

            if (!event.hasItem() || event.getItem() == null) return;

            final Player player = event.getPlayer();
            final ItemStack item = event.getItem();
            from(player).ifPresent(world -> {
                if (item.getType() == Material.FLINT_AND_STEEL || item.getType() == Material.FIRE_CHARGE)
                    if (!world.hasAccess(player, WorldSetting.START_FIRE)) event.setCancelled(true);


            });

        });

        Events.subscribe(VehicleEnterEvent.class).handler(event -> {
            final Vehicle vehicle = event.getVehicle();
            final Entity entity = event.getEntered();

            if (entity instanceof Player player) {
                from(vehicle.getLocation()).ifPresent(world -> {
                    if (!world.hasAccess(player, WorldSetting.RIDE_VEHICLES)) event.setCancelled(true);
                });
            }
        }).bindWith(consumer);

        Events.subscribe(EntityPickupItemEvent.class).handler(event -> {
            if (event.getEntity() instanceof Player player) {
                from(player).ifPresent(world -> {
                    if (!world.hasAccess(player, WorldSetting.PICK_ITEMS)) event.setCancelled(true);
                });
            }
        }).bindWith(consumer);

        Events.subscribe(PlayerDropItemEvent.class).handler(event -> {
            final Player player = event.getPlayer();

            from(player).ifPresent(world -> {
                if (!world.hasAccess(player, WorldSetting.DROP_ITEMS)) event.setCancelled(true);
            });

        }).bindWith(consumer);

        Events.subscribe(PlayerInteractEntityEvent.class).handler(event -> {

            final Player player = event.getPlayer();

            if (event.getRightClicked() instanceof Villager) {

                from(player).ifPresent(world -> {
                    if (!world.hasAccess(player, WorldSetting.TRADE_VILLAGER)) {
                        event.setCancelled(true);
                    }
                });
            }

        }).bindWith(consumer);

        Events.subscribe(PlayerTeleportEvent.class).handler(event -> {

            final Player player = event.getPlayer();

            if (event.getCause() != PlayerTeleportEvent.TeleportCause.ENDER_PEARL) return;

            from(player).ifPresent(world -> {

                if (!world.hasAccess(player, WorldSetting.USE_ENDERPEARL)) {
                    event.setTo(player.getLocation());
                }

            });

        }).bindWith(consumer);

        Events.subscribe(EntityDamageByEntityEvent.class).handler(event -> {
            final Entity entity = event.getEntity();
            final Entity damager = event.getDamager();

            if ((!(entity instanceof Player)) && (damager instanceof Player player)) {
                from(entity.getLocation()).ifPresent(world -> {
                    if (entity instanceof Monster) {
                        if (!world.hasAccess(player, WorldSetting.HARM_MOBS)) event.setCancelled(true);
                    } else {
                        if (!world.hasAccess(player, WorldSetting.HARM_ANIMALS)) event.setCancelled(true);
                    }
                });
            }
        }).bindWith(consumer);
    }
}
