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

public class Selector extends LevelChanger {
    Menu<String> selector;

    public Selector(Model model, GUIFactory guiFactory, LevelReader reader, IOManager manager) {
        super(model, guiFactory, reader, manager);
        this.selector = model.getSelectorMenu();
        this.gui = guiFactory.drawSelector();
    }

    @Override
    public State handle(InputKey action) throws LevelReadException {
        switch (action) {
            case Up:
                try {
                    this.selector.previousOption();
                } catch (IndexBoundsException ignored) { }
                break;

            case Right:
                this.readAndSetLevel(this.model.getSelectorMenu().getSelected());
                return new Playing(model, guiFactory, reader, manager);

            case Down:
                try {
                    this.selector.nextOption();
                } catch (IndexBoundsException ignored) { }
                break;

            case Pause: case Quit: return new MainMenu(model, guiFactory, reader, manager);

            default: break;
        }

        return this;
    }
}
