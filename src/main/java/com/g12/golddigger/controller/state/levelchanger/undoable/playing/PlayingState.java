package com.g12.golddigger.controller.state.levelchanger.undoable.playing;

import com.g12.golddigger.controller.state.State;
import com.g12.golddigger.controller.state.levelchanger.undoable.GameOver;
import com.g12.golddigger.controller.state.levelchanger.undoable.GameWon;
import com.g12.golddigger.controller.state.levelchanger.undoable.Undoable;
import com.g12.golddigger.model.Model;
import com.g12.golddigger.model.level.element.Element;
import com.g12.golddigger.model.level.element.Position;
import com.g12.golddigger.model.level.movable.Movable;
import com.g12.golddigger.model.level.unmovable.Delimiter;
import com.g12.golddigger.model.level.unmovable.destructible.Destructible;
import com.g12.golddigger.model.level.unmovable.pickable.Key;
import com.g12.golddigger.model.level.unmovable.pickable.Pickable;
import com.g12.golddigger.model.level.unmovable.walkable.Door;
import com.g12.golddigger.model.level.unmovable.walkable.Scaffold;
import com.g12.golddigger.model.level.unmovable.walkable.Walkable;
import com.g12.golddigger.utils.IOManager;
import com.g12.golddigger.utils.LevelReader;
import com.g12.golddigger.view.GUIFactory;

public abstract class PlayingState extends Undoable {
    protected long startTime;
    protected final static long FALL_SPEED = 200;

    public PlayingState(Model model, GUIFactory guiFactory, LevelReader reader, IOManager manager) {
        super(model, guiFactory, reader, manager);
        this.startTime = System.currentTimeMillis();
    }

    protected void updateTime() {
        long currentTime = System.currentTimeMillis();
        this.model.getLevel().setTime(this.model.getLevel().getTime() + currentTime - startTime);
        this.startTime = currentTime;
    }

    protected State act(Position diggerPos, Position offset) {
        Position target = new Position(diggerPos.getX() + offset.getX(), diggerPos.getY() + offset.getY());

        if (collidesDelimiter(target)) return getNextState();

        if (collideAndMove(offset, target)) return getNextState();

        if (collideAndHit(target)) return getNextState();

        pickUp(target);

        this.model.getLevel().getDigger().setPosition(target);
        return getNextState();
    }

    protected State getNextState() {
        State state;

        if (checkGameOver()) {
            state = new GameOver(model, guiFactory, reader, manager);
        } else if (checkGameWon()) {
            this.updateTime();
            state = new GameWon(model, guiFactory, reader, manager);
        } else {
            state = getStateCondition();
        }

        return state;
    }

    protected abstract State getStateCondition();

    protected boolean checkGameOver() {
        Position diggerPos = this.model.getLevel().getDigger().getPosition();

        for (Element element : this.model.getLevel().getBoulders()) {
            if (element.getPosition().equals(diggerPos)) {
                return true;
            }
        }

        return false;
    }

    private boolean checkGameWon() {
        Door door;
        Position diggerPos = this.model.getLevel().getDigger().getPosition();

        if ((door = collidesDoor(diggerPos)) != null) {
            return door.isOpen();
        }

        return false;
    }

    private boolean collideAndHit(Position target) {
        Destructible destructible = collidesDestructible(target);
        if (destructible == null) {
            return false;
        }

        if (decreaseHP(destructible) == 0) {
            setToFallBoulder(destructible.getPosition());
            if(noCollision(new Position(target.getX(), target.getY() + 1))) {
                this.model.getLevel().getScaffolds().add(new Scaffold(target));
            }
        }
        return true;
    }

    private boolean collideAndMove(Position offset, Position target) {
        Movable collided;
        if ((collided = collidesMovable(target)) != null) {
            Position moveTo = new Position(target.getX() + offset.getX(), target.getY() + offset.getY());
            move(collided, moveTo);

            return true;
        }
        return false;
    }

    private void setToFallBoulder(Position destroyedPosition) {
        Movable boulder = collidesMovable(new Position(destroyedPosition.getX(), destroyedPosition.getY() - 1));

        if(boulder != null){
            boulder.setFallCounter(System.currentTimeMillis() + FALL_SPEED);
        }
    }

    protected State refresh() {
        for (Movable movable : this.model.getLevel().getBoulders()) {
            moveBoulder(movable, this.getFallPosition(movable));
        }

        return getNextState();
    }

    private void moveBoulder(Movable movable, Position fellPos) {
        if(System.currentTimeMillis() > movable.getFallCounter()) {
            if(!movable.getPosition().equals(fellPos)){
                movable.setPosition(fellPos);
                movable.setFallCounter(System.currentTimeMillis() + FALL_SPEED);
            } else if(collidesMovable(new Position(movable.getPosition().getX(), movable.getPosition().getY() + 1)) != null){

                if(move(movable, new Position(movable.getPosition().getX() - 1, movable.getPosition().getY()))){
                    movable.setFallCounter(System.currentTimeMillis() + FALL_SPEED);

                } else if(move(movable, new Position(movable.getPosition().getX() + 1, movable.getPosition().getY()))){
                    movable.setFallCounter(System.currentTimeMillis() + FALL_SPEED);
                }
            }
        }
    }

    protected Position getFallPosition(Movable movable) {
        Position curPos = movable.getPosition();
        Position fallPos = new Position(curPos.getX(), curPos.getY() + 1);

        return noCollision(fallPos) && System.currentTimeMillis() > movable.getFallCounter() ? fallPos : curPos;
    }

    protected void pickUp(Position position) {
        Pickable pickable;
        if ((pickable = collidesPickable(position)) != null) {
            pickable.setPicked(true);
            if(pickable instanceof Key){
                openDoor();
            }
        }
    }

    protected void openDoor(){
        boolean open = true;

        for (Pickable pickable : this.model.getLevel().getKeys()){
            if (!pickable.isPicked()) {
                open = false;
                break;
            }
        }

        this.model.getLevel().getDoor().setOpen(open);
    }

    protected int decreaseHP(Destructible destructible) {
        destructible.setHP(destructible.getHP() - 1);

        return destructible.getHP();
    }

    protected boolean move(Movable movable, Position position) {
        if (noCollision(position)) {
            movable.setPosition(position);
            return true;
        }
        return false;
    }

    protected boolean noCollision(Position position) {
        return collidesMovable(position) == null && !collidesDelimiter(position)
                && collidesDestructible(position) == null;
    }

    protected Movable collidesMovable(Position position)  {
        for (Movable movable : this.model.getLevel().getBoulders()) {
            if (movable.getPosition().equals(position)) {
                return movable;
            }
        }

        return null;
    }

    protected Pickable collidesPickable(Position position) {
        for (Pickable key : this.model.getLevel().getKeys()) {
            if (key.getPosition().equals(position) && !key.isPicked()) {
                return key;
            }
        }

        for (Pickable gold : this.model.getLevel().getGoldPieces()) {
            if (gold.getPosition().equals(position) && !gold.isPicked()) {
                return gold;
            }
        }

        return null;
    }

    protected boolean collidesWalkable(Position position) {
        for (Walkable scaffold : this.model.getLevel().getScaffolds()) {
            if (scaffold.getPosition().equals(position)) {
                return true;
            }
        }

        return collidesDoor(position) != null;
    }

    protected boolean collidesDelimiter(Position position) {
        for (Delimiter delimiter : this.model.getLevel().getDelimiters()) {
            if (delimiter.getPosition().equals(position)) {
                return true;
            }
        }

        return false;
    }

    protected Destructible collidesDestructible(Position position) {
        for (Destructible dirt : this.model.getLevel().getDirtBlocks()) {
            if (dirt.getHP() > 0 && dirt.getPosition().equals(position)) {
                return dirt;
            }
        }

        return null;
    }

    protected Door collidesDoor(Position position) {
        return this.model.getLevel().getDoor().getPosition().equals(position) ? this.model.getLevel().getDoor() : null;
    }
}