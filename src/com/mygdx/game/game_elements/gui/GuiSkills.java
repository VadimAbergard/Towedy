package com.mygdx.game.game_elements.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.api.*;
import com.mygdx.game.game_elements.InfoAboutPlayer;
import com.mygdx.game.game_elements.Language;
import com.mygdx.game.game_elements.Skills;
import com.mygdx.game.game_elements.sound.ListSound;
import com.mygdx.game.game_elements.sound.TypeSound;

public class GuiSkills implements GUI {

    private final Texture background;
    private final int countButton = 3;
    private final GameButton[] buttons;
    private boolean visible;
    public GuiSkills() {
        this.visible = false;
        this.background = new Texture("sprites\\elements\\backgroundGreen.png");
        this.buttons = new GameButton[countButton];


        for (int i = 0; i < countButton; i++) {
            ClickEvent clickEvent = null;
            final int[] price = new int[1];
            final int[] level = new int[1];
            final String[] nameSkill = new String[1];
            Texture texture = null;

            switch (i) {
                case 0:
                    price[0] = 50;
                    level[0] = 1;
                    texture = new Texture("sprites\\button\\icon2.png");
                    nameSkill[0] = Language.getText("towerLong");
                    clickEvent = new ClickEvent() {
                        @Override
                        public void click() {
                            if(InfoAboutPlayer.getMoneyGlobal() < price[0]) return;
                            InfoAboutPlayer.removeMoneyGlobal(price[0]);
                            InfoAboutPlayer.addSkill(Skills.TOWER_LONG);
                        }
                    };
                    break;
                case 1:
                    price[0] = 125;
                    level[0] = 2;
                    texture = new Texture("sprites\\button\\icon6.png");
                    nameSkill[0] = Language.getText("towerFreeze");
                    clickEvent = new ClickEvent() {
                        @Override
                        public void click() {
                            if(InfoAboutPlayer.getMoneyGlobal() < price[0]) return;
                            InfoAboutPlayer.removeMoneyGlobal(price[0]);
                            InfoAboutPlayer.addSkill(Skills.TOWER_FREEZE);
                        }
                    };
                    break;
                case 2:
                    price[0] = 200;
                    level[0] = 3;
                    nameSkill[0] = Language.getText("towerTurret");
                    texture = new Texture("sprites\\button\\icon4.png");
                    clickEvent = new ClickEvent() {
                        @Override
                        public void click() {
                            if(InfoAboutPlayer.getMoneyGlobal() < price[0]) return;
                            InfoAboutPlayer.removeMoneyGlobal(price[0]);
                            InfoAboutPlayer.addSkill(Skills.TOWER_TURRET);
                        }
                    };
                    break;
            }

            this.buttons[i] = new GameButton();
            this.buttons[i].createButton(texture, clickEvent,
                    new TextGame(13,
                            "{YELLOW}" + nameSkill[0] +
                                    "/n__{WHITE}" + Language.getText("price") + ":_{YELLOW}" + price[0] +
                            "/n__{WHITE}__" + Language.getText("requiredLevel") + ":_{YELLOW}" + level[0]));
            this.buttons[i].setSoundOver(ListSound.getSound(TypeSound.OVER_BUTTON));
        }
    }

    @Override
    public void draw(SpriteBatch batch, float x, float y) {
        if(!this.visible) return;
        batch.draw(this.background, x, y, Gdx.graphics.getWidth() / 3.8f, Gdx.graphics.getHeight());

        int indent = 0;
        for (int i = 0;i < countButton;i++) {
            switch (i) {
                case 0:
                    if(InfoAboutPlayer.hasSkill(Skills.TOWER_LONG)) continue;
                    break;
                case 1:
                    if(InfoAboutPlayer.hasSkill(Skills.TOWER_FREEZE)) continue;
                    break;
                case 2:
                    if(InfoAboutPlayer.hasSkill(Skills.TOWER_TURRET)) continue;
                    break;
            }

            if(InfoAboutPlayer.getMoneyGlobal() >= 50) buttons[i].setUse(true);
            else buttons[i].setUse(false);
            buttons[i].draw(batch, 10 + x, Gdx.graphics.getHeight() - 80 - (90 * indent) + y,
                    64, 64,
                    70, 60);
            indent++;
        }

    }

    @Override
    public void click() {
        if(!this.visible) return;
        for (int i = 0;i < countButton;i++) {
            this.buttons[i].click();
        }
    }

    @Override
    public void over() {
        for (int i = 0;i < countButton;i++) {
            this.buttons[i].over();
        }
    }

    @Override
    public void write(int keycode) {}

    @Override
    public void setVisible(boolean value) {
        visible = value;
    }

    @Override
    public boolean getVisible() {
        return this.visible;
    }

    @Override
    public void dispose() {
        background.dispose();
        for (GameButton button : buttons) {
            button.dispose();
        }
    }
}
