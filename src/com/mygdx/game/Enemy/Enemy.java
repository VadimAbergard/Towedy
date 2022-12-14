package com.mygdx.game.Enemy;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public interface Enemy {
    void update(float delta);
    int getDamageToBase();
    int getSpeed();
    int getDefaultSpeed();
    void setSpeed(int value);
    int getHealth();
    int getMoneyForKill();
    int getExForKill();
    void removeHealth(int value);
    Vector2 getPos();
    TextureRegion getTexture();
    boolean isDestroy();
    void destroy();
}
