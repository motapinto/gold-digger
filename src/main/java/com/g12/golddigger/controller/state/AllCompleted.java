package com.g12.golddigger.controller.state;

import com.g12.golddigger.controller.state.levelchanger.MainMenu;
import com.g12.golddigger.model.Model;
import com.g12.golddigger.utils.IOManager;
import com.g12.golddigger.utils.LevelReader;
import com.g12.golddigger.view.GUIFactory;
import com.g12.golddigger.view.InputKey;

public class AllCompleted extends State {

    public AllCompleted(Model model, GUIFactory guiFactory, LevelReader reader, IOManager manager) {
        super(model, guiFactory, reader, manager);
        this.gui = guiFactory.drawAllCompleted();
    }

    @Override
    public State handle(InputKey action) {
        return (action != InputKey.Null && action != InputKey.Update) ?
                new MainMenu(model, guiFactory, reader, manager) :
                this;
    }
}
