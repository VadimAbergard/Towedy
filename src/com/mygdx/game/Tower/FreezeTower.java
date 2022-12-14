package com.mygdx.game.Tower;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Enemy.Enemy;
import com.mygdx.game.scene.GameScene;

public class FreezeTower implements Tower {
    private final TextureRegion texture;
    private final Vector2 position;
    private float distantion;
    private float couldown;
    private int levelUpdate;

    public FreezeTower() {
        this.distantion = 250;
        this.couldown = 3;
        this.levelUpdate = 0;
        this.texture = GameScene.getSplitSpriteMap().getTexture(1, 15);

        this.position = new Vector2();
    }

    @Override
    public void update(float delta) {
        for (Enemy enemy: GameScene.getGameWorld().getEnemy()) {
            final float scale = GameScene.getScale();
            Vector2 vector = new Vector2(this.getPos().y * scale + scale / 2f, this.getPos().x * scale + scale / 2f);

            if(GameScene.distantion(enemy.getPos(), vector) <= this.getDistantion() / 2f) {
                enemy.setSpeed(enemy.getDefaultSpeed() / 2);
                continue;
            }
            enemy.setSpeed(enemy.getDefaultSpeed());
        }
    }

    @Override
    public float getDistantion() {
        return distantion;
    }

    @Override
    public int getDamage() {
        return 0;
    }

    @Override
    public void addDistantion(float value) {
        this.distantion += value;
    }

    @Override
    public void removeTimeCouldown(float value) {
        this.couldown -= value;
    }

    @Override
    public void addDamage(int value) {}

    public float getCouldown() {
        return couldown;
    }

    @Override
    public Vector2 getPos() {
        return position;
    }

    @Override
    public int getMaxLevelUpdate() {
        return 3;
    }

    @Override
    public int getLevelUpdate() {
        return levelUpdate;
    }

    @Override
    public void addLevelUpdate() {
        levelUpdate++;
    }

    @Override
    public void setPos(int x, int y) {
        this.position.x = x;
        this.position.y = y;
    }

    @Override
    public TextureRegion getTexture() {
        return texture;
    }
}
