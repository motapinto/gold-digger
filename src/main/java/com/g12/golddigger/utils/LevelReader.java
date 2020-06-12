package com.g12.golddigger.utils;

import com.g12.golddigger.exception.LevelReadException;
import com.g12.golddigger.model.Level;
import com.g12.golddigger.model.level.element.Position;
import com.g12.golddigger.model.level.movable.Boulder;
import com.g12.golddigger.model.level.movable.Digger;
import com.g12.golddigger.model.level.unmovable.Delimiter;
import com.g12.golddigger.model.level.unmovable.walkable.Door;
import com.g12.golddigger.model.level.unmovable.destructible.Dirt;
import com.g12.golddigger.model.level.unmovable.pickable.Gold;
import com.g12.golddigger.model.level.unmovable.pickable.Key;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LevelReader {
    private final String levelsFolder;

    public LevelReader(String levelsFolder) {
        this.levelsFolder = levelsFolder;
    }

    public Level readLevel(int levelIndex) throws LevelReadException {

        URL resource = ClassLoader.getSystemClassLoader().getResource(this.levelsFolder + "/Level" + levelIndex + ".lvl");
        if(resource == null){
            throw new LevelReadException("Cannot read Level");
        }

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(resource.getFile()));
        } catch (FileNotFoundException e) {
            throw new LevelReadException("Cannot read Level");
        }

        String line;

        Digger digger = null;
        Door door = null;
        List<Boulder> boulders = new ArrayList<>();
        List<Dirt> dirtList = new ArrayList<>();
        List<Key> keys = new ArrayList<>();
        List<Gold> goldList = new ArrayList<>();
        List<Delimiter> delimiters = new ArrayList<>();

        String levelName;
        try {
            if((line = reader.readLine()) != null) {
                levelName = line;
            } else {
                throw new LevelReadException("Cannot read Level");
            }
        } catch (IOException e) {
            throw new LevelReadException("Cannot read Level name");
        }

        int y = 0;
        int maxX = 0;
        while(true) {
            try {
                if ((line = reader.readLine()) == null) break;
            } catch (IOException e) {
                throw new LevelReadException("Cannot read Level");
            }
            int x = 0;
            for(int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                switch (c) {
                    case 'X': boulders.add(new Boulder(new Position(x, y))); break;
                    case 'H': dirtList.add(new Dirt(1, new Position(x, y))); break;
                    case 'R': dirtList.add(new Dirt(2, new Position(x, y))); break;
                    case 'K': dirtList.add(new Dirt(3, new Position(x, y))); break;
                    case '%': keys.add(new Key(new Position(x, y))); break;
                    case '-': delimiters.add(new Delimiter(new Position(x, y))); break;
                    case '+': goldList.add(new Gold(new Position(x, y))); break;
                    case 'I': digger = new Digger(new Position(x, y)); break;
                    case 'D': door = new Door(new Position(x, y)); break;
                }
                x++;
                if(x > maxX) maxX = x;
            }
            y++;
        }

        try {
            reader.close();
        } catch (IOException exception) {
            throw new LevelReadException("Cannot read Level");
        }

        if(levelName.equals("") || digger == null || door == null ||
                delimiters.size() == 0)
            throw new LevelReadException("Cannot read Level");

        return new Level(levelIndex, levelName, digger, boulders, door, dirtList, keys, goldList, delimiters);
    }
}
