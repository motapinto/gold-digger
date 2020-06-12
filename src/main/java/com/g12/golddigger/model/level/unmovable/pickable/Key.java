package com.g12.golddigger.model.level.unmovable.pickable;

import com.g12.golddigger.model.level.element.Position;

public class Key extends Pickable {
    public Key(Position position) {
        super(position);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Key other = (Key) o;
        return other.position.equals(this.position);
    }
}
