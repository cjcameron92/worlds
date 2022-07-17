package gg.habibi.worlds;

import gg.habibi.worlds.api.World;
import gg.habibi.worlds.registry.ChunkRegistry;
import gg.habibi.worlds.registry.SimpleChunkRegistry;
import gg.habibi.worlds.registry.SimpleWorldRegistry;
import gg.habibi.worlds.registry.WorldRegistry;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class Worlds {

    private static final WorldRegistry worldRegistry;
    private static final ChunkRegistry chunkRegistry;

    static {
        worldRegistry = new SimpleWorldRegistry();
        chunkRegistry = new SimpleChunkRegistry();
    }

    public static @NotNull Optional<World> from(@NotNull LivingEntity entity) {
        return chunkRegistry.get(entity.getLocation());
    }

    public static @NotNull Optional<World> from(@NotNull Block block) {
        return chunkRegistry.get(block);
    }

    public static @NotNull Optional<World> from(@NotNull Location location) {
        return chunkRegistry.get(location);
    }

    public static WorldRegistry getWorldRegistry() {
        return worldRegistry;
    }

    public static ChunkRegistry getChunkRegistry() {
        return chunkRegistry;
    }
}
