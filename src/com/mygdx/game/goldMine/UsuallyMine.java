package com.mygdx.game.goldMine;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.game_elements.InfoAboutPlayer;
import com.mygdx.game.scene.GameScene;

public class UsuallyMine implements GoldMine {
    private final TextureRegion texture;

    private final Vector2 position;
    private int moneyFromMine;
    private int speed;
    private float deltaTime;
    private int levelUpdate;

    public UsuallyMine() {
        this.position = new Vector2();
        this.moneyFromMine = 10;
        this.speed = 3;
        this.levelUpdate = 0;
        this.texture = GameScene.getSplitSpriteMap().getTexture(1, 14);
    }

    @Override
    public void update(float delta) {
        deltaTime += delta;
        if(deltaTime >= this.getGenerateSpeed()) {
            InfoAboutPlayer.addMoneyInGame(getMoneyFromMine());
            deltaTime = 0;
        }
    }

    @Override
    public float getGenerateSpeed() {
        return this.speed;
    }

    @Override
    public int getMoneyFromMine() {
        return this.moneyFromMine;
    }

    @Override
    public void addMineMoney(int value) {
        this.moneyFromMine += value;
    }

    @Override
    public void removeSpeed(float value) {
        this.speed -= value;
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
