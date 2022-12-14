package com.mygdx.game.api;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class ColorSquare {

    private final Texture texture;

    public ColorSquare(int width, int height ,Color color) {
        Pixmap pixmap = new Pixmap(width * 10, height * 10, Pixmap.Format.RGBA4444);
        pixmap.setColor(color);
        pixmap.fillRectangle(0, 0, width * 10, height * 10);
        this.texture = new Texture(pixmap);
        pixmap.dispose();
    }

    public ColorSquare(int width, int height, float r, float g, float b, float a) {
        Pixmap pixmap = new Pixmap(width * 10, height * 10, Pixmap.Format.RGBA4444);
        pixmap.setColor(r, b, g, a);
        pixmap.fillRectangle(0, 0, width * 10, height * 10);
        this.texture = new Texture(pixmap);
        pixmap.dispose();
    }

    public Texture get() {
        return this.texture;
    }
}
