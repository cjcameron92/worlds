package gg.habibi.worlds.api;

import com.google.gson.JsonElement;
import gg.habibi.worlds.util.ChunkUtil;
import me.lucko.helper.gson.JsonBuilder;
import me.lucko.helper.serialize.*;
import me.lucko.helper.setting.BooleanSettingMap;
import me.lucko.helper.setting.BooleanSettingMapFactory;
import me.lucko.helper.terminable.composite.AbstractCompositeTerminable;
import org.bukkit.Chunk;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class SimpleWorld extends AbstractCompositeTerminable implements World {

    private UUID owner;
    private String ownerName;
    private ChunkRegion region;
    private Set<UUID> trusted;

    private Point spawn;
    private Position centerPosition;
    private String world;

    private BooleanSettingMap<WorldSetting> settings;

    public SimpleWorld(@NotNull UUID owner, @NotNull String ownerName, @NotNull ChunkRegion region) {
        this.owner = owner;
        this.ownerName = ownerName;
        this.trusted = new HashSet<>();
        this.world = region.getMax().getWorld();
        this.settings = BooleanSettingMapFactory.create(WorldSetting.class).newMap();
        setRegion(region);
        setSpawn(Point.of(centerPosition, Direction.from(centerPosition.toLocation())));
    }

    @Override public void setSpawn(Point spawn) {
        this.spawn = spawn;
    }

    @Override public void trust(@NotNull UUID uuid) {
        this.trusted.add(uuid);
    }

    @Override public void untrust(@NotNull UUID uuid) {
        this.trusted.remove(uuid);
    }

    @Override public void setOwner(@NotNull UUID uuid) {
        this.owner = uuid;
    }

    @Override public void setRegion(@NotNull ChunkRegion region) {
        this.region = region;
        this.centerPosition = ChunkUtil.getCenter(ChunkUtil.getCenter(region.getMin(), region.getMax(), world), world);
    }

    @Override public void setSettings(BooleanSettingMap<WorldSetting> settings) {
        this.settings = settings;
    }

    @Override public @NotNull UUID getOwner() {
        return this.owner;
    }

    @NotNull @Override public String getOwnerName() {
        return ownerName;
    }

    public @NotNull Set<UUID> getTrusted() {
        return this.trusted;
    }

    public @Override @NotNull Position getCenter() {
        return this.centerPosition;
    }

    @Override public @NotNull ChunkRegion getRegion() {
        return this.region;
    }

    @NotNull @Override public Point getSpawn() {
        return spawn;
    }

    @NotNull @Override public BooleanSettingMap<WorldSetting> getSettings() {
        return settings;
    }

    @Override public void close() {
        this.owner = null;
        this.region = null;
        this.trusted = null;
        this.centerPosition = null;
    }

    @NotNull @Override public JsonElement serialize() {
        return JsonBuilder.object()
                .add("owner", owner.toString())
                .add("ownerName", ownerName)
                .add("region", region.serialize())
                .add("spawn", spawn.serialize())
                .add("settings", settings.encodeToString())
                .add("trusted", JsonBuilder.array().addStrings(trusted.stream().map(UUID::toString).collect(Collectors.toList())).build())
                .build();
    }
}
