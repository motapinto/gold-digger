package com.g12.golddigger.model.level.unmovable.destructible;

import com.g12.golddigger.model.level.element.Position;

public class Dirt extends Destructible {
    public Dirt(int hitPoints, Position position) {
        super(hitPoints, position);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dirt other = (Dirt) o;
        return other.position.equals(this.position) &&
                other.hitPoints == this.hitPoints;
    }
}
