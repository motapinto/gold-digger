package com.g12.golddigger.model.level.element;

import java.io.Serializable;

public abstract class Element implements Serializable {
    protected Position position;

    public Element(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return this.position;
    }
}
