package gg.habibi.worlds.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.lucko.helper.gson.GsonSerializable;
import me.lucko.helper.serialize.ChunkRegion;
import me.lucko.helper.serialize.Point;
import me.lucko.helper.serialize.Position;
import me.lucko.helper.setting.BooleanSettingMap;
import me.lucko.helper.setting.BooleanSettingMapFactory;
import me.lucko.helper.terminable.Terminable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public interface World extends Terminable, GsonSerializable {

    @NotNull UUID getOwner();

    @NotNull String getOwnerName();

    @NotNull Set<UUID> getTrusted();

    @NotNull ChunkRegion getRegion();

    @NotNull Position getCenter();

    @NotNull Point getSpawn();

    @NotNull BooleanSettingMap<WorldSetting> getSettings();

    void setRegion(@NotNull ChunkRegion region);

    void setOwner(@NotNull UUID uuid);

    void trust(@NotNull UUID uuid);

    void untrust(@NotNull UUID uuid);

    void setSpawn(@NotNull Point point);

    void setSettings(BooleanSettingMap<WorldSetting> settings);

    default boolean hasAccess(@NotNull Player player, WorldSetting setting) {
        if (getOwner().equals(player.getUniqueId())) {
            return true;
        } else if (getTrusted().contains(player.getUniqueId())) {
            return getSettings().get(setting);
        } else return false;
    }

    static @NotNull World deserialize(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();

        final UUID uuid = UUID.fromString(object.get("owner").getAsString());
        final String ownerName = object.get("ownerName").getAsString();
        final ChunkRegion chunkRegion = ChunkRegion.deserialize(object.get("region"));
        final Point point = Point.deserialize(object.get("spawn"));
        final JsonArray trustedArray = object.getAsJsonArray("trusted");
        final Set<UUID> trusted = new HashSet<>() {{
            trustedArray.forEach(e -> add(UUID.fromString(e.getAsString())));
        }};

        final BooleanSettingMap<WorldSetting> settingMap = BooleanSettingMapFactory.create(WorldSetting.class).decode(object.get("settings").getAsString());

        final World world = new SimpleWorld(uuid, ownerName, chunkRegion);
        world.setSpawn(point);
        world.setSettings(settingMap);
        trusted.forEach(world::trust);


        return world;
    }


}
