package com.mygdx.game.paterns.fabric;

import com.mygdx.game.paterns.fabric.enemy.FactoryEnemy;
import com.mygdx.game.paterns.fabric.goldmine.FactoryGoldMine;
import com.mygdx.game.paterns.fabric.tower.FactoryTower;

public class AbstractFactory {
    public Factory createFactory(TypeFactory type) {
        switch (type) {
            case TOWER: return new FactoryTower();
            case ENEMY: return new FactoryEnemy();
            case GOLD_MINE: return new FactoryGoldMine();
            default: return null;
        }
    }
}

