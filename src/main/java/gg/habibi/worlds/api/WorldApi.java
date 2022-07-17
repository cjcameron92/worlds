package gg.habibi.worlds.api;

import gg.habibi.worlds.queue.WorldCreationQueue;
import me.lucko.helper.promise.Promise;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

public interface WorldApi {

    @NotNull Promise<World> createWorld(@NotNull UUID uuid, @NotNull String name);

    @NotNull WorldCache getWorldCache();

    @NotNull WorldCreationQueue getCreationQueue();

    @NotNull Map<UUID, World> getWorlds();
}
