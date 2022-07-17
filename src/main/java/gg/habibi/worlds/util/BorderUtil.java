package gg.habibi.worlds.util;

import gg.habibi.worlds.api.World;
import org.bukkit.Bukkit;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;

public class BorderUtil {

    private BorderUtil() {}

    public static void sendBorder(World world, Player player) {
      final WorldBorder worldBorder = Bukkit.createWorldBorder();
      worldBorder.setCenter(world.getCenter().getX(), world.getCenter().getZ());
      worldBorder.setSize(3 * 16);
      worldBorder.setWarningDistance(0);
      player.setWorldBorder(worldBorder);
    }
}
