package gg.habibi.worlds.api;

import me.lucko.helper.serialize.ChunkPosition;

public class WorldCache {

    private ChunkPosition lastChunk;

    public WorldCache(ChunkPosition lastChunk) {
        this.lastChunk = lastChunk;
    }

    public void setLastChunk(ChunkPosition lastChunk) {
        this.lastChunk = lastChunk;
    }

    public ChunkPosition getLastChunk() {
        return lastChunk;
    }
}
