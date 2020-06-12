package com.g12.golddigger.model.level.movable;

import com.g12.golddigger.model.level.element.Position;

public class Boulder extends Movable {
    public Boulder(Position position) {
        super(position);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Boulder other = (Boulder) o;
        return other.position.equals(this.position);
    }
}
