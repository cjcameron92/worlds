package gg.habibi.worlds.registry;

import gg.habibi.worlds.api.World;
import me.lucko.helper.serialize.ChunkPosition;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public interface ChunkRegistry {

    @NotNull Optional<World> register(@NotNull ChunkPosition chunkPosition, @NotNull UUID uuid);

    @NotNull Optional<World> get(@NotNull ChunkPosition chunkPosition);

    default @NotNull Optional<World> get(@NotNull Location location) {
        return get(ChunkPosition.of(location.getChunk()));
    }

    default @NotNull Optional<World> get(@NotNull Block block) {
        return get(block.getLocation());
    }
}
