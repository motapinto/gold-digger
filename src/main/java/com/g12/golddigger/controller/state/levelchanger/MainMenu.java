package com.g12.golddigger.controller.state.levelchanger;

import com.g12.golddigger.controller.state.AllCompleted;
import com.g12.golddigger.controller.state.State;
import com.g12.golddigger.controller.state.levelchanger.undoable.playing.Playing;
import com.g12.golddigger.exception.ExitGameException;
import com.g12.golddigger.exception.IndexBoundsException;
import com.g12.golddigger.exception.LevelReadException;
import com.g12.golddigger.model.Menu;
import com.g12.golddigger.model.Model;
import com.g12.golddigger.model.menu.Option;
import com.g12.golddigger.utils.IOManager;
import com.g12.golddigger.utils.LevelReader;
import com.g12.golddigger.view.GUIFactory;
import com.g12.golddigger.view.InputKey;

import java.io.IOException;

public class MainMenu extends LevelChanger {
    private final Menu<Option> menu;

    public MainMenu(Model model, GUIFactory guiFactory, LevelReader reader, IOManager manager) {
        super(model, guiFactory, reader, manager);
        this.menu = model.getMainMenu();
        this.gui = this.guiFactory.drawMenu();
    }

    @Override
    public State handle(InputKey action) throws LevelReadException, ExitGameException {
        switch (action) {
            case Up:
                try {
                    this.menu.previousOption();
                } catch (IndexBoundsException ignored) { }
                break;

            case Right:
                switch (this.menu.getSelectedOption()) {
                    case Scratch:
                        try {
                            this.manager.setAndSave("achieved", "");
                        } catch (IOException ignored) { }

                        this.model.setAchieved(-1);
                        this.model.getSelectorMenu().reset();
                        this.model.setLevel(null);

                    case Start:
                        try {
                            this.readAndSetLevel(this.model.getNextLevel());
                            return new Playing(model, guiFactory, reader, manager);
                        } catch (IndexBoundsException e) {
                            return new AllCompleted(model, guiFactory, reader, manager);
                        }

                    case Selector: return new Selector(model, guiFactory, reader, manager);
                }

            case Down:
                try {
                    this.menu.nextOption();
                } catch (IndexBoundsException ignored) { }
                break;

            case Pause: case Quit: throw new ExitGameException("Exiting game...");

            default: break;
        }

        return this;
    }
}