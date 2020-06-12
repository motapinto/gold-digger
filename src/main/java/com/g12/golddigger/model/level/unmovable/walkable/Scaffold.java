package com.g12.golddigger.model.level.unmovable.walkable;

import com.g12.golddigger.model.level.element.Position;

public class Scaffold extends Walkable {
    public Scaffold(Position position) {
        super(position);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Scaffold other = (Scaffold) o;
        return other.position.equals(this.position);
    }
}
