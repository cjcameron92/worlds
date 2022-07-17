package gg.habibi.worlds.api;

import com.google.common.collect.ImmutableList;
import gg.habibi.worlds.Worlds;
import gg.habibi.worlds.config.SettingConf;
import gg.habibi.worlds.queue.WorldCreationQueue;
import me.lucko.helper.promise.Promise;
import me.lucko.helper.serialize.ChunkPosition;
import me.lucko.helper.serialize.ChunkRegion;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SimpleWorldApi implements WorldApi {

    private final SettingConf settingConf;
    private final WorldCache worldCache;
    private final WorldCreationQueue worldCreationQueue;

    public SimpleWorldApi(@NotNull SettingConf settingConf) {
        this.settingConf = settingConf;
        this.worldCache = new WorldCache(ChunkPosition.of(0, 0, settingConf.defaultWorld));
        this.worldCreationQueue = new WorldCreationQueue();

        Bukkit.createWorld(WorldCreator.name(settingConf.defaultWorld)
                .generateStructures(false)
                .biomeProvider(new BiomeProvider() {

                    @Override
                    public @NotNull Biome getBiome(@NotNull WorldInfo worldInfo, int i, int i1, int i2) {
                        return Biome.PLAINS;
                    }

                    @Override
                    public @NotNull List<Biome> getBiomes(@NotNull WorldInfo worldInfo) {
                        return ImmutableList.of(Biome.PLAINS);
                    }
                }));
    }

    @Override public @NotNull Promise<World> createWorld(@NotNull UUID uuid, @NotNull String name) {
        return Promise.supplyingAsync(() -> {
            final ChunkPosition chunkPosition = worldCache.getLastChunk();
            worldCache.setLastChunk(chunkPosition.add(settingConf.spacePerWorld, settingConf.spacePerWorld));
            final ChunkRegion chunkRegion = ChunkRegion.
                    of(ChunkPosition.
                            of(chunkPosition.getX() + settingConf.worldChunkSize, chunkPosition.getZ() + settingConf.worldChunkSize, settingConf.defaultWorld), ChunkPosition.
                            of(chunkPosition.getX() - settingConf.worldChunkSize, chunkPosition.getZ() - settingConf.worldChunkSize, settingConf.defaultWorld));

            final World world = new SimpleWorld(uuid, name, chunkRegion);
            Worlds.getWorldRegistry().register(uuid, world);
            return world;
        });
    }

    @Override public @NotNull WorldCache getWorldCache() {
        return worldCache;
    }

    @Override public @NotNull WorldCreationQueue getCreationQueue() {
        return worldCreationQueue;
    }

    @Override public @NotNull Map<UUID, World> getWorlds() {
        return Worlds.getWorldRegistry().getWorlds();
    }
}
