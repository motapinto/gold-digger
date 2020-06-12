package com.g12.golddigger.model.level.movable;

import com.g12.golddigger.model.level.element.Position;

public class Digger extends Movable {

    public Digger(Position position) {
        super(position);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Digger other = (Digger) o;
        return other.position.equals(this.position);
    }
}
