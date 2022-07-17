package gg.habibi.worlds.queue;

import gg.habibi.worlds.api.World;
import gg.habibi.worlds.event.WorldCreateEvent;
import me.lucko.helper.Schedulers;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.Queue;

public class WorldCreationQueue implements Runnable {

    private static final long CREATION_TICK_RATE = 100L;

    private final Queue<World> queue;

    public WorldCreationQueue() {
        this.queue = new LinkedList<>();
        Schedulers.sync().runRepeating(this, CREATION_TICK_RATE, CREATION_TICK_RATE);
    }

    @Override public void run() {
        if (queue.isEmpty()) return;
        final World world = queue.poll();
        Bukkit.getPluginManager().callEvent(new WorldCreateEvent(world));
    }

    public void add(@NotNull World world) {
        this.queue.add(world);
    }
}
