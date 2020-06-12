package com.g12.golddigger.model.level.unmovable.destructible;

import com.g12.golddigger.model.level.element.Position;
import com.g12.golddigger.model.level.unmovable.Unmovable;

public abstract class Destructible extends Unmovable {
    protected int hitPoints;

    public Destructible(int hitPoints, Position position) {
        super(position);
        this.hitPoints = hitPoints;
    }

    public int getHP() {
        return this.hitPoints;
    }
    public void setHP(int hp)  {
        this.hitPoints = hp;
    }
}