package com.mygdx.game.game_elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.api.TextGame;
import com.mygdx.game.game_elements.sound.ListSound;
import com.mygdx.game.game_elements.sound.TypeSound;

public class Message {
    private final TextGame text;
    private boolean visible;
    private String message = "";
    private Sound sound;

    public Message(TextGame text) {
        this.sound = ListSound.getSound(TypeSound.NOTIFY);
        this.text = text;
        this.visible = false;
    }

    public void show(String message) {
        visible = true;
        this.message = message;
        this.sound.play();

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                visible = false;
            }
        }, 30);
    }

    public void draw(SpriteBatch batch) {
        if(this.visible) text.draw(batch, message, 50, Gdx.graphics.getHeight() - 100);
    }
}
