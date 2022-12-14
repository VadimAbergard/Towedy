package com.mygdx.game.api;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class TextGame {

    private final FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    private final BitmapFont text;
    private String defaultText;
    private int length;

    public TextGame(int size) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts\\russianFont.ttf"));
        parameter.size = size;
        this.defaultText = null;
        parameter.borderWidth = 1.4f;

        setDefaultSettings();

        text = generator.generateFont(parameter);
        generator.dispose();
    }

    public TextGame(int size, String defaultText) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts\\russianFont.ttf"));
        parameter.size = size;
        this.defaultText = defaultText;
        parameter.borderWidth = 1.4f;

        setDefaultSettings();

        text = generator.generateFont(parameter);
        generator.dispose();
    }

    public TextGame(int size, String defaultText, float borderWidth) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts\\russianFont.ttf"));
        parameter.size = size;
        this.defaultText = defaultText;
        parameter.borderWidth = borderWidth;

        setDefaultSettings();

        text = generator.generateFont(parameter);
        generator.dispose();
    }

    public void draw(SpriteBatch batch, final String str, float x, float y) {
        final String[] colorCode = {"{YELLOW}", "{RED}", "{GREEN}", "{GRAY}", "{WHITE}"};
        Color color = Color.WHITE;
        final char[] strChar = str.toCharArray();

        int indent = 0;
        int indentY = 0;
        for (int i = 0; i < strChar.length; i++) {
            for (int l = 0; l < colorCode.length; l++) {
                boolean smooth = false;
                int typeColor = 0;
                for (int k = 0; k < colorCode[l].toCharArray().length; k++) {
                    try {
                        smooth = true;
                        typeColor = l;
                        if (strChar[i + k] != colorCode[l].toCharArray()[k]) {
                            smooth = false;
                            break;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        break;
                    }
                }
                if(smooth) {
                    switch (l) {
                        case 0:
                            color = Color.YELLOW;
                            break;
                        case 1:
                            color = Color.RED;
                            break;
                        case 2:
                            color = Color.GREEN;
                            break;
                        case 3:
                            color = Color.GRAY;
                            break;
                        case 4:
                            color = Color.WHITE;
                            break;
                    }
                    for (int j = 0; j < colorCode[typeColor].toCharArray().length; j++) {
                        strChar[i + colorCode[typeColor].toCharArray().length - 1 - j] = ' ';
                    }
                }
                this.length = strChar.length - colorCode[typeColor].toCharArray().length;
            }
            //System.out.println(strChar[i] + "=" + "\' \'" + "&" + strChar[i + 1] + "=" + "\' \'" + " " + indent + " " + (strChar[i] == ' ' && strChar[i + 1] == ' '));
            try {
                if (strChar[i] == ' '/* && strChar[i] == ' '*/) continue;
            } catch (ArrayIndexOutOfBoundsException e) {break;}
            if(strChar[i] == '_') strChar[i] = ' ';
            if(strChar[i] == '/' && strChar[i + 1] == 'n') {
                indentY++;
                indent = 0;
                strChar[i] = ' ';
                strChar[i + 1] = ' ';
            }
            text.setColor(color);
            //System.out.println(indent);
            text.draw(batch, "" + strChar[i], x + parameter.size * indent++ / 1.3f - ((parameter.size * 2f) * indentY), y - ((parameter.size / 8f) * (parameter.size * indentY)));
            //text.draw(batch, "" + strChar[i], Gdx.graphics.getWidth() - 600 + parameter.size * indent++ / 1.8f, 50);
        }
        //Gdx.app.exit();
    }

    public int getLength() {
        return this.length;
    }
    public String getDefaultText() {
        return this.defaultText;
    }

    public void setDefaultText(String text) {
        this.defaultText = text;
    }

    private void setDefaultSettings() {
        StringBuilder FONT_CHARS = new StringBuilder();
        for ( int i = 0x20; i < 0x7B; i++ ) FONT_CHARS.append((char) i); // цифры и весь английский
        for ( int i = 0x401; i < 0x452; i++ ) FONT_CHARS.append((char) i); // русские

        parameter.characters = FONT_CHARS.toString();
    }

    public void dispose() {
        //if(text != null) text.dispose();
    }
}
