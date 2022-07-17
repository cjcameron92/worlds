package gg.habibi.worlds.registry;

import gg.habibi.worlds.api.World;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface WorldRegistry {

    @NotNull World register(@NotNull UUID uuid, @NotNull World world);

    @NotNull Optional<World> get(@NotNull UUID uuid);

    @NotNull Map<UUID, World> getWorlds();


}
