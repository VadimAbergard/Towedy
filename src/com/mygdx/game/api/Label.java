package com.mygdx.game.api;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Label {
    private final Texture texture;
    private final TextGame displayedText;
    private final String defaultText;
    private String text;
    private float x, y;
    private float width, height;
    private boolean writeNow;
    private final int maxLength;

    /*public WriteField() {
        this.write = false;
    }*/

    public Label(Texture texture, TextGame text, int maxLength) {
        //this.textureDefault = texture;
        this.writeNow = false;
        this.displayedText = text;
        this.defaultText = text.getDefaultText();
        this.texture = texture;
        this.text = "";
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        this.maxLength = maxLength;
    }

    public void draw(SpriteBatch batch, float x, float y, float width, float height, float xText, float yText) {
        setInfoButton(x, y, width, height);

        if(writeNow) batch.setColor(0.5f, 0.5f, 0.5f, 1f);
        batch.draw(this.texture, x, y, width, height);
        if(writeNow) batch.setColor(Color.WHITE);

        if(!text.equals("") || writeNow) {
            displayedText.draw(batch, this.text, x + xText, y + yText);
        } else
            displayedText.draw(batch, this.displayedText.getDefaultText(), x + xText, y + yText);
    }

    public boolean click(float mouseX, float mouseY) {
        if(mouseX > this.x && mouseX < this.x + this.width &&
                Gdx.graphics.getHeight() - this.y > mouseY && Gdx.graphics.getHeight() - this.y - this.height < mouseY) {
            writeNow = true;
            return true;
        }
        writeNow = false;
        return false;
    }

    public void write(int keycode) {
        if(!writeNow) return;
        if(Input.Keys.toString(keycode).equals("Escape")) {
            writeNow = false;
            return;
        }
        final String[] ignoredKey = {"L-Shift", "R-Shift", "L-Ctrl", "R-Ctrl", "L-Alt", "R-Alt",
            "Numpad 0", "Numpad 1", "Numpad 2", "Numpad 3", "Numpad 4", "Numpad 5", "Numpad 6", "Numpad 7", "Numpad 8", "Numpad 9",
                "Num -", "Num +", "Num /", "Num *", "Num Lock", "Num .", "Num Enter", "Menu", "SYM", "Tab", "Caps Lock",
                "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12",
                "Forward Delete", "Insert", "Home", "End", "Page Down", "Page Up", "Scroll Lock", "Print", "Enter",
                "Left", "Down", "Up", "Right"};
        for (String key : ignoredKey) {
            if (Input.Keys.toString(keycode).equals(key)) return;
        }
        if (Input.Keys.toString(keycode).equals("Delete")) {
            String text = "";
            char[] textChar = this.text.toCharArray();
            for (int i = 0; i < textChar.length; i++) {
                if(i == textChar.length - 1) break;
                text += textChar[i];
            }
            this.text = text;
            return;
        }
        if(this.text.toCharArray().length == maxLength) return;
        if(Input.Keys.toString(keycode).equals("Space")) {
            text += "_";
            return;
        }
        text += Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) ? Input.Keys.toString(keycode) : Input.Keys.toString(keycode).toLowerCase();
    }

    public String getText() {
        return this.text;
    }

    public int getLengthText() {
        return this.text.toCharArray().length;
    }

    public Texture getTexture() {
        return this.texture;
    }

    public boolean getWriteNow() {
        return this.writeNow;
    }

    public boolean isTextNumber() {
        char[] textChars = this.text.toCharArray();
        boolean is = false;
        for (int i = 0; i < textChars.length; i++) {
            if(!Character.isDigit(textChars[i])) {
                is = false;
                break;
            }
            is = true;
        }
        return is;
    }
    //@TODO write only number!!!!
    public void setWriteNumber(boolean value) {

    }

    private void setInfoButton(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
