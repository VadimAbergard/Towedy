package com.mygdx.game.game_elements.music;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class ListMusic {
    private static final Music[] musics = {
            Gdx.audio.newMusic(Gdx.files.internal("music\\gameMusic.ogg")),
            Gdx.audio.newMusic(Gdx.files.internal("music\\menu.ogg"))
    };

    public static Music getMusic(TypeMusic typeMusic) {
        switch (typeMusic) {
            case GAME_MUSIC: return musics[0];
            case MENU_MUSIC: return musics[1];
            default: return null;
        }
    }
}
