package gg.habibi.worlds.menu;

import gg.habibi.worlds.Worlds;
import gg.habibi.worlds.api.WorldSetting;
import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.menu.paginated.PaginatedGui;
import me.lucko.helper.menu.paginated.PaginatedGuiBuilder;
import me.lucko.helper.text3.Text;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SettingMenu extends PaginatedGui {

    public SettingMenu(Player player) {
        super((paginatedGui -> Arrays.stream(WorldSetting.values()).map(setting -> ItemStackBuilder.of(setting.material).name(setting.name).lore(setting.shortDescription).build(() -> {
            Worlds.getWorldRegistry().get(player.getUniqueId()).ifPresent(world -> {
                player.sendMessage(Text.colorize("&aYou have " + (world.getSettings().toggle(setting) ? "enabled" : "disabled") + " " + setting.name));
            });
        })).collect(Collectors.toList())), player, PaginatedGuiBuilder.create().title("&8Settings"));
    }
}
