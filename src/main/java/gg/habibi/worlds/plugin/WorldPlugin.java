package gg.habibi.worlds.plugin;

import com.google.common.reflect.TypeToken;
import gg.habibi.worlds.Worlds;
import gg.habibi.worlds.api.*;
import gg.habibi.worlds.command.WorldCommand;
import gg.habibi.worlds.command.impl.DefaultWorldCommand;
import gg.habibi.worlds.config.MessageConf;
import gg.habibi.worlds.config.SettingConf;
import gg.habibi.worlds.event.WorldLoadEvent;
import gg.habibi.worlds.listener.*;
import me.lucko.helper.plugin.ExtendedJavaPlugin;
import me.lucko.helper.serialize.GsonStorageHandler;
import me.lucko.helper.toml.TomlConfigRegistry;
import org.bukkit.Bukkit;

import java.util.Map;
import java.util.UUID;

public class WorldPlugin extends ExtendedJavaPlugin {

    private WorldApi worldApi;
    private GsonStorageHandler<WorldCache> worldCacheGsonStorageHandler;
    private GsonStorageHandler<Map<UUID, World>> worldStorage;

    @Override protected void enable() {
        final TomlConfigRegistry registry = provideService(TomlConfigRegistry.class, new TomlConfigRegistry(getDataFolder()));
        final MessageConf messageConf = provideService(MessageConf.class, registry.register(MessageConf.class, new MessageConf()));
        final SettingConf settingConf = provideService(SettingConf.class, registry.register(SettingConf.class, new SettingConf()));

        worldApi = provideService(WorldApi.class, new SimpleWorldApi(settingConf));

        worldCacheGsonStorageHandler = new GsonStorageHandler<>("worldCache", ".json", getDataFolder(), new TypeToken<>() {});
        worldCacheGsonStorageHandler.load().ifPresent(data -> worldApi.getWorldCache().setLastChunk(data.getLastChunk()));

        worldStorage = new GsonStorageHandler<>("worlds", ".json", getDataFolder(), new TypeToken<>() {});
        worldStorage.load().ifPresent(data -> data.forEach(((uuid, world) ->  {
            Bukkit.getLogger().info("Loaded world [owner] -> " + world.getOwnerName());
            Worlds.getWorldRegistry().register(uuid, world);
            Bukkit.getPluginManager().callEvent(new WorldLoadEvent(world));
        })));

        // Events \\
        bindModule(new PlayerListener(worldApi, messageConf, settingConf));
        bindModule(new WorldListener(messageConf, settingConf));
        bindModule(new EntityListener(messageConf));
        bindModule(new LagListener(settingConf));
        bindModule(new SettingListener(worldApi, messageConf, settingConf));

        // Commands \\
        final WorldCommand worldCommand = new DefaultWorldCommand(messageConf);
        worldCommand.getCommand().registerAndBind(this, worldCommand.getAliases());
    }

    @Override protected void disable() {
        worldCacheGsonStorageHandler.save(worldApi.getWorldCache());
        worldStorage.save(worldApi.getWorlds());
    }
}
