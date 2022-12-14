package com.mygdx.game.game_elements;

import com.mygdx.game.Enemy.Enemy;
import com.mygdx.game.paterns.fabric.AbstractFactory;
import com.mygdx.game.paterns.fabric.TypeFactory;
import com.mygdx.game.paterns.fabric.enemy.TypeEnemy;

public class Way {
    private final int count;
    private final Enemy[] enemy;

    public Way(TypeEnemy typeEnemy, int count) {
        this.count = count;
        this.enemy = new Enemy[count];

        for (int i = 0; i < this.enemy.length; i++) {
            this.enemy[i] = new AbstractFactory().createFactory(TypeFactory.ENEMY).create(typeEnemy);
        }
    }

    public Enemy getEnemy(int index) {
        return this.enemy[index];
    }

    public int getCount() {
        return this.count;
    }
}
