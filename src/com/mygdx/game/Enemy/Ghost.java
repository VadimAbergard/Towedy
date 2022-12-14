package com.mygdx.game.Enemy;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.game_elements.InfoAboutPlayer;
import com.mygdx.game.game_elements.Language;
import com.mygdx.game.scene.GameScene;

public class Ghost implements Enemy {

    private final TextureRegion texture = GameScene.getSplitSpriteEnemy().getTexture(1, 1);
    private final Vector2 position;
    private final Vector2 point;
    private boolean reachedToPoint;
    private boolean isMoveVertical;
    private DirectionEnemy direction;
    private boolean destroy;
    private int health;
    private final int defaultSpeed;
    private int speed;

    public Ghost(/*Vector2 vector*/) {
        this.position = new Vector2();

        Vector2 vector = new Vector2();
        for (int i = 0; i < GameScene.getMap().length; i++) {
            if (GameScene.getMap()[i][0] == 1) vector = new Vector2(0, i);
        }

        this.health = 5;
        this.direction = DirectionEnemy.RIGHT;
        this.destroy = false;
        this.reachedToPoint = true;
        this.defaultSpeed = 25;
        this.speed = this.defaultSpeed;

        this.point = vector;

        final Vector2 finalVector = vector;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                position.y = finalVector.y * GameScene.getScale() + GameScene.getScale() / 3f;
            }
        }, 0f);
    }

    @Override
    public void update(float delta) {
        if(reachedToPoint) {
            findPoint();
            return;
        }
        final int splitToX = 50;
        final int splitToY = 50;

        boolean vertical = true;
        switch (direction) {
            case UP:
                vertical = this.position.y / splitToY > point.y + 0.2f;
                break;

            case DOWN:
                vertical = this.position.y / splitToY < point.y + 0.2f;
                break;
        }
        if(this.position.x / splitToX > point.x + 0.3f && vertical) {
            reachedToPoint = true;
            return;
        }

        switch (direction) {
            case RIGHT:
                this.position.x = this.position.x + delta * this.speed;
                break;

            case UP:
                this.position.y = this.position.y + delta * this.speed;
                break;

            case DOWN:
                this.position.y = this.position.y - delta * this.speed;
                break;
        }
    }

    @Override
    public int getDamageToBase() {
        return 5;
    }

    @Override
    public int getSpeed() {
        return this.speed;
    }

    @Override
    public int getDefaultSpeed() {
        return this.defaultSpeed;
    }

    @Override
    public void setSpeed(int value) {
        this.speed = value;
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public int getMoneyForKill() {
        return 20;
    }

    @Override
    public int getExForKill() {
        return 50;
    }

    @Override
    public void removeHealth(int value) {
        this.health -= value;
        if(this.health <= 0) {
            this.destroy = true;
            this.destroy();
        }
    }

    @Override
    public Vector2 getPos() {
        return this.position;
    }

    @Override
    public TextureRegion getTexture() {
        return texture;
    }

    @Override
    public boolean isDestroy() {
        return destroy;
    }
    @Override
    public void destroy() {
        this.getPos().x = 1000000;
        InfoAboutPlayer.addMoneyInGame(this.getMoneyForKill());
        InfoAboutPlayer.addEx(this.getExForKill());
        InfoAboutPlayer.addMoneyGlobal(1);
        GameScene.getSliderLineEnemy().setCount(GameScene.getSliderLineEnemy().getCount() + 1);
        //GameScene.getGameWorld().getEnemy().remove(this);
    }

    private void findPoint() {
        if(!isMoveVertical)
            for (int i = -7; i < 7; i++) {
                if(i == 0) continue;
                try {
                    if (GameScene.getPointForMoveEnemy()[(int) point.y + i][(int) point.x] == 1) {
                        point.set(point.x, point.y + i);
                        reachedToPoint = false;
                        isMoveVertical = true;
                        direction = i > 0 ? DirectionEnemy.UP : DirectionEnemy.DOWN;
                        return;
                    }
                } catch (ArrayIndexOutOfBoundsException e) { continue;}
            }


        for (int i = 1; i < 7; i++) {
            try {
                if(GameScene.getPointForMoveEnemy()[(int)point.y][(int)point.x + i] == 1) {
                    point.set(point.x + i, point.y);
                    reachedToPoint = false;
                    isMoveVertical = false;
                    direction = DirectionEnemy.RIGHT;
                    return;
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {}
        }

        // when enemy will be at base
        destroy = true;
        GameScene.getBase().removeHealth(this.getDamageToBase());
        GameScene.getSliderLineEnemy().setCount(GameScene.getSliderLineEnemy().getCount() + 1);
        GameScene.getMessage().show("{RED}" + Language.getText("baseDamaged"));
    }
}
