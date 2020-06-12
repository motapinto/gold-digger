package com.g12.golddigger.controller.state;

import com.g12.golddigger.model.Model;
import com.g12.golddigger.utils.IOManager;
import com.g12.golddigger.utils.LevelReader;
import com.g12.golddigger.view.GUI;
import com.g12.golddigger.view.GUIFactory;
import com.g12.golddigger.view.InputKey;

public abstract class State {
    protected final Model model;
    protected final GUIFactory guiFactory;
    protected final LevelReader reader;
    protected final IOManager manager;
    protected GUI gui;

    public State(Model model, GUIFactory guiFactory, LevelReader reader, IOManager manager) {
        this.model = model;
        this.guiFactory = guiFactory;
        this.reader = reader;
        this.manager = manager;
    }

    public Model getModel() {
        return model;
    }

    public GUI getGUI() {
        return gui;
    }

    public abstract State handle(InputKey action) throws Exception;
}
