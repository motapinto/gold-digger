package com.g12.golddigger.controller.state.levelchanger;

import com.g12.golddigger.utils.IOManager;
import com.g12.golddigger.utils.LevelReader;
import com.g12.golddigger.controller.state.State;
import com.g12.golddigger.exception.LevelReadException;
import com.g12.golddigger.model.Level;
import com.g12.golddigger.model.Model;
import com.g12.golddigger.view.GUIFactory;

public abstract class LevelChanger extends State {
    public LevelChanger(Model model, GUIFactory guiFactory, LevelReader reader, IOManager manager) {
        super(model, guiFactory, reader, manager);
    }

    protected void readAndSetLevel(int levelIndex) throws LevelReadException {
        Level level = reader.readLevel(levelIndex);
        this.model.setLevel(level);
    }
}
