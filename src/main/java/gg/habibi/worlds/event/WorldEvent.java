package gg.habibi.worlds.event;

import gg.habibi.worlds.api.World;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public abstract class WorldEvent extends Event {

    private static final HandlerList i = new HandlerList();

    private final World world;

    public WorldEvent(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }

    @Override public @NotNull HandlerList getHandlers() {
        return i;
    }

    public static HandlerList getHandlerList() {
        return i;
    }
}
