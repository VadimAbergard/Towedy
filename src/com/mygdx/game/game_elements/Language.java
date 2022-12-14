package com.mygdx.game.game_elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class Language {

    private static final JsonValue fromJsonLanguage;

    static {
        JsonValue fromJson = new JsonReader().parse(Gdx.files.internal("assets\\save\\save.json"));
        String nameLanguage = fromJson.getString("language");

        fromJsonLanguage = new JsonReader().parse(Gdx.files.internal("languages\\" + nameLanguage + ".json"));
    }

    public static String getText(String key) {
        return fromJsonLanguage.getString(key);
    }
}
