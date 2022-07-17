package gg.habibi.worlds.listener;

import gg.habibi.worlds.config.MessageConf;
import me.lucko.helper.Events;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import me.lucko.helper.text3.Text;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.jetbrains.annotations.NotNull;

import static gg.habibi.worlds.Worlds.*;

public class EntityListener implements TerminableModule {

    private final MessageConf messageConf;

    public EntityListener(MessageConf messageConf) {
        this.messageConf = messageConf;
    }

    @Override public void setup(@NotNull TerminableConsumer consumer) {
        Events.subscribe(EntitySpawnEvent.class).handler(event -> {
            final Entity entity = event.getEntity();
            if (!(entity instanceof Player)) {
                if (from(entity.getLocation()).isEmpty()) {
                    event.setCancelled(true);
                }
            }
        }).bindWith(consumer);

        Events.subscribe(CreatureSpawnEvent.class).handler(event -> {
            final LivingEntity entity = event.getEntity();
            if (!(entity instanceof Player)) {
                if (from(entity).isEmpty()) {
                    event.setCancelled(true);
                }
            }
        }).bindWith(consumer);

        Events.subscribe(EntityDamageByEntityEvent.class).handler(event -> {

            if(event.getEntity() instanceof Player player) {

                if(event.getDamager() instanceof Player attacker) {
                    attacker.sendMessage(Text.colorize(messageConf.pvpDisabled));
                    event.setCancelled(true);
                    return;
                }

                from(player).ifPresent(world -> {
                    if (!world.hasAccess(player)) {
                        event.setCancelled(true);
                    }
                });
            }

            if (event.getDamager() instanceof Player player && event.getEntity() instanceof Animals entity) {

                from(entity).ifPresent(world -> {
                    if (!world.hasAccess(player)) {
                        event.setCancelled(true);
                        player.sendMessage(Text.colorize(messageConf.noDamageAnimalsAccess));
                    }
                });
            }

        }).bindWith(consumer);
    }
}
