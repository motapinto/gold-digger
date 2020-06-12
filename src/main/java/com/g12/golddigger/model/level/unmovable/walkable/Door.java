package com.g12.golddigger.model.level.unmovable.walkable;

import com.g12.golddigger.model.level.element.Position;

public class Door extends Walkable {
    private boolean open = false;

    public Door(Position position) {
        super(position);
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Door other = (Door) o;
        return other.position.equals(this.position);
    }
}
