package com.mygdx.game.paterns.fabric.goldmine;

import com.mygdx.game.Enemy.Enemy;
import com.mygdx.game.Tower.Tower;
import com.mygdx.game.goldMine.GoldMine;
import com.mygdx.game.goldMine.UsuallyMine;
import com.mygdx.game.paterns.fabric.Factory;
import com.mygdx.game.paterns.fabric.enemy.TypeEnemy;
import com.mygdx.game.paterns.fabric.tower.TypeTower;

public class FactoryGoldMine implements Factory {
    @Override
    public GoldMine create(TypeGoldMine type) {
        switch (type) {
            case USUALLY_MINE:
                return new UsuallyMine();
            default: return null;
        }
    }

    @Override
    public Enemy create(TypeEnemy type) {
        return null;
    }

    @Override
    public Tower create(TypeTower type) {
        return null;
    }
}

