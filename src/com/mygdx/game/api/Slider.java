package com.mygdx.game.api;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Slider {
    private float x, y;
    private float width, height;
    private final Texture textureBackround;
    private final Texture textureFill;
    private final Texture point;
    private int maxCount;
    private int count;
    private final boolean use;

    public Slider(Texture textureBackround, Texture texturePoint, Texture textureFill, int maxCount, boolean use) {
        this.point = texturePoint;
        this.textureBackround = textureBackround;
        this.textureFill = textureFill;
        this.maxCount = maxCount;
        this.use = use;

        this.count = 0;
    }

    public void draw(SpriteBatch batch, float x, float y) {
        setInfoSlider(x, y);
        batch.draw(this.textureBackround, x, y);
    }

    public void draw(SpriteBatch batch, float x, float y, float width, float height) {
        setInfoSlider(x, y, width, height);
        batch.draw(this.textureBackround, x, y, width, height);
        batch.draw(this.textureFill, x + 3, y + 3, width / this.maxCount * this.count - 6, height - 6);
        batch.draw(this.point, x + (width / this.maxCount * this.count) - height + height / 4f, y - height / 8f, height + height / 2f, height + height / 2f);
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setMaxCount(int count) {
        this.maxCount = count;
    }
    public int getMaxCount() {
        return this.maxCount;
    }

    public int getCount() {
        return count;
    }

    public void dispose() {
        textureBackround.dispose();
        textureFill.dispose();
        point.dispose();
    }

    private void setInfoSlider(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    private void setInfoSlider(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
