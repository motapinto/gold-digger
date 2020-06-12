package com.g12.golddigger.controller.state.levelchanger.undoable;

import com.g12.golddigger.controller.state.AllCompleted;
import com.g12.golddigger.controller.state.State;
import com.g12.golddigger.controller.state.levelchanger.MainMenu;
import com.g12.golddigger.controller.state.levelchanger.undoable.playing.Playing;
import com.g12.golddigger.exception.IndexBoundsException;
import com.g12.golddigger.exception.LevelReadException;
import com.g12.golddigger.model.Level;
import com.g12.golddigger.model.Menu;
import com.g12.golddigger.model.Model;
import com.g12.golddigger.utils.IOManager;
import com.g12.golddigger.utils.LevelReader;
import com.g12.golddigger.view.GUIFactory;
import com.g12.golddigger.view.InputKey;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameWon extends Undoable {


    public GameWon(Model model, GUIFactory guiFactory, LevelReader reader, IOManager manager) {
        super(model, guiFactory, reader, manager);
        Level level = this.model.getLevel();

        if(level != null && this.model.getAchieved() < level.getFileIndex()) {
            this.model.setAchieved(level.getFileIndex());
            try {
                this.model.getSelectorMenu().setMax(level.getFileIndex() + 1);
                manager.setAndSave("achieved", String.valueOf(level.getFileIndex()));
            } catch (IndexBoundsException | IOException e) {
                System.err.println("Failed to update the maximum level achieved");
            }
        }

        List<String> options = new ArrayList<>();
        options.add("Next level");
        options.add("Restart");
        options.add("Undo last move");
        options.add("Quit to menu");

        this.model.setOptionalMenu(new Menu<>(options));

        this.gui = this.guiFactory.drawGameWon();
    }

    @Override
    public State handle(InputKey action) throws LevelReadException {
        Menu<String> options = this.model.getOptionalMenu();

        switch (action) {
            case Up:
                try {
                    options.previousOption();
                } catch (IndexBoundsException ignored) { }
                return this;

            case Down:
                try {
                    options.nextOption();
                } catch (IndexBoundsException ignored) { }
                return this;

            case Right:
                switch (options.getSelected()) {
                    case 0:
                        try {
                            this.readAndSetLevel(this.model.getNextLevel());
                            return new Playing(model, guiFactory, reader, manager);
                        } catch (IndexBoundsException e) {
                            return new AllCompleted(model, guiFactory, reader, manager);
                        }

                    case 1:
                        this.readAndSetLevel(this.model.getLevel().getFileIndex());
                        return new Playing(model, guiFactory, reader, manager);

                    case 2:
                        this.undo();
                        return new Playing(model, guiFactory, reader, manager);

                    case 3: return new MainMenu(model, guiFactory, reader, manager);
                    default: return this;
                }

            case Restart:
                this.readAndSetLevel(this.model.getLevel().getFileIndex());
                return new Playing(model, guiFactory, reader, manager);

            case Undo:
                this.undo();
                return new Playing(model, guiFactory, reader, manager);

            case Quit: return new MainMenu(model, guiFactory, reader, manager);

            default: return this;
        }
    }
}
