package com.g12.golddigger.utils;

import com.g12.golddigger.exception.LevelReadException;
import com.g12.golddigger.model.Level;
import com.g12.golddigger.model.level.element.Position;
import com.g12.golddigger.model.level.movable.Boulder;
import com.g12.golddigger.model.level.movable.Digger;
import com.g12.golddigger.model.level.unmovable.Delimiter;
import com.g12.golddigger.model.level.unmovable.destructible.Dirt;
import com.g12.golddigger.model.level.unmovable.pickable.Gold;
import com.g12.golddigger.model.level.unmovable.pickable.Key;
import com.g12.golddigger.model.level.unmovable.walkable.Door;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class LevelReaderTest {
    public LevelReader reader;

    @BeforeEach
    public void setUp() {
        this.reader = new LevelReader("levels");
    }

    @Test
    public void readLevel0() throws LevelReadException {
        Digger digger = new Digger(new Position(7, 3));

        List<Boulder> boulders = new ArrayList<>();
        boulders.add(new Boulder(new Position(1, 3)));
        boulders.add(new Boulder(new Position(2, 3)));
        boulders.add(new Boulder(new Position(3, 3)));
        boulders.add(new Boulder(new Position(4, 3)));
        boulders.add(new Boulder(new Position(5, 3)));
        boulders.add(new Boulder(new Position(6, 3)));

        Door door = new Door(new Position(1, 2));

        List<Dirt> dirtBlocks = new ArrayList<>();
        dirtBlocks.add(new Dirt(1, new Position(1, 1)));
        dirtBlocks.add(new Dirt(2, new Position(2, 1)));
        dirtBlocks.add(new Dirt(3, new Position(3, 1)));

        List<Key> keys = new ArrayList<>();
        keys.add(new Key(new Position(4, 2)));
        keys.add(new Key(new Position(5, 2)));
        keys.add(new Key(new Position(6, 2)));
        keys.add(new Key(new Position(7, 2)));

        List<Gold> golds = new ArrayList<>();
        golds.add(new Gold(new Position(5, 1)));
        golds.add(new Gold(new Position(7, 1)));

        List<Delimiter> delimiters = new ArrayList<>();
        delimiters.add(new Delimiter(new Position(0, 0)));
        delimiters.add(new Delimiter(new Position(1, 0)));
        delimiters.add(new Delimiter(new Position(2, 0)));
        delimiters.add(new Delimiter(new Position(3, 0)));
        delimiters.add(new Delimiter(new Position(4, 0)));
        delimiters.add(new Delimiter(new Position(5, 0)));
        delimiters.add(new Delimiter(new Position(6, 0)));
        delimiters.add(new Delimiter(new Position(7, 0)));
        delimiters.add(new Delimiter(new Position(8, 0)));

        delimiters.add(new Delimiter(new Position(0, 1)));
        delimiters.add(new Delimiter(new Position(4, 1)));
        delimiters.add(new Delimiter(new Position(6, 1)));
        delimiters.add(new Delimiter(new Position(8, 1)));

        delimiters.add(new Delimiter(new Position(0, 2)));
        delimiters.add(new Delimiter(new Position(2, 2)));
        delimiters.add(new Delimiter(new Position(3, 2)));
        delimiters.add(new Delimiter(new Position(8, 2)));

        delimiters.add(new Delimiter(new Position(0, 3)));
        delimiters.add(new Delimiter(new Position(8, 3)));

        delimiters.add(new Delimiter(new Position(0, 4)));
        delimiters.add(new Delimiter(new Position(1, 4)));
        delimiters.add(new Delimiter(new Position(2, 4)));
        delimiters.add(new Delimiter(new Position(3, 4)));
        delimiters.add(new Delimiter(new Position(4, 4)));
        delimiters.add(new Delimiter(new Position(5, 4)));
        delimiters.add(new Delimiter(new Position(6, 4)));
        delimiters.add(new Delimiter(new Position(7, 4)));
        delimiters.add(new Delimiter(new Position(8, 4)));

        Level level0 = new Level(0, "Test0", digger, boulders, door, dirtBlocks, keys, golds, delimiters);

        Level level = reader.readLevel(0);

        assert reader.readLevel(0).equals(level0);
    }

    @Test
    public void readLevel1() throws LevelReadException {
        Digger digger = new Digger(new Position(7, 3));

        List<Boulder> boulders = new ArrayList<>();
        boulders.add(new Boulder(new Position(1, 3)));
        boulders.add(new Boulder(new Position(4, 3)));
        boulders.add(new Boulder(new Position(6, 3)));

        Door door = new Door(new Position(1, 2));

        List<Dirt> dirtBlocks = new ArrayList<>();
        dirtBlocks.add(new Dirt(1, new Position(1, 1)));
        dirtBlocks.add(new Dirt(3, new Position(2, 1)));
        dirtBlocks.add(new Dirt(1, new Position(3, 1)));
        dirtBlocks.add(new Dirt(3, new Position(4, 1)));

        dirtBlocks.add(new Dirt(2, new Position(2, 3)));
        dirtBlocks.add(new Dirt(2, new Position(3, 3)));
        dirtBlocks.add(new Dirt(2, new Position(5, 3)));

        List<Key> keys = new ArrayList<>();
        keys.add(new Key(new Position(4, 2)));
        keys.add(new Key(new Position(5, 2)));
        keys.add(new Key(new Position(6, 2)));
        keys.add(new Key(new Position(7, 2)));

        List<Gold> golds = new ArrayList<>();
        golds.add(new Gold(new Position(5, 1)));
        golds.add(new Gold(new Position(7, 1)));

        List<Delimiter> delimiters = new ArrayList<>();
        delimiters.add(new Delimiter(new Position(0, 0)));
        delimiters.add(new Delimiter(new Position(1, 0)));
        delimiters.add(new Delimiter(new Position(2, 0)));
        delimiters.add(new Delimiter(new Position(3, 0)));
        delimiters.add(new Delimiter(new Position(4, 0)));
        delimiters.add(new Delimiter(new Position(5, 0)));
        delimiters.add(new Delimiter(new Position(6, 0)));
        delimiters.add(new Delimiter(new Position(7, 0)));
        delimiters.add(new Delimiter(new Position(8, 0)));

        delimiters.add(new Delimiter(new Position(0, 1)));
        delimiters.add(new Delimiter(new Position(8, 1)));

        delimiters.add(new Delimiter(new Position(0, 2)));
        delimiters.add(new Delimiter(new Position(2, 2)));
        delimiters.add(new Delimiter(new Position(3, 2)));
        delimiters.add(new Delimiter(new Position(8, 2)));

        delimiters.add(new Delimiter(new Position(0, 3)));
        delimiters.add(new Delimiter(new Position(8, 3)));

        delimiters.add(new Delimiter(new Position(0, 4)));
        delimiters.add(new Delimiter(new Position(1, 4)));
        delimiters.add(new Delimiter(new Position(2, 4)));
        delimiters.add(new Delimiter(new Position(3, 4)));
        delimiters.add(new Delimiter(new Position(4, 4)));
        delimiters.add(new Delimiter(new Position(5, 4)));
        delimiters.add(new Delimiter(new Position(6, 4)));
        delimiters.add(new Delimiter(new Position(7, 4)));
        delimiters.add(new Delimiter(new Position(8, 4)));

        Level level1 = new Level(1, "Test1", digger, boulders, door, dirtBlocks, keys, golds, delimiters);

        assert reader.readLevel(1).equals(level1);
    }
}
