package com.g12.golddigger.controller.state.levelchanger.undoable.playing;

import com.g12.golddigger.controller.state.State;
import com.g12.golddigger.controller.state.levelchanger.Paused;
import com.g12.golddigger.model.Model;
import com.g12.golddigger.model.level.element.Position;
import com.g12.golddigger.model.level.movable.Digger;
import com.g12.golddigger.utils.IOManager;
import com.g12.golddigger.utils.LevelReader;
import com.g12.golddigger.view.GUIFactory;
import com.g12.golddigger.view.InputKey;

public class InAir extends PlayingState {

    public InAir(Model model, GUIFactory guiFactory, LevelReader reader, IOManager manager) {
        super(model, guiFactory, reader, manager);
        this.gui = this.guiFactory.drawGame();
    }

    @Override
    public State handle(InputKey action) {
        Digger digger = this.model.getLevel().getDigger();
        Position diggerPos = digger.getPosition();
        Position offset;

        switch (action) {
            case Left:
                offset = new Position(-1, 0);
                break;
            case Right:
                offset = new Position(1, 0);
                break;
            case Down:
                offset = new Position(0, 1);
                break;
            case Update:
                this.updateTime();
                return this.fallAndRefresh();
            case Quit: case Pause:
                this.updateTime();
                return new Paused(model, guiFactory, reader, manager);
            case Undo:
                this.undo();
                return new Playing(model, guiFactory, reader, manager);
            default:
                return this;
        }

        this.model.cloneState();
        return act(diggerPos, offset);
    }

    protected State fallAndRefresh() {
        Digger digger = this.model.getLevel().getDigger();
        if(System.currentTimeMillis() > digger.getFallCounter()){
            this.act(digger.getPosition(), new Position(0, 1));
            digger.setFallCounter(System.currentTimeMillis() + FALL_SPEED);
        }

        return this.refresh();
    }

    @Override
    protected State getStateCondition() {
        return hasStoppedFalling() ? new Playing(model, guiFactory, reader, manager) : this;
    }

    protected boolean hasStoppedFalling() {
        Position diggerPosition = this.model.getLevel().getDigger().getPosition();
        Position bellowDigger = new Position(diggerPosition.getX(), diggerPosition.getY() + 1);

        return this.collidesWalkable(diggerPosition) || !noCollision(bellowDigger);
    }
}
