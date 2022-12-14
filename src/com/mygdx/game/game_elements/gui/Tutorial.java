package com.mygdx.game.game_elements.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.api.GUI;
import com.mygdx.game.api.TextGame;
import com.mygdx.game.game_elements.Language;

public class Tutorial implements GUI {
    private final Texture background;
    private final Texture[] images;
    private final TextGame text;
    private int state;

    public Tutorial() {
        this.background = new Texture("sprites\\elements\\blackSquare.png");
        this.images = new Texture[2];
        this.images[0] = new Texture("sprites\\elements\\tutorial.png");
        this.images[1] = new Texture("sprites\\elements\\tutorial2.png");
        this.state = 0;
        this.text = new TextGame(28, Language.getText("tutorialText1"));
    }

    @Override
    public void draw(SpriteBatch batch, float x, float y) {
        System.out.println(state);
        if(state != 2) batch.draw(background, x - 400, y, 400, 300);
        switch (state) {
            case 0:
                batch.draw(images[0], x- 400 + 100, y + 30, images[0].getWidth() * 8f, images[0].getHeight() * 8f);
                text.draw(batch, text.getDefaultText(),x - 400 + 50, y + 250);
                break;
            case 1:
                batch.draw(images[1], x- 400 + 100, y + 30, images[1].getWidth() * 8f, images[1].getHeight() * 8f);
                text.draw(batch, text.getDefaultText(),x - 400 + 50, y + 250);
                break;
        }

        if((Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.W) ||
                Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.D)) && state == 0) {
            state = 1;
            text.setDefaultText(Language.getText("tutorialText2"));
        }

        if((Gdx.input.isKeyPressed(Input.Keys.NUM_1) || Gdx.input.isKeyPressed(Input.Keys.NUM_2) ||
                Gdx.input.isKeyPressed(Input.Keys.NUM_3)) && state == 1) {
            state = 2;
        }
    }

    @Override
    public void click() {

    }

    @Override
    public void over() {

    }

    @Override
    public void write(int keycode) {

    }

    @Override
    public void setVisible(boolean value) {

    }

    @Override
    public boolean getVisible() {
        return false;
    }

    @Override
    public void dispose() {
        background.dispose();
        for (Texture image : images) {
            image.dispose();
        }
        text.dispose();
    }
}
