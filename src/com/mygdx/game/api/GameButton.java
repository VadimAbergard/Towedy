package com.mygdx.game.api;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class GameButton {

    private Texture texture;
    private Texture textureDefault;
    private Texture textureOver;
    private float x, y;
    private float width, height;
    private ClickEvent clickEvent;
    private TextGame textButton;
    private String textForDontUseButton;
    private Sound sound;
    private boolean playSound;
    private boolean isUse;

    public void createButton(Texture texture, ClickEvent clickEvent) {
        this.textureDefault = texture;
        this.texture = texture;
        this.clickEvent = clickEvent;
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        this.isUse = true;
    }

    public void createButton(Texture texture, ClickEvent clickEvent, TextGame text) {
        this.textureDefault = texture;
        this.texture = texture;
        this.clickEvent = clickEvent;
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        this.textButton = text;
        textForDontUseButton = "{GRAY}" + text.getDefaultText();
        this.isUse = true;
    }

    public void draw(SpriteBatch batch, float x, float y) {
        setInfoButton(x, y);
        batch.draw(this.texture, x, y);
    }

    public void draw(SpriteBatch batch, float x, float y, float width, float height) {
        setInfoButton(x, y, width, height);
        if(!isUse) batch.setColor(0.5f, 0.5f, 0.5f, 1f);
        batch.draw(this.texture, x, y, width, height);
        if(!isUse) batch.setColor(Color.WHITE);
    }

    public void draw(SpriteBatch batch, float x, float y, float width, float height, float xText, float yText) {
        setInfoButton(x, y, width, height);
        if(!isUse) batch.setColor(0.5f, 0.5f, 0.5f, 1f);
        batch.draw(this.texture, x, y, width, height);
        if(!isUse) batch.setColor(Color.WHITE);
        textButton.draw(batch, this.isUse ? this.textButton.getDefaultText() : this.textForDontUseButton, x + xText, y + yText);
    }

    public boolean click(float mouseX, float mouseY) {
        if(!this.isUse) return false;
        if(mouseX > this.x && mouseX < this.x + this.width &&
                Gdx.graphics.getHeight() - this.y > mouseY && Gdx.graphics.getHeight() - this.y - this.height < mouseY) {
            if(clickEvent != null) {
                clickEvent.click();
                return true;
            }
        }
        return false;
    }

    public boolean click() {
        if(!this.isUse) return false;
        if(Gdx.input.getX() > this.x && Gdx.input.getX() < this.x + this.width &&
                Gdx.graphics.getHeight() - this.y > Gdx.input.getY() && Gdx.graphics.getHeight() - this.y - this.height < Gdx.input.getY()) {
            if(clickEvent != null) {
                clickEvent.click();
                return true;
            }
        }
        return false;
    }

    public void setTextureOver(Texture textureOver) {
        this.textureOver = textureOver;
    }
    public void setSoundOver(Sound sound) {
        this.sound = sound;
    }
    public Texture getTextureOver() {
        return this.textureOver;
    }
    public TextGame getText() {
        return this.textButton;
    }
    public Sound getSound() {
        return this.sound;
    }
    public Texture getTexture() {
        return this.texture;
    }

    public boolean over(float mouseX, float mouseY) {
        if(this.textureOver == null || !this.isUse) return false;
        if(mouseX > this.x && mouseX < this.x + this.width &&
                Gdx.graphics.getHeight() - this.y > mouseY && Gdx.graphics.getHeight() - this.y - this.height < mouseY) {
            this.texture = this.textureOver;
            if(sound != null && !playSound) {
                sound.play();
                playSound = true;
            }
            return true;
        }
        this.texture = this.textureDefault;
        playSound = false;
        return false;
    }

    public boolean over() {
        if(this.textureOver == null || !this.isUse) return false;
        if(Gdx.input.getX() > this.x && Gdx.input.getX() < this.x + this.width &&
                Gdx.graphics.getHeight() - this.y > Gdx.input.getY() && Gdx.graphics.getHeight() - this.y - this.height < Gdx.input.getY()) {
            this.texture = this.textureOver;
            if(sound != null && !playSound) {
                sound.play();
                playSound = true;
            }
            return true;
        }
        this.texture = this.textureDefault;
        playSound = false;
        return false;
    }

    public Vector2 getPosition() {
        return new Vector2(this.x, this.y);
    }
    public void setClickEvent(ClickEvent event) {
        this.clickEvent = event;
    }
    public void setUse(boolean value) {
        this.isUse = value;
    }

    private void setInfoButton(float x, float y) {
        this.x = x;
        this.y = y;
    }

    private void setInfoButton(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void dispose() {
        this.texture.dispose();
        this.textureDefault.dispose();
        if(textureOver != null) this.textureOver.dispose();
        if(textButton != null) this.textButton.dispose();
        if(sound != null) this.sound.dispose();
    }
}
