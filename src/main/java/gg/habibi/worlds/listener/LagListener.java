package gg.habibi.worlds.listener;

import gg.habibi.worlds.config.SettingConf;
import me.lucko.helper.Helper;
import me.lucko.helper.Schedulers;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import static gg.habibi.worlds.Worlds.*;

public class LagListener implements TerminableModule {

    private static final long TICK_RATE = 6_000L;

    private final SettingConf settingConf;

    public LagListener(SettingConf settingConf) {
        this.settingConf = settingConf;
    }

    @Override public void setup(@NotNull TerminableConsumer consumer) {
        Schedulers.async().runRepeating(() -> Helper.world(settingConf.defaultWorld).ifPresent(bukkitWorld -> {
            for (Entity entity : bukkitWorld.getEntities()) {
                if (from(entity.getLocation()).isEmpty()) {
                    entity.remove();
                }
            }
        }), TICK_RATE, TICK_RATE);
    }
}
