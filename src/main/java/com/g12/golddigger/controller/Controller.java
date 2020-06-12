package com.g12.golddigger.controller;

import com.g12.golddigger.controller.state.State;
import com.g12.golddigger.exception.ExitGameException;
import com.g12.golddigger.model.Model;
import com.g12.golddigger.view.GUI;
import com.g12.golddigger.view.InputKey;

public class Controller {
    private final Model model;
    private State state;

    public Controller(Model model, State state) {
        this.model = model;
        this.state = state;
    }

    public void run() throws Exception {
        GUI gui = this.state.getGUI();
        InputKey action = gui.getInput();

        if(action == InputKey.EOF)
            throw new ExitGameException("Forcefully closing the game");

        this.state = this.state.handle(action);
    }

    public void update() throws Exception {
        this.state = this.state.handle(InputKey.Update);
    }

    public void draw() {
        GUI gui = this.state.getGUI();

        gui.clear();
        gui.draw(this.model);
        gui.refresh();
    }
}
