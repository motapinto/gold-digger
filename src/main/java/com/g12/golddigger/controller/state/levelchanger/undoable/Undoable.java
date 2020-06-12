package com.g12.golddigger.controller.state.levelchanger.undoable;

import com.g12.golddigger.utils.IOManager;
import com.g12.golddigger.utils.LevelReader;
import com.g12.golddigger.controller.state.levelchanger.LevelChanger;
import com.g12.golddigger.model.Level;
import com.g12.golddigger.model.Model;
import com.g12.golddigger.view.GUIFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public abstract class Undoable extends LevelChanger {

    public Undoable(Model model, GUIFactory guiFactory, LevelReader reader, IOManager manager) {
        super(model, guiFactory, reader, manager);
    }

    protected void undo() {
        try {
            List<byte[]> states = this.model.getStates();
            if(states.isEmpty()) {
                return;
            }

            ByteArrayInputStream byteStream = new ByteArrayInputStream(states.get(states.size() - 1));

            Level validState = (Level) new ObjectInputStream(byteStream).readObject();
            this.model.setLevel(validState);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Failed to load valid previous state");
        }
    }
}
