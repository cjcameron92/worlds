package gg.habibi.worlds.event;

import gg.habibi.worlds.api.World;
import org.bukkit.entity.Player;

public class WorldEnterEvent extends WorldEvent {

    private final Player entering;

    public WorldEnterEvent(World world, Player entering) {
        super(world);
        this.entering = entering;
    }

    public Player getEntering() {
        return entering;
    }
}
