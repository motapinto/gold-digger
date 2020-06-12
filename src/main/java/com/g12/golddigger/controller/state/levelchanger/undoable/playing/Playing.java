package com.g12.golddigger.controller.state.levelchanger.undoable.playing;

import com.g12.golddigger.controller.state.State;
import com.g12.golddigger.controller.state.levelchanger.MainMenu;
import com.g12.golddigger.controller.state.levelchanger.Paused;
import com.g12.golddigger.model.Model;
import com.g12.golddigger.model.level.element.Position;
import com.g12.golddigger.model.level.movable.Digger;
import com.g12.golddigger.utils.IOManager;
import com.g12.golddigger.utils.LevelReader;
import com.g12.golddigger.view.GUIFactory;
import com.g12.golddigger.view.InputKey;

public class Playing extends PlayingState {

    public Playing(Model model, GUIFactory guiFactory, LevelReader reader, IOManager manager) {
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
            case Up:
                offset = new Position(0, -1);
                break;
            case Update:
                this.updateTime();
                return this.refresh();
            case Quit: case Pause:
                this.updateTime();
                return new Paused(model, guiFactory, reader, manager);
            case Undo:
                this.undo();
                return this;

            default:
                return this;
        }

        this.model.cloneState();
        return act(diggerPos, offset);
    }

    @Override
    protected State getStateCondition() {
        Digger digger = this.model.getLevel().getDigger();
        State nextState = this;

        if(diggerIsFalling()) {
            digger.setFallCounter(System.currentTimeMillis() + FALL_SPEED);
            nextState = new InAir(model, guiFactory, reader, manager);
        }

        return nextState;
    }

    private boolean diggerIsFalling() {
        Position diggerPosition = this.model.getLevel().getDigger().getPosition();
        Position belowPosition = new Position(diggerPosition.getX(), diggerPosition.getY() + 1);

        return !collidesWalkable(diggerPosition) && collidesDoor(diggerPosition) == null && noCollision(belowPosition);
    }
}
