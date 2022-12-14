package com.mygdx.game.api;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SelectList {
    //private int countSlots;
    private final GameButton[] gameButtons;
    private final Texture arrow;
    private final GameButton mainButton;
    private boolean isDeployed;
    public SelectList(GameButton button, Texture arrow, GameButton... gameButtons) {
        this.gameButtons = gameButtons;
        this.arrow = arrow;

        this.mainButton = new GameButton();
        mainButton.createButton(button.getTexture(), new ClickEvent() {
            @Override
            public void click() {
                isDeployed = !isDeployed;
            }
        }, button.getText());
        mainButton.setTextureOver(button.getTextureOver());
        mainButton.setSoundOver(button.getSound());
    }

    public void draw(SpriteBatch batch, float x, float y, float width, float height) {
        mainButton.draw(batch, x, y, width, height, 10, 43);
        batch.draw(this.arrow, x + width - width / 5f, y + height / 4f, width / 7f, height / 2.5f);

        if(this.isDeployed) {
            int i = 1;
            for (GameButton button : this.gameButtons) {
                button.draw(batch, x + width - width / 1.3f, y - (height * i++), width / 1.3f, height, 10, 47);
            }
        }
    }

    public void click() {
        mainButton.click();
        if(!isDeployed) return;
        for (GameButton button : this.gameButtons) {
            if(button.click()) isDeployed = false;
        }
    }

    public void over() {
        mainButton.over();
        if(!isDeployed) return;
        for (GameButton button : this.gameButtons) {
            button.over();
        }
    }

    public void dispose() {
        mainButton.dispose();
        arrow.dispose();
        for (GameButton button: this.gameButtons) {
            button.dispose();
        }
    }
}
