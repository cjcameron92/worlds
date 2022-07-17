package gg.habibi.worlds.registry;

import gg.habibi.worlds.Worlds;
import gg.habibi.worlds.api.World;
import me.lucko.helper.serialize.ChunkPosition;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleChunkRegistry implements ChunkRegistry {

    private final Map<ChunkPosition, UUID> chunks = new ConcurrentHashMap<>();

    @Override public @NotNull Optional<World> register(@NotNull ChunkPosition chunkPosition, @NotNull UUID uuid) {
        chunks.put(chunkPosition, uuid);
        return get(uuid);
    }

    @Override public @NotNull Optional<World> get(@NotNull ChunkPosition chunkPosition) {
        return
                chunks.containsKey(chunkPosition) ?
                        get(chunks.get(chunkPosition))
                        :
                        Optional.empty();
    }

    public Optional<World> get(UUID uuid) {
        return Worlds.getWorldRegistry().get(uuid);
    }
}
