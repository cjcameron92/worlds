package gg.habibi.worlds.registry;

import gg.habibi.worlds.Worlds;
import gg.habibi.worlds.api.World;
import gg.habibi.worlds.config.SettingConf;
import me.lucko.helper.Services;
import me.lucko.helper.serialize.ChunkPosition;
import me.lucko.helper.serialize.ChunkRegion;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleWorldRegistry implements WorldRegistry {

    private final Map<UUID, World> worlds = new ConcurrentHashMap<>();
    private final SettingConf settingConf = Services.load(SettingConf.class);

    @Override public @NotNull World register(@NotNull UUID uuid, @NotNull World world) {

        final ChunkRegion chunkRegion = world.getRegion();

        for (int x = chunkRegion.getMin().getX(); x <= chunkRegion.getMax().getX(); x++) {
            for (int z = chunkRegion.getMin().getZ(); z <= chunkRegion.getMax().getZ(); z++) {
                Worlds.getChunkRegistry().register(ChunkPosition.of(x, z, settingConf.defaultWorld), uuid);
            }
        }

        this.worlds.put(uuid, world);
        return world;
    }

    @Override public @NotNull Optional<World> get(@NotNull UUID uuid) {
        return this.worlds.containsKey(uuid) ? Optional.of(this.worlds.get(uuid)) : Optional.empty();
    }

    @NotNull @Override public Map<UUID, World> getWorlds() {
        return worlds;
    }
}
