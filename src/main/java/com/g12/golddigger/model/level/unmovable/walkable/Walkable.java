package com.g12.golddigger.model.level.unmovable.walkable;

import com.g12.golddigger.model.level.element.Position;
import com.g12.golddigger.model.level.unmovable.Unmovable;

public abstract class Walkable extends Unmovable {
    public Walkable(Position position) {
        super(position);
    }
}
