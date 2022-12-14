package com.mygdx.game.Tower;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Enemy.Enemy;
import com.mygdx.game.scene.GameScene;

public class TurretTower implements Tower {
    private final TextureRegion texture;

    private final Vector2 position;
    private boolean canShoot;
    private float distantion;
    private int damage;
    private float couldown;
    private boolean towerUpdate;
    private int levelUpdate;
    private float deltaTime;

    public TurretTower() {
        this.distantion = 200;
        this.damage = 1;
        this.couldown = 1.7f;
        this.levelUpdate = 0;
        this.texture = GameScene.getSplitSpriteMap().getTexture(1, 13);

        this.canShoot = true;
        this.towerUpdate = false;
        this.position = new Vector2();
    }

    @Override
    public void update(float delta) {
        deltaTime += delta;
        if(canShoot || towerUpdate) {
            for (Enemy enemy: GameScene.getGameWorld().getEnemy()) {
                final float scale = GameScene.getScale();
                Vector2 vector = new Vector2(this.getPos().y * scale + scale / 2f, this.getPos().x * scale + scale / 2f);

                if(GameScene.distantion(enemy.getPos(), vector) <= this.getDistantion() / 2f) {
                    enemy.removeHealth(this.getDamage());
                    canShoot = false;
                    towerUpdate = false;
                    return;
                }
            }
        }
        if(deltaTime >= this.getCouldown()) {
            canShoot = true;
            deltaTime = 0;
        }
    }

    @Override
    public float getDistantion() {
        return distantion;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public void addDistantion(float value) {
        this.towerUpdate = true;
        this.distantion += value;
    }

    @Override
    public void removeTimeCouldown(float value) {
        this.towerUpdate = true;
        this.couldown -= value;
    }

    @Override
    public void addDamage(int value) {
        this.towerUpdate = true;
        this.damage += value;
    }

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
