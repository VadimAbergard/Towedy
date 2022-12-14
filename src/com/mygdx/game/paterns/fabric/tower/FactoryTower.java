package com.mygdx.game.paterns.fabric.tower;

import com.mygdx.game.Enemy.Enemy;
import com.mygdx.game.Tower.*;
import com.mygdx.game.goldMine.GoldMine;
import com.mygdx.game.paterns.fabric.Factory;
import com.mygdx.game.paterns.fabric.enemy.TypeEnemy;
import com.mygdx.game.paterns.fabric.goldmine.TypeGoldMine;

public class FactoryTower implements Factory {
    @Override
    public Enemy create(TypeEnemy type) {
        return null;
    }

    public Tower create(TypeTower type) {
        switch (type) {
            case SHORT_TOWER: return new ShortTower();
            case LONG_TOWER: return new LongTower();
            case FREEZE_TOWER: return new FreezeTower();
            case TURRET_TOWER: return new TurretTower();
            default: return null;
        }
    }

    @Override
    public GoldMine create(TypeGoldMine type) {
        return null;
    }
}

