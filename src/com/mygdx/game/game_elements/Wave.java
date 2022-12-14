package com.mygdx.game.game_elements;

import com.mygdx.game.scene.GameScene;

import java.util.ArrayList;
import java.util.List;

public class Wave {
    private final List<Way> ways;
    private boolean isSpawning;
    private float time;
    private int enemyCount = 0;

    public Wave() {
        time = 0;
        ways = new ArrayList<>();
        isSpawning = false;
    }

    public void addWay(Way way) {
        ways.add(way);
    }

    public void spawnWay() {
        if(isSpawning) return;

        isSpawning = true;
        enemyCount = 0;
        GameScene.addAmountWave(1);
    }

    public void update(float delta) {
        time += delta;

        if(time >= 0.5f && isSpawning) {
            if (enemyCount == ways.get(0).getCount()) {
                ways.remove(0);
                isSpawning = false;
                enemyCount = 0;
                if (ways.size() >= 1) spawnWay();
                return;
            }
            GameScene.getGameWorld().spawn(ways.get(0).getEnemy(enemyCount));
            enemyCount++;
            time = 0;
        }
    }

    public int getTotalEnemies() {
        int count = 0;
        for (Way way : ways) {
            count += way.getCount();
        }
        return count;
    }
}

