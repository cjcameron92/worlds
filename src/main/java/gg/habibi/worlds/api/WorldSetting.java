package gg.habibi.worlds.api;

import me.lucko.helper.setting.BooleanSetting;
import me.lucko.helper.text3.Text;
import org.bukkit.Material;

public enum WorldSetting implements BooleanSetting {

    TRADE_VILLAGER("Villager Trade", "Access to trade with villagers.", Material.EMERALD),
    HARM_ANIMALS("Harm Animals","Access to harm Peaceful and Passive entities.", Material.SHEARS),
    HARM_MOBS("Harm Mobs", "Access to harm Harmful mobs.", Material.DIAMOND_SWORD),
    PLACE_BREAK("Place/Break Blocks", "Access to place and break blocks.", Material.SCAFFOLDING),
    RIDE_VEHICLES("Ride Vehicles","Access to enter and leave vehicles.", Material.MINECART),
    INTERACT_REDSTONE("Redstone","Access to cause a redstone input (pressure plates, buttons)", Material.REDSTONE),
    INTERACT_DOOR("Open/Close Door", "Access to close or open doors.", Material.ACACIA_DOOR),
    WORLD_UPGRADES("World Upgrades","Allows to member to access your world core and upgrades.", Material.BEACON),
    WORLD_PERMISSION("World Permissions", "Allows the member to manage other role's permissions.", Material.ACACIA_SIGN),
    WORLD_SETSPAWN("World's Spawnpoint", "Allows the member to set the world's spawnpoint", Material.RESPAWN_ANCHOR),
    ACCESS_WARP("Warp Point", "Allows the member to warp.", Material.ENDER_EYE),
    USE_ENDERPEARL("World's Spawnpoint", "Allows the member to enderpearl around in world.", Material.ENDER_PEARL),
    START_FIRE("START FIRE", "Allows the member to start a fire in the world", Material.GOLD_INGOT),
    DROP_ITEMS("Drop Items", "Allows the member to Drop items on the ground in the world", Material.IRON_INGOT),
    PICK_ITEMS("Pick Items", "Allows the member to picks up items off the ground in the world", Material.GOLD_INGOT),
    INTERACT_CONTAINERS("&cSee Containers","Allows the member to open containers.", Material.CHEST);

    public final String name;
    public final String shortDescription;
    public final Material material;

    WorldSetting(String name, String shortDescription, Material material){
        this.name = Text.colorize("&c" + name);
        this.shortDescription = Text.colorize("&7" + shortDescription);
        this.material = material;
    }

}
