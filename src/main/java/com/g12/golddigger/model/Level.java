package com.g12.golddigger.model;

import com.g12.golddigger.model.level.movable.Boulder;
import com.g12.golddigger.model.level.movable.Digger;
import com.g12.golddigger.model.level.unmovable.Delimiter;
import com.g12.golddigger.model.level.unmovable.destructible.Dirt;
import com.g12.golddigger.model.level.unmovable.pickable.Gold;
import com.g12.golddigger.model.level.unmovable.pickable.Key;
import com.g12.golddigger.model.level.unmovable.walkable.Door;
import com.g12.golddigger.model.level.unmovable.walkable.Scaffold;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Level implements Serializable {
    private long time;
    private boolean isCompleted;

    private final String levelName;
    private final int fileIndex;
    private final Digger digger;
    private final ArrayList<Boulder> boulders;
    private final Door door;
    private final ArrayList<Dirt> dirtBlocks;
    private final ArrayList<Key> keys;
    private final ArrayList<Gold> goldPieces;
    private final ArrayList<Scaffold> scaffolds;
    private final ArrayList<Delimiter> delimiters;

    public Level(int fileIndex, String levelName, Digger digger, List<Boulder> boulders, Door door, List<Dirt> dirtBlocks, List<Key> keys, List<Gold> goldPieces, List<Delimiter> delimiters) {
        this.time = 0;
        this.isCompleted = false;

        this.fileIndex = fileIndex;
        this.levelName = levelName;
        this.digger = digger;
        this.boulders = new ArrayList<>(boulders);
        this.door = door;
        this.dirtBlocks = new ArrayList<>(dirtBlocks);
        this.keys = new ArrayList<>(keys);
        this.goldPieces = new ArrayList<>(goldPieces);
        this.delimiters = new ArrayList<>(delimiters);
        this.scaffolds = new ArrayList<>();
    }

    public int getFileIndex() {
        return fileIndex;
    }

    public String getLevelName() {
        return levelName;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Digger getDigger() {
        return digger;
    }

    public List<Boulder> getBoulders() {
        return boulders;
    }

    public Door getDoor() {
        return door;
    }

    public List<Dirt> getDirtBlocks() {
        return dirtBlocks;
    }

    public List<Key> getKeys() {
        return keys;
    }

    public List<Gold> getGoldPieces() {
        return goldPieces;
    }

    public int getGoldCollected() {
        int collected = 0;
        for(Gold gold : goldPieces)
            if(gold.isPicked()) collected++;

        return collected;
    }

    public List<Scaffold> getScaffolds() {
        return scaffolds;
    }

    public List<Delimiter> getDelimiters() {
        return delimiters;
    }

    public int getScore() {
        final int maxTime = 15;
        int score = getGoldCollected() * 5;

        if((int) (time / 1000) < maxTime)  return score + 3*(maxTime - (int) (time / 1000));
        return score ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Level level = (Level) o;
        return time == level.time &&
                isCompleted == level.isCompleted &&
                fileIndex == level.fileIndex &&
                levelName.equals(level.levelName) &&
                digger.equals(level.digger) &&
                boulders.equals(level.boulders) &&
                door.equals(level.door) &&
                dirtBlocks.equals(level.dirtBlocks) &&
                keys.equals(level.keys) &&
                goldPieces.equals(level.goldPieces) &&
                scaffolds.equals(level.scaffolds) &&
                delimiters.equals(level.delimiters);
    }
}
