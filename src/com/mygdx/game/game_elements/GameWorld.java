package com.mygdx.game.game_elements;

import com.mygdx.game.Enemy.Enemy;
import com.mygdx.game.Tower.Tower;
import com.mygdx.game.goldMine.GoldMine;

import java.util.ArrayList;
import java.util.List;

public class GameWorld {

    private final List<Enemy> enemy = new ArrayList<>();
    private final List<Tower> tower = new ArrayList<>();
    private final List<GoldMine> goldMine = new ArrayList<>();

    public List<Enemy> getEnemy() {
        return enemy;
    }
    public List<GoldMine> getGoldMine() {
        return goldMine;
    }

    public List<Tower> getTower() {
        return tower;
    }

    public void spawn(Enemy enemy) {
        this.enemy.add(enemy);
    }

}
