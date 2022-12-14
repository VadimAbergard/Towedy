package com.mygdx.game.api;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface GUI {
    void draw(SpriteBatch batch, float x, float y);
    void click();
    void over();
    void write(int keycode);
    void setVisible(boolean value);
    boolean getVisible();
    void dispose();
}
