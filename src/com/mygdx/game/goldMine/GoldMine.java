package com.mygdx.game.goldMine;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public interface GoldMine {
    void update(float delta);
    float getGenerateSpeed();
    int getMoneyFromMine();
    void addMineMoney(int value);
    void removeSpeed(float value);
    Vector2 getPos();
    int getMaxLevelUpdate();
    int getLevelUpdate();
    void addLevelUpdate();
    void setPos(int x, int y);
    TextureRegion getTexture();
}
