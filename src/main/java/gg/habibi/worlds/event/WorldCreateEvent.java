package gg.habibi.worlds.event;

import gg.habibi.worlds.api.World;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class WorldCreateEvent extends WorldEvent {

    public WorldCreateEvent(World world) {
        super(world);

        // call this once world is created
        Bukkit.getPluginManager().callEvent(new WorldLoadEvent(world));
    }
}