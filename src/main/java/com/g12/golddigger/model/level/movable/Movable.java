package com.g12.golddigger.model.level.movable;

import com.g12.golddigger.model.level.element.Element;
import com.g12.golddigger.model.level.element.Position;

public abstract class Movable extends Element {
    private long fallCounter;

    public Movable(Position position) {
        super(position);
        this.fallCounter = 0;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public long getFallCounter() {
        return fallCounter;
    }

    public void setFallCounter(long fallCounter) {
        this.fallCounter = fallCounter;
    }
}
