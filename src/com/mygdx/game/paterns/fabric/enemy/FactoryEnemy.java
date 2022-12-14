package com.mygdx.game.paterns.fabric.enemy;

import com.mygdx.game.Enemy.*;
import com.mygdx.game.Tower.Tower;
import com.mygdx.game.goldMine.GoldMine;
import com.mygdx.game.paterns.fabric.Factory;
import com.mygdx.game.paterns.fabric.goldmine.TypeGoldMine;
import com.mygdx.game.paterns.fabric.tower.TypeTower;

public class FactoryEnemy implements Factory {
    public Enemy create(TypeEnemy type) {
        switch (type) {
            case ENEMY1:
                return new Ghost();
            case ENEMY2:
                return new Slime();
            case ENEMY3:
                return new IceCream();
            case ENEMY4:
                return new Sphere();
            default: return null;
        }
    }

    @Override
    public Tower create(TypeTower type) {
        return null;
    }

    @Override
    public GoldMine create(TypeGoldMine type) {
        return null;
    }
}

