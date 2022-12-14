package com.mygdx.game.game_elements.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class ListSound {
    private static final Sound[] sounds = {
            Gdx.audio.newSound(Gdx.files.internal("sound\\upLevel.ogg")),
            Gdx.audio.newSound(Gdx.files.internal("sound\\overButton.ogg")),
            Gdx.audio.newSound(Gdx.files.internal("sound\\notify.ogg"))
    };

    public static Sound getSound(TypeSound typeSound) {
        switch (typeSound) {
            case UP_LEVEL: return sounds[0];
            case OVER_BUTTON: return sounds[1];
            case NOTIFY: return sounds[2];
            default: return null;
        }
    }
}
