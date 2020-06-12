package com.g12.golddigger.controller.state.levelchanger;

import com.g12.golddigger.controller.state.State;
import com.g12.golddigger.controller.state.levelchanger.undoable.playing.Playing;
import com.g12.golddigger.exception.IndexBoundsException;
import com.g12.golddigger.exception.LevelReadException;
import com.g12.golddigger.model.Menu;
import com.g12.golddigger.model.Model;
import com.g12.golddigger.utils.IOManager;
import com.g12.golddigger.utils.LevelReader;
import com.g12.golddigger.view.GUIFactory;
import com.g12.golddigger.view.InputKey;

import java.util.ArrayList;
import java.util.List;

public class Paused extends LevelChanger {

    public Paused(Model model, GUIFactory guiFactory, LevelReader reader, IOManager manager) {
        super(model, guiFactory, reader, manager);

        List<String> options = new ArrayList<>();
        options.add("Continue");
        options.add("Restart");
        options.add("Quit to menu");

        this.model.setOptionalMenu(new Menu<>(options));

        this.gui = guiFactory.drawPaused();
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
                    case 1:
                        this.readAndSetLevel(this.model.getLevel().getFileIndex());
                    case 0:
                        return new Playing(model, guiFactory, reader, manager);

                    case 2: return new MainMenu(model, guiFactory, reader, manager);
                    default: return this;
                }

            case Pause: return new Playing(model, guiFactory, reader, manager);

            case Restart:
                this.readAndSetLevel(this.model.getLevel().getFileIndex());
                return new Playing(model, guiFactory, reader, manager);

            case Quit: return new MainMenu(model, guiFactory, reader, manager);

            default: break;
        }

        return this;
    }
}
