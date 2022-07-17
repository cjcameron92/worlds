package gg.habibi.worlds.util;

import me.lucko.helper.serialize.ChunkPosition;
import me.lucko.helper.serialize.Position;
import org.bukkit.Bukkit;

public class ChunkUtil {

    private ChunkUtil() {}

    public static ChunkPosition getCenter(ChunkPosition min, ChunkPosition max, String world) {
       return ChunkPosition.of(((max.getX() - min.getX())/2) + min.getX(), ((max.getZ() - min.getZ())/2) + min.getZ(), world);
    }

    public static Position getCenter(ChunkPosition position, String world) {
        int minX = position.getX() << 4;
        int minZ = position.getZ() << 4;

        int x = minX+ 8;
        int z = minZ + 8;

        var bukkitWorld = Bukkit.getWorld(world);
        if (bukkitWorld == null) throw new IllegalArgumentException("world is not a bukkit world!");
        int y = bukkitWorld.getHighestBlockYAt(x, z);

        return Position.of(x, y, z, world);
    }
}
