package com.mygdx.game.game_elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.api.ClickEvent;
import com.mygdx.game.api.GameButton;
import com.mygdx.game.api.TextGame;
import com.mygdx.game.paterns.fabric.AbstractFactory;
import com.mygdx.game.paterns.fabric.Factory;
import com.mygdx.game.paterns.fabric.TypeFactory;
import com.mygdx.game.paterns.fabric.tower.TypeTower;
import com.mygdx.game.scene.GameScene;

public class ShopTower {
    private final int COUNT_PRODUCT = 4;
    private final GameButton[] product = new GameButton[COUNT_PRODUCT];
    private boolean visible = true;

    public ShopTower() {
        for (int i = 0; i < product.length; i++) {
            ClickEvent clickEvent = null;
            final Factory factory = new AbstractFactory().createFactory(TypeFactory.TOWER);
            final int[] price = new int[1];
            Texture texture = null;

            switch (i) {
                case 0:
                    price[0] = 100;
                    texture = new Texture("sprites\\button\\icon3.png");
                    clickEvent = new ClickEvent() {
                        @Override
                        public void click() {
                            if(price[0] > InfoAboutPlayer.getMoneyInGame()) return;
                            else InfoAboutPlayer.removeMoneyInGame(price[0]);
                            if(GameScene.getCellTower()[GameScene.getSelectSlotX()][GameScene.getSelectSlotY()] != null) return;
                            GameScene.setTower(factory.create(TypeTower.SHORT_TOWER));
                            GameScene.updateCircleTower(factory.create(TypeTower.SHORT_TOWER));
                        }
                    };
                    break;
                case 1:
                    price[0] = 100;
                    texture = new Texture("sprites\\button\\icon2.png");
                    clickEvent = new ClickEvent() {
                        @Override
                        public void click() {
                            if(price[0] > InfoAboutPlayer.getMoneyInGame()) return;
                            else InfoAboutPlayer.removeMoneyInGame(price[0]);
                            if(GameScene.getCellTower()[GameScene.getSelectSlotX()][GameScene.getSelectSlotY()] != null) return;
                            GameScene.setTower(factory.create(TypeTower.LONG_TOWER));
                            GameScene.updateCircleTower(factory.create(TypeTower.LONG_TOWER));
                        }
                    };
                    break;
                case 2:
                    price[0] = 100;
                    texture = new Texture("sprites\\button\\icon6.png");
                    clickEvent = new ClickEvent() {
                        @Override
                        public void click() {
                            if(price[0] > InfoAboutPlayer.getMoneyInGame()) return;
                            else InfoAboutPlayer.removeMoneyInGame(price[0]);
                            if(GameScene.getCellTower()[GameScene.getSelectSlotX()][GameScene.getSelectSlotY()] != null) return;
                            GameScene.setTower(factory.create(TypeTower.FREEZE_TOWER));
                            GameScene.updateCircleTower(factory.create(TypeTower.FREEZE_TOWER));
                        }
                    };
                    break;
                case 3:
                    price[0] = 1100;
                    texture = new Texture("sprites\\button\\icon4.png");
                    clickEvent = new ClickEvent() {
                        @Override
                        public void click() {
                            if(price[0] > InfoAboutPlayer.getMoneyInGame()) return;
                            else InfoAboutPlayer.removeMoneyInGame(price[0]);
                            if(GameScene.getCellTower()[GameScene.getSelectSlotX()][GameScene.getSelectSlotY()] != null) return;
                            GameScene.setTower(factory.create(TypeTower.TURRET_TOWER));
                            GameScene.updateCircleTower(factory.create(TypeTower.TURRET_TOWER));
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
        if(!GameScene.isPlayerBuyingTower() || !this.visible) return;
        for (int i = 0; i < product.length; i++) {

            if(Integer.parseInt(product[i].getText().getDefaultText()) > InfoAboutPlayer.getMoneyInGame())
                product[i].setUse(false);
            else product[i].setUse(true);

            switch (i) {
                case 1:
                    if(!InfoAboutPlayer.hasSkill(Skills.TOWER_LONG)) continue;
                    break;
                case 2:
                    if(!InfoAboutPlayer.hasSkill(Skills.TOWER_FREEZE)) continue;
                    break;
                case 3:
                    if(!InfoAboutPlayer.hasSkill(Skills.TOWER_TURRET)) continue;
                    break;
            }

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
        for (GameButton button : product) {
            button.dispose();
        }
    }
}
