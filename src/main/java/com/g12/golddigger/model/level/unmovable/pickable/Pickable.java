package com.g12.golddigger.model.level.unmovable.pickable;

import com.g12.golddigger.model.level.element.Position;
import com.g12.golddigger.model.level.unmovable.Unmovable;

public abstract class Pickable extends Unmovable {
    protected boolean picked;

    public Pickable(Position position) {
        super(position);
        this.picked = false;
    }

    public boolean isPicked() {
        return picked;
    }

    public void setPicked(boolean picked) {
        this.picked = picked;
    }
}
