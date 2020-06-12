package com.g12.golddigger.utils;

import com.g12.golddigger.exception.IndexBoundsException;
import com.g12.golddigger.exception.LevelReadException;
import com.g12.golddigger.model.Menu;
import com.g12.golddigger.model.Model;
import com.g12.golddigger.model.menu.Option;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ModelBuilder {
    private final IOManager manager;

    public ModelBuilder(IOManager manager) {
        this.manager = manager;
    }

    public Model build(String levelFolder) throws IOException, IndexBoundsException, LevelReadException {
        List<Option> buttons = new ArrayList<>() {
            {
                add(Option.Start);
                add(Option.Scratch);
                add(Option.Selector);
            }
        };

        Menu<Option> menu = new Menu<>(buttons);
        List<String> levels = this.readLevelNames(levelFolder);
        if(levels.isEmpty()) {
            throw new LevelReadException("No levels were found in: " + levelFolder);
        }
        String achievedSaved = this.manager.getProperty("achieved");
        int achieved = (achievedSaved == null || achievedSaved.equals("")) ? -1 : Integer.parseInt(achievedSaved);
        Menu<String> selector = new Menu<>(levels);
        selector.setMax(achieved + 1);

        return new Model(achieved, null, menu, selector);
    }

    private List<String> readLevelNames(String levelsFolder) throws IOException, LevelReadException {
        List<String> levels = new ArrayList<>();

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL levelsFolderUrl = loader.getResource(levelsFolder);
        if(levelsFolderUrl == null) {
            System.err.println("Folder not found: " + levelsFolder);
            return levels;
        }

        File[] levelFiles = new File(levelsFolderUrl.getPath()).listFiles();
        if(levelFiles == null) {
            throw new LevelReadException("No levels found in: " + levelsFolder);
        }

        for (File level : levelFiles){
            String levelFileName = level.getName();
            URL resource = ClassLoader.getSystemClassLoader().getResource("levels/" + levelFileName);
            if(resource == null) {
                System.err.println("Failed to read level: " + levelFileName);
                continue;
            }

            BufferedReader reader = new BufferedReader(new FileReader(resource.getFile()));

            String levelName;

            if((levelName = reader.readLine()) == null) {
                System.err.println("Failed to read level name in file " + levelFileName);
                continue;
            }

            levels.add(levelName);
        }

        return levels;
    }
}
