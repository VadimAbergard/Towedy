package com.mygdx.game.game_elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.api.ClickEvent;
import com.mygdx.game.api.GameButton;
import com.mygdx.game.api.TextGame;
import com.mygdx.game.scene.GameScene;

public class ShopUpdateGoldMine {
    private final int COUNT_PRODUCT = 2;
    private final GameButton[] product = new GameButton[COUNT_PRODUCT];
    private boolean visible = true;

    public ShopUpdateGoldMine() {
        for (int i = 0; i < product.length; i++) {
            ClickEvent clickEvent = null;
            final int[] price = new int[1];
            Texture texture = null;

            switch (i) {
                case 0:
                    price[0] = 300;
                    texture = new Texture("sprites\\button\\icon7.png");
                    clickEvent = new ClickEvent() {
                        @Override
                        public void click() {
                            if(price[0] > InfoAboutPlayer.getMoneyInGame()) return;
                            else InfoAboutPlayer.removeMoneyInGame(price[0]);
                            GameScene.updateGoldMine();
                        }
                    };
                    break;
                case 1:
                    texture = new Texture("sprites\\button\\icon1.png");
                    clickEvent = new ClickEvent() {
                        @Override
                        public void click() {
                            GameScene.deleteGoldMine();
                            visible = false;
                        }
                    };
                    break;
            }

            product[i] = new GameButton();
            product[i].createButton(texture, clickEvent,
            new TextGame(15, "" + price[0]));
        }
    }

    public void draw(SpriteBatch batch) {
        if(!this.visible) return;
        for (int i = 0; i < product.length; i++) {

            if(Integer.parseInt(product[i].getText().getDefaultText()) > InfoAboutPlayer.getMoneyInGame() ||
                    GameScene.getGoldMineOnSquare().getMaxLevelUpdate() == GameScene.getGoldMineOnSquare().getLevelUpdate()) {
                if(!product[i].getText().getDefaultText().equals("0")) product[i].setUse(false);
                else product[i].setUse(true);
            }


            if(product[i].getText().getDefaultText().equals("0"))
                product[i].draw(batch, Gdx.graphics.getWidth() - 90,
                        Gdx.graphics.getHeight() - (90 * (i + 1)), 70, 70);
            else
                product[i].draw(batch, Gdx.graphics.getWidth() - 90,
                    Gdx.graphics.getHeight() - (90 * (i + 1)),
                    70,
                    70,
                    38,
                    15);
        }
    }
    public GameButton[] getProduct() {
        return this.product;
    }

    public boolean getVisible() {
        return this.visible;
    }

    public void setVisible(boolean value) {
        this.visible = value;
    }

    public void dispose() {
        for(GameButton button : product) {
            button.dispose();
        }
    }
}
