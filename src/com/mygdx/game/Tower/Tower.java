package com.mygdx.game.Tower;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public interface Tower {
    void update(float delta);
    float getDistantion();
    float getCouldown();
    int getDamage();
    void addDistantion(float value);
    void removeTimeCouldown(float value);
    void addDamage(int value);
    Vector2 getPos();
    int getMaxLevelUpdate();
    int getLevelUpdate();
    void addLevelUpdate();
    void setPos(int x, int y);
    TextureRegion getTexture();
}
