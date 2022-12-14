package com.mygdx.game.paterns.fabric;

import com.mygdx.game.Enemy.Enemy;
import com.mygdx.game.Tower.Tower;
import com.mygdx.game.goldMine.GoldMine;
import com.mygdx.game.paterns.fabric.enemy.TypeEnemy;
import com.mygdx.game.paterns.fabric.goldmine.TypeGoldMine;
import com.mygdx.game.paterns.fabric.tower.TypeTower;

public interface Factory {
    Enemy create(TypeEnemy type);
    Tower create(TypeTower type);
    GoldMine create(TypeGoldMine type);
}
