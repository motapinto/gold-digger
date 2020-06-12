package com.g12.golddigger.model.level.unmovable;

import com.g12.golddigger.model.level.element.Position;

public class Delimiter extends Unmovable {
    public Delimiter(Position position) {
        super(position);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Delimiter other = (Delimiter) o;
        return other.position.equals(this.position);
    }
}
