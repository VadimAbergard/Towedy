package com.mygdx.game.scene;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Enemy.Enemy;
import com.mygdx.game.Tower.Tower;
import com.mygdx.game.api.*;
import com.mygdx.game.game_elements.*;
import com.mygdx.game.game_elements.gui.Tutorial;
import com.mygdx.game.game_elements.music.ListMusic;
import com.mygdx.game.game_elements.music.TypeMusic;
import com.mygdx.game.game_elements.sound.ListSound;
import com.mygdx.game.game_elements.sound.TypeSound;
import com.mygdx.game.goldMine.GoldMine;
import com.mygdx.game.paterns.fabric.enemy.TypeEnemy;

import java.util.ArrayList;
import java.util.List;

public class GameScene implements Screen, InputProcessor {

    private final SpriteBatch batch = new SpriteBatch();
    private static final SplitSprite splitSprite = new SplitSprite(new Texture("sprites\\map\\tiledMap.png"), 15, 1);
    private static final SplitSprite splitSpriteEnemy = new SplitSprite(new Texture("sprites\\enemy\\enemy.png"), 4, 1);

    private static Slider sliderLineEnemy;

    private static int amountWave = 0;

    private Texture redSquare;
    private int[] redSquarePos = new int[2];
    private static ShopTower shopTower;
    private static ShopGoldMine shopGoldMine;
    private static ShopUpdateTower shopUpdateTower;
    private static ShopUpdateGoldMine shopUpdateGoldMine;
    private static Base base;
    private static float scale;
    private static float zoom;
    private boolean zoomMove;
    private static boolean isPlayerBuyingTower;
    private static int selectSlotX, selectSlotY;
    private static int[][] map;

    private static GameButton[][] mapField;
    private static TextureRegion[][] tiledMap;
    private static int[][] pointForMoveEnemy;
    private Texture[][] maskOnMap;
    private static GameButton[][] cellTower;
    private static GameButton[][] cellGoldMine;
    private static GameWorld gameWorld;

    private Wave wave;

    private static Texture circleTower;
    private int animationEnemy;

    private OrthographicCamera camera;
    private OrthographicCamera cameraUI;
    private final Game game;
    private TextGame textMoney;
    private TextGame textWave;
    private TextGame textLevel;
    private TextGame textNextLevel;
    private TextGame textTimerWaves;
    private static Message message;
    private static boolean pause;
    private Texture blackSquare;
    private GameButton buttonPauseReturn;
    private GameButton buttonPauseExit;
    private Music music;
    private boolean musicIntroduction;
    private boolean generateGame;
    private GameButton buttonConfirm;
    private GameButton buttonGenerateAgain;
    private GameButton buttonExit;
    private SelectList listDifficulty;
    private Difficulty difficulty;
    private Difficulty difficultyInSettingsMap;
    private float timeNextWave;
    private boolean waveGoing = false;
    private Texture arrow;
    private boolean showTextureArrow;
    private int speedGame;
    private GUI guiTutorial;


    public GameScene(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        InfoAboutPlayer.setMoneyInGame(100);
        guiTutorial = new Tutorial();
        speedGame = 1;
        music = ListMusic.getMusic(TypeMusic.GAME_MUSIC);
        pause = false;
        blackSquare = new Texture("sprites\\elements\\blackSquare.png");
        buttonPauseReturn = new GameButton();
        buttonPauseReturn.createButton(new Texture("sprites\\button\\buttonPause.png"), new ClickEvent() {
            @Override
            public void click() {
                pause = !pause;
            }
        }, new TextGame(28, Language.getText("resume")));
        buttonPauseReturn.setTextureOver(new Texture("sprites\\button\\buttonPauseOver.png"));

        buttonPauseExit = new GameButton();
        buttonPauseExit.createButton(new Texture("sprites\\button\\buttonPause.png"), new ClickEvent() {
            @Override
            public void click() {
                music.stop();
                dispose();
                game.setScreen(new MainMenu(game));
            }
        }, new TextGame(28, Language.getText("exit")));
        buttonPauseExit.setTextureOver(new Texture("sprites\\button\\buttonPauseOver.png"));

        timeNextWave = 20f;

        textMoney = new TextGame(30);
        textWave = new TextGame(30);
        textLevel = new TextGame(30);
        textNextLevel = new TextGame(15);
        textTimerWaves = new TextGame(23);

        message = new Message(new TextGame(18, null, 1f));

        zoomMove = false;
        isPlayerBuyingTower = false;
        selectSlotY = 0;
        selectSlotX = 0;

        gameWorld = new GameWorld();

        animationEnemy = 0;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                animationEnemy = animationEnemy == 0 ? 1 : 0;
            }
        }, 1f, 1);

        shopTower = new ShopTower();
        shopGoldMine = new ShopGoldMine();
        shopUpdateTower = new ShopUpdateTower();
        shopUpdateGoldMine = new ShopUpdateGoldMine();
        base = new Base();
        wave = new Wave();

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cameraUI = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Gdx.input.setInputProcessor(this);


        sliderLineEnemy = new Slider(new Texture("sprites\\slider\\sliderLineEnemy.png"),
                new Texture("sprites\\slider\\iconSwords.png"),
                new Texture("sprites\\slider\\sliderLineEnemyFill.png"), 1,  false);

        generateGame = MainMenu.playerStartNewGame();
        if(generateGame) {
            arrow = new Texture("sprites\\elements\\arrow2.png");
            Texture whiteSquare = new ColorSquare(1, 1, 1, 1, 1, 0.3f).get();
            Texture muchWhiteSquare = new ColorSquare(1, 1, 1, 1, 1, 0.6f).get();
            difficulty = Difficulty.EASY;
            difficultyInSettingsMap = Difficulty.EASY;

            buttonConfirm = new GameButton();
            buttonConfirm.createButton(nullTexture(), new ClickEvent() {
                @Override
                public void click() {
                    InfoAboutPlayer.setDifficultyInGame(difficultyInSettingsMap);
                    generateGame = false;
                }
            }, new TextGame(25, Language.getText("confirm")));
            buttonConfirm.setTextureOver(whiteSquare);
            buttonConfirm.setSoundOver(ListSound.getSound(TypeSound.OVER_BUTTON));


            buttonGenerateAgain = new GameButton();
            buttonGenerateAgain.createButton(nullTexture(), new ClickEvent() {
                @Override
                public void click() {
                    difficultyInSettingsMap = difficulty;
                    generateMap();
                }
            }, new TextGame(21, Language.getText("generateAgain")));
            buttonGenerateAgain.setTextureOver(whiteSquare);
            buttonGenerateAgain.setSoundOver(ListSound.getSound(TypeSound.OVER_BUTTON));


            buttonExit = new GameButton();
            buttonExit.createButton(nullTexture(), new ClickEvent() {
                @Override
                public void click() {
                    music.stop();
                    dispose();
                    game.setScreen(new MainMenu(game));
                }
            }, new TextGame(25, Language.getText("exitInMenu")));
            buttonExit.setTextureOver(whiteSquare);
            buttonExit.setSoundOver(ListSound.getSound(TypeSound.OVER_BUTTON));

            GameButton buttonListEasy = new GameButton();
            buttonListEasy.createButton(whiteSquare, new ClickEvent() {
                @Override
                public void click() {
                    difficulty = Difficulty.EASY;
                    showArrow();
                }
            }, new TextGame(25, "{GREEN}" + Language.getText("easy")));
            buttonListEasy.setTextureOver(muchWhiteSquare);
            buttonListEasy.setSoundOver(ListSound.getSound(TypeSound.OVER_BUTTON));

            GameButton buttonListNormal = new GameButton();
            buttonListNormal.createButton(whiteSquare, new ClickEvent() {
                @Override
                public void click() {
                    difficulty = Difficulty.NORMAL;
                    showArrow();
                }
            }, new TextGame(20, "{YELLOW}" + Language.getText("normal")));
            buttonListNormal.setTextureOver(muchWhiteSquare);
            buttonListNormal.setSoundOver(ListSound.getSound(TypeSound.OVER_BUTTON));

            GameButton buttonListHard = new GameButton();
            buttonListHard.createButton(whiteSquare, new ClickEvent() {
                @Override
                public void click() {
                    difficulty = Difficulty.HARD;
                    showArrow();
                }
            }, new TextGame(25, "{RED}" + Language.getText("hard")));
            buttonListHard.setTextureOver(muchWhiteSquare);
            buttonListHard.setSoundOver(ListSound.getSound(TypeSound.OVER_BUTTON));


            GameButton buttonList = new GameButton();
            buttonList.createButton(whiteSquare, null, new TextGame(16, Language.getText("diffuculty")));
            buttonList.setTextureOver(muchWhiteSquare);
            buttonList.setSoundOver(ListSound.getSound(TypeSound.OVER_BUTTON));

            listDifficulty = new SelectList(buttonList, new Texture("sprites\\button\\arrow.png"),
                    buttonListEasy, buttonListNormal, buttonListHard);
        }

        generateMap();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        delta = delta * speedGame;

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        drawMap(batch);
        //System.out.println("tower - " + gameWorld.getTower().size() + ", goldMine - " + gameWorld.getGoldMine().size());
        int orderAnimEnemy = 1;
        for (Enemy enemy : gameWorld.getEnemy()) {
            if(!enemy.isDestroy()) {
                //batch.setColor(Color.BLUE);
                batch.draw(enemy.getTexture(), enemy.getPos().x, enemy.getPos().y + animationEnemy * 3 * orderAnimEnemy, scale / 2f, scale / 2f);
                //batch.setColor(Color.WHITE);
                orderAnimEnemy *= -1;
                enemy.update(delta);
            }
        }


        for (Tower tower : gameWorld.getTower()) {
            if(tower.getPos().x == selectSlotX && tower.getPos().y == selectSlotY) {
                if(circleTower != null)
                    batch.draw(circleTower, tower.getPos().y * scale - tower.getDistantion() / 2f + scale / 2f,
                        tower.getPos().x * scale - tower.getDistantion() / 2f + scale / 2f);
            }
            if(pause) continue;
            tower.update(delta);
        }

        for (GoldMine goldMine : gameWorld.getGoldMine()) {
            if(pause) continue;
            goldMine.update(delta);
        }



        //UI
        cameraUI.update();
        batch.setProjectionMatrix(cameraUI.combined);

        if(!generateGame) {
            guiTutorial.draw(batch, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 3f);

            shopTower.draw(batch);
            shopGoldMine.draw(batch);
            shopUpdateTower.draw(batch);
            shopUpdateGoldMine.draw(batch);

            message.draw(batch);

            sliderLineEnemy.draw(batch, Gdx.graphics.getWidth() / 4f,
                    /*wave.getTotalEnemies() >= 0 ? -100 : */Gdx.graphics.getHeight() - 60, Gdx.graphics.getWidth() / 2f, 30);

            String money = "" + InfoAboutPlayer.getMoneyInGame();

            textMoney.draw(batch, Language.getText("money") + ":{YELLOW}" + money,
                    Gdx.graphics.getWidth() - (textMoney.getLength() * 21f), 50);
            if(timeNextWave > 0) {
                textTimerWaves.draw(batch, Language.getText("nextWave") + ":" + (timeNextWave <= 10 ? "{RED}" : "{YELLOW}") + ((int) (Math.floor(timeNextWave))),
                        10, Gdx.graphics.getHeight() - 20);
                timeNextWave -= delta;
            }
            textWave.draw(batch, Language.getText("wave") + ":{YELLOW}" + amountWave,
                    10, 50);
            textLevel.draw(batch, Language.getText("level") + ":{YELLOW}" + InfoAboutPlayer.getLevel(),
                    Gdx.graphics.getWidth() / 2f - textLevel.getLength() * 8f, 50);
            textNextLevel.draw(batch, Language.getText("nextLevel")+ ":{YELLOW}" + InfoAboutPlayer.getEx() + "/" + InfoAboutPlayer.getExNextLevel(),
                    Gdx.graphics.getWidth() / 2f - textNextLevel.getLength() * 4.5f, 70);

            if (pause) {
                batch.draw(blackSquare, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                final float width = buttonPauseExit.getTexture().getWidth() * 7.5f;
                final int height = buttonPauseExit.getTexture().getHeight() * 5;
                buttonPauseReturn.draw(batch,
                        Gdx.graphics.getWidth() / 2f - width / 2f,
                        Gdx.graphics.getHeight() / 2f - height / 2f,
                        width,
                        height,
                        10,
                        50);
                buttonPauseExit.draw(batch,
                        Gdx.graphics.getWidth() / 2f - width / 2f,
                        Gdx.graphics.getHeight() / 2f - height / 2f - height - 20,
                        width,
                        height,
                        10,
                        50);
            }
        } else {
            buttonConfirm.draw(batch, 0, 100, 340,  50, 5, 40);
            buttonGenerateAgain.draw(batch, 0, 50, 340,  50, 5, 40);
            buttonExit.draw(batch, 0, 0, 340,  50, 5, 40);

            listDifficulty.draw(batch, 0, Gdx.graphics.getHeight() - 200, 200, 50);
            if(showTextureArrow) batch.draw(arrow,
                    buttonGenerateAgain.getPosition().x + 350,
                    buttonGenerateAgain.getPosition().y + 8,
                    arrow.getWidth() * 5, arrow.getHeight() * 5);
        }
        batch.end();

        final float SPEED_CAMERA = 12f + (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) ? 10f : 0f);
        if(!pause) {
            if (Gdx.input.isKeyPressed(Input.Keys.D)) camera.position.x = camera.position.x + SPEED_CAMERA;
            if (Gdx.input.isKeyPressed(Input.Keys.A)) camera.position.x = camera.position.x - SPEED_CAMERA;
            if (Gdx.input.isKeyPressed(Input.Keys.W)) camera.position.y = camera.position.y + SPEED_CAMERA;
            if (Gdx.input.isKeyPressed(Input.Keys.S)) camera.position.y = camera.position.y - SPEED_CAMERA;
        }

        final int SPEED_ZOOM = 2;
        if(zoomMove) {
            if(scale < zoom) {
                scale += SPEED_ZOOM;
                if(scale > zoom) zoomMove = false;
            }
            else if(scale > zoom) {
                scale -= SPEED_ZOOM;
                if(scale < zoom) zoomMove = false;
            }
        }

        if(InfoAboutPlayer.getEx() >= InfoAboutPlayer.getExNextLevel()) {
            ListSound.getSound(TypeSound.UP_LEVEL).play();
            InfoAboutPlayer.addLevel(1);
            InfoAboutPlayer.setExNextLevel();
            InfoAboutPlayer.resetEx();
        }

        if(!generateGame) {
            drawOverButton();
            buttonPauseReturn.over(Gdx.input.getX(), Gdx.input.getY());
            buttonPauseExit.over(Gdx.input.getX(), Gdx.input.getY());
            if(timeNextWave <= 0 && !waveGoing) {
                generateWave();
                waveGoing = true;
            }
            if(sliderLineEnemy.getCount() == sliderLineEnemy.getMaxCount()) {
                sliderLineEnemy.setCount(0);
                timeNextWave = 20;
                clearEnemyInGameWorld();
                waveGoing = false;
            }
            if(waveGoing) wave.update(delta);
            if(base.getHealth() <= 0) game.setScreen(new MainMenu(game));
        } else {
            listDifficulty.over();
            buttonGenerateAgain.over();
            buttonConfirm.over();
            buttonExit.over();
        }

        if(!music.isPlaying()) {
            music.play();
            if(!musicIntroduction) {
                musicIntroduction = true;
                music.setVolume(0);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        music.setVolume(music.getVolume() + 1 / 100f);
                    }
                }, 0, 0.1f, 100);
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportHeight = height;
        camera.viewportWidth = width;
        cameraUI.viewportHeight = height;
        cameraUI.viewportWidth = width;

        camera.position.set(new Vector3(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0));
        camera.update();
        cameraUI.position.set(new Vector3(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0));
        cameraUI.update();

        scale = 50;
        zoom = scale;

        shopTower.setVisible(false);
        shopGoldMine.setVisible(false);
        shopUpdateGoldMine.setVisible(false);
        shopUpdateTower.setVisible(false);
        clearMask();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        //splitSprite.dispose();
        //splitSpriteEnemy.dispose();
        sliderLineEnemy.dispose();
        if(redSquare != null) redSquare.dispose();
        shopUpdateGoldMine.dispose();
        shopGoldMine.dispose();
        shopUpdateTower.dispose();
        shopUpdateTower.dispose();
        for (int i = 0; i < mapField.length; i++) {
            for (int k = 0; k < mapField[0].length; k++) {
                mapField[i][k].dispose();
            }
        }

        for (int i = 0; i < maskOnMap.length; i++) {
            for (int k = 0; k < maskOnMap[0].length; k++) {
                if(maskOnMap[i][k] != null) maskOnMap[i][k].dispose();
            }
        }

        for (int i = 0; i < cellTower.length; i++) {
            for (int k = 0; k < cellTower[0].length; k++) {
                if(cellTower[i][k] != null) cellTower[i][k].dispose();
            }
        }

        for (int i = 0; i < cellGoldMine.length; i++) {
            for (int k = 0; k < cellGoldMine[0].length; k++) {
                if(cellGoldMine[i][k] != null) cellGoldMine[i][k].dispose();
            }
        }

        if(circleTower != null) circleTower.dispose();

        textTimerWaves.dispose();
        textNextLevel.dispose();
        textMoney.dispose();
        textLevel.dispose();
        textWave.dispose();

        blackSquare.dispose();
        music.dispose();

        buttonExit.dispose();
        buttonConfirm.dispose();
        buttonGenerateAgain.dispose();
        buttonPauseExit.dispose();
        buttonPauseReturn.dispose();

        arrow.dispose();

        guiTutorial.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if(!generateGame && !pause) {
            if(Gdx.input.isKeyPressed(Input.Keys.NUM_1)) speedGame = 1;
            if(Gdx.input.isKeyPressed(Input.Keys.NUM_2)) speedGame = 3;
            if(Gdx.input.isKeyPressed(Input.Keys.NUM_3)) speedGame = 5;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && !generateGame) {
            pause = !pause;
            if(pause) speedGame = 0;

            if(shopTower.getVisible() || shopUpdateTower.getVisible()) {
                shopTower.setVisible(false);
                shopUpdateTower.setVisible(false);
                shopGoldMine.setVisible(false);
                shopUpdateGoldMine.setVisible(false);
                return true;
            }

            //Save.save();
            //Gdx.app.exit();
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(generateGame) {
            buttonGenerateAgain.click();
            buttonConfirm.click();
            buttonExit.click();

            listDifficulty.click();
            return true;
        }

        if(pause) {
            buttonPauseReturn.click();
            buttonPauseExit.click();
            return true;
        }
        for (int i = 0; i < shopTower.getProduct().length; i++) {
            if(!shopTower.getVisible()) break;
            if(shopTower.getProduct()[i].click()) {
                return true;
            }
        }

        for (int i = 0; i < shopGoldMine.getProduct().length; i++) {
            if(!shopGoldMine.getVisible()) break;
            if(shopGoldMine.getProduct()[i].click()) {
                return true;
            }
        }

        for (int i = 0; i < shopUpdateTower.getProduct().length; i++) {
            if(!shopUpdateTower.getVisible()) break;
            if(shopUpdateTower.getProduct()[i].click()) {
                return true;
            }
        }

        for (int i = 0; i < shopUpdateGoldMine.getProduct().length; i++) {
            if(!shopUpdateGoldMine.getVisible()) break;
            if(shopUpdateGoldMine.getProduct()[i].click()) {
                return true;
            }
        }

        for (int i = 0; i < mapField.length; i++) {
            for (int k = 0; k < mapField[i].length; k++) {
                if(mapField[i][k].click(screenX + (camera.position.x - Gdx.graphics.getWidth() / 2f), screenY - (camera.position.y - Gdx.graphics.getHeight() / 2f))) {
                    maskOnMap[redSquarePos[0]][redSquarePos[1]].dispose();
                    maskOnMap[redSquarePos[0]][redSquarePos[1]] = nullTexture();
                    if(redSquare != null) redSquare.dispose();
                    redSquare = redSquare();
                    maskOnMap[i][k] = redSquare;
                    redSquarePos = new int[]{i, k};
                }

                if(cellGoldMine[i][k] != null) {
                    if(k == selectSlotY && selectSlotX == i) {
                        shopTower.setVisible(false);
                        shopUpdateTower.setVisible(false);
                        shopUpdateGoldMine.setVisible(true);
                        shopGoldMine.setVisible(false);
                    }
                }

                if(cellTower[i][k] != null) {
                    if(cellTower[i][k].click(screenX + (camera.position.x - Gdx.graphics.getWidth() / 2f), screenY - (camera.position.y - Gdx.graphics.getHeight() / 2f))) {
                        shopTower.setVisible(false);
                        shopUpdateTower.setVisible(true);
                        shopUpdateGoldMine.setVisible(false);
                        shopGoldMine.setVisible(false);

                        for (Tower tower : gameWorld.getTower()) {
                            if(tower.getPos().x == i && tower.getPos().y == k) {
                                updateCircleTower(tower);
                                break;
                            }
                        }

                    }
                }

            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        /*final int ZOOM = 3;
        float checkZoomBorder = zoom;
        checkZoomBorder += ZOOM * -amountY;
        System.out.println(checkZoomBorder + " < " + defaultScale * 2f);
        if(checkZoomBorder > (defaultScale) * 2f || checkZoomBorder < (defaultScale - map[0].length) / 1.5f - (Gdx.graphics.getWidth() - Gdx.graphics.getHeight()) / 20f) return false;

        zoom += ZOOM * -amountY;
        //camera.position.add(-ZOOM * 50, -ZOOM * 50, 0f);
        zoomMove = true;
        //camera.position.set(new Vector2(camera.position.x + ZOOM * 5 * -amountY, camera.position.y + ZOOM * 5 * -amountY), 0);
*/
        return false;
    }

    private static Texture nullTexture() {
       return new Texture("sprites\\button\\nullSprite.png");
    }

    private void clearMask() {
        for (int i = 0; i < mapField.length; i++) {
            for (int k = 0; k < mapField[i].length; k++) {
                if(maskOnMap[i][k] == null) continue;
                maskOnMap[i][k].dispose();
                maskOnMap[i][k] = nullTexture();
            }
        }
    }

    private Texture redSquare() {
        Pixmap pixmap = new Pixmap((int) scale, (int) scale, Pixmap.Format.RGBA4444);
        pixmap.setColor(Color.RED);
        final int COUNT_SQUARE = 3;
        for (int i = 0; i < COUNT_SQUARE; i++) {
            pixmap.drawRectangle(i, i, pixmap.getWidth() - COUNT_SQUARE, pixmap.getHeight() - COUNT_SQUARE);
        }
        redSquare = new Texture(pixmap);
        pixmap.dispose();

        return redSquare;
    }

    private Texture blackSquare() {
        Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA4444);
        pixmap.setColor(0, 0, 0, 0.5f);
        pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
        redSquare = new Texture(pixmap);
        pixmap.dispose();

        return redSquare;
    }

    private void drawOverButton() {
        if(pause) return;
        for (int i = 0; i < mapField.length; i++) {
            for (int k = 0; k < mapField[i].length; k++) {
                if(cellTower[i][k] != null) {
                    cellTower[i][k].over(
                            Gdx.input.getX() + (camera.position.x - Gdx.graphics.getWidth() / 2f),
                            Gdx.input.getY() - (camera.position.y - Gdx.graphics.getHeight() / 2f));
                    continue;
                }
                if(cellGoldMine[i][k] != null) {
                    cellGoldMine[i][k].over(
                            Gdx.input.getX() + (camera.position.x - Gdx.graphics.getWidth() / 2f),
                            Gdx.input.getY() - (camera.position.y - Gdx.graphics.getHeight() / 2f));
                    continue;
                }

                mapField[i][k].over(
                        Gdx.input.getX() + (camera.position.x - Gdx.graphics.getWidth() / 2f),
                        Gdx.input.getY() - (camera.position.y - Gdx.graphics.getHeight() / 2f));
            }
        }
    }

    private void generateMap() {
        int widthMap = 0;
        int heightMap = 0;
        float countRock = 0;
        float countGold = 0;
        if(generateGame) {
            switch (difficulty) {
                case EASY:
                    widthMap = 24;
                    heightMap = 20;
                    countRock = 0.45f;
                    countGold = 0.8f;
                    break;

                case NORMAL:
                    widthMap = 20;
                    heightMap = 18;
                    countRock = 0.8f;
                    countGold = 0.5f;
                    break;

                case HARD:
                    widthMap = 16;
                    heightMap = 16;
                    countRock = 1.2f;
                    countGold = 0.3f;
                    break;
            }
        }
        map = Map.generate(widthMap, heightMap, countRock, countGold);
        mapField = new GameButton[map.length][map[0].length];
        tiledMap = new TextureRegion[map.length][map[0].length];
        maskOnMap = new Texture[map.length][map[0].length];
        cellTower = new GameButton[map.length][map[0].length];
        cellGoldMine = new GameButton[map.length][map[0].length];
        pointForMoveEnemy = new int[map.length][map[0].length];

        for (int i = 0; i < mapField.length; i++) {
            for (int k = 0; k < mapField[i].length; k++) {
                mapField[i][k] = new GameButton();
                maskOnMap[i][k] = nullTexture();
                ClickEvent clickEvent = null;
                TextureRegion textureRegion = null;
                boolean isClickField = false;
                final int finalI = i;
                final int finalK = k;

                switch (map[i][k]) {
                    case 0:
                        isClickField = true;
                        textureRegion = splitSprite.getTexture(1, 8);

                        clickEvent = new ClickEvent() {
                            @Override
                            public void click() {
                                isPlayerBuyingTower = true;
                                shopGoldMine.setVisible(false);
                                shopUpdateGoldMine.setVisible(false);
                                shopUpdateTower.setVisible(false);
                                shopTower.setVisible(true);

                                selectSlotX = finalI;
                                selectSlotY = finalK;
                            }
                        };
                        break;
                    case 1:
                        textureRegion = splitSprite.getTexture(1, 1);
                        break;
                    case 2:
                        textureRegion = splitSprite.getTexture(1, 3);
                        break;
                    case 3:
                        pointForMoveEnemy[i][k] = 1;
                        textureRegion = splitSprite.getTexture(1, 6);
                        break;
                    case 4:
                        pointForMoveEnemy[i][k] = 1;
                        textureRegion = splitSprite.getTexture(1, 2);
                        break;
                    case 5:
                        pointForMoveEnemy[i][k] = 1;
                        textureRegion = splitSprite.getTexture(1, 4);
                        break;
                    case 6:
                        pointForMoveEnemy[i][k] = 1;
                        textureRegion = splitSprite.getTexture(1, 5);
                        break;
                    case 7:
                        isClickField = true;
                        textureRegion = splitSprite.getTexture(1, 9);

                        clickEvent = new ClickEvent() {
                            @Override
                            public void click() {
                                selectSlotX = finalI;
                                selectSlotY = finalK;

                                shopGoldMine.setVisible(true);
                                shopUpdateTower.setVisible(false);
                                shopUpdateGoldMine.setVisible(false);
                                shopTower.setVisible(false);
                                System.out.println(shopGoldMine.getVisible());
                            }
                        };
                        break;
                    case 8:
                        textureRegion = splitSprite.getTexture(1, 10);
                        break;
                    case 9:
                        pointForMoveEnemy[i][k] = 1;
                        textureRegion = splitSprite.getTexture(1, 7);
                        break;
                }

                tiledMap[i][k] = textureRegion;
                mapField[i][k].createButton(nullTexture(), clickEvent);
                if(isClickField) mapField[i][k].setTextureOver(new Texture("sprites\\button\\selectZone.png"));
            }
        }
    }

    private void drawMap(SpriteBatch batch) {
        for (int i = 0; i < mapField.length; i++) {
            for (int k = 0; k < mapField[i].length; k++) {
                batch.draw(tiledMap[i][k], scale * k, scale * i,
                        scale, scale);

                mapField[i][k].draw(batch, scale * k, scale * i,
                        scale, scale);

                if(cellTower[i][k] != null) cellTower[i][k].draw(batch, scale * k, scale * i,
                        scale, scale);

                batch.draw(maskOnMap[i][k], scale * k, scale * i,
                        scale, scale);
            }
        }
    }

    private void generateWave() {
        final int countWave = (int)(amountWave / 2.5f) + 1;
        List<TypeEnemy> typeEnemies = new ArrayList<>();
        typeEnemies.add(TypeEnemy.ENEMY1);
        if(amountWave > 3) typeEnemies.add(TypeEnemy.ENEMY2);
        if(amountWave > 6) typeEnemies.add(TypeEnemy.ENEMY3);
        if(amountWave > 9) typeEnemies.add(TypeEnemy.ENEMY4);
        int k = 0;
        for (int i = 0; i < countWave; i++) {
            wave.addWay(new Way(typeEnemies.get(k++), (int)(Math.random() * countWave) + 5));
            if(k == typeEnemies.size()) k = 0;
            System.out.println(k + " = " + typeEnemies.size());
        }
        wave.spawnWay();
        if(sliderLineEnemy != null) {
            sliderLineEnemy.setCount(0);
            sliderLineEnemy.setMaxCount(wave.getTotalEnemies());
        }
    }

    public static Tower getTowerOnSquare() {
        for (int i = 0; i < gameWorld.getTower().size(); i++) {
            Tower tower = gameWorld.getTower().get(i);
            if (tower.getPos().x == selectSlotX && tower.getPos().y == selectSlotY) return tower;
        }
        return null;
    }

    public static GoldMine getGoldMineOnSquare() {
        for (int i = 0; i < gameWorld.getGoldMine().size(); i++) {
            GoldMine goldMine = gameWorld.getGoldMine().get(i);
            if (goldMine.getPos().x == selectSlotX && goldMine.getPos().y == selectSlotY) return goldMine;
        }
        return null;
    }
    private void showArrow() {
        showTextureArrow = true;

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                showTextureArrow = false;
            }
        }, 3);
    }

    public static void updateCircleTower(Tower tower) {
        if(circleTower != null) circleTower.dispose();

        Pixmap pixmap = new Pixmap((int)tower.getDistantion(), (int)tower.getDistantion(), Pixmap.Format.RGBA4444);
        pixmap.setColor(Color.BLUE);
        for (int i = 0; i < 2; i++) {
            pixmap.drawCircle(pixmap.getWidth() / 2, pixmap.getHeight() / 2, (int)tower.getDistantion() / 2 - i);
        }
        circleTower = new Texture(pixmap);
        pixmap.dispose();
    }

    public static int getSelectSlotX() {
        return selectSlotX;
    }
    public static boolean getPause() {
        return pause;
    }
    public static Slider getSliderLineEnemy() {
        return sliderLineEnemy;
    }

    public static int getSelectSlotY() {
        return selectSlotY;
    }
    public static int getAmountWave() {
        return amountWave;
    }
    public static void addAmountWave(int value) { amountWave += value;}
    public static ShopUpdateGoldMine getShopUpdateGoldMine() { return shopUpdateGoldMine;}

    public static GameButton[][] getCellTower() {
        return cellTower;
    }

    public static GameButton[][] getCellGoldMine() {
        return cellGoldMine;
    }

    private void clearEnemyInGameWorld() {
        gameWorld.getEnemy().clear();
    } // @TODO use
    public static boolean isPlayerBuyingTower() {
        return isPlayerBuyingTower;
    }
    public static Message getMessage() {
        return message;
    }

    public static void showMessage(String text) {
        message.show(text);
    }

    public static float getScale() {
        return scale;
    }
    public static SplitSprite getSplitSpriteEnemy() {
        return splitSpriteEnemy;
    }

    public static SplitSprite getSplitSpriteMap() {
        return splitSprite;
    }
    public static int[][] getPointForMoveEnemy() {
        return pointForMoveEnemy;
    }
    public static Base getBase() {
        return base;
    }
    public static GameWorld getGameWorld() {
        return gameWorld;
    }
    public static int[][] getMap() {
        return map;
    }

    public static double distantion(Vector2 ObjOne, Vector2 ObjTwo) {
        return Math.sqrt(Math.pow((ObjOne.x - ObjTwo.x), 2) + Math.pow((ObjOne.y - ObjTwo.y), 2));
    }

    public static void setTower(Tower tower) {
        tower.setPos(selectSlotX, selectSlotY);
        gameWorld.getTower().add(tower);
        tiledMap[selectSlotX][selectSlotY] = tower.getTexture();
        cellTower[selectSlotX][selectSlotY] = new GameButton();
        cellTower[selectSlotX][selectSlotY].createButton(nullTexture(), new ClickEvent() {
            @Override
            public void click() {
                System.out.println("i'm tower");
            }
        });
        cellTower[selectSlotX][selectSlotY].setTextureOver(new Texture("sprites\\button\\selectZone.png"));

        shopTower.setVisible(false);
    }

    public static void setGoldMine(GoldMine goldMine) {
        goldMine.setPos(selectSlotX, selectSlotY);
        gameWorld.getGoldMine().add(goldMine);
        tiledMap[selectSlotX][selectSlotY] = goldMine.getTexture();
        cellGoldMine[selectSlotX][selectSlotY] = new GameButton();
        cellGoldMine[selectSlotX][selectSlotY].createButton(nullTexture(), new ClickEvent() {
            @Override
            public void click() {
                System.out.println("i'm goldMine");
            }
        });
        cellGoldMine[selectSlotX][selectSlotY].setTextureOver(new Texture("sprites\\button\\selectZone.png"));

        //shopTower.setVisible(false);
    }
    public static void deleteTower() {
        for (int i = 0; i < gameWorld.getTower().size(); i++) {
            if(gameWorld.getTower().get(i).getPos().x == selectSlotX &&
                    gameWorld.getTower().get(i).getPos().y == selectSlotY) {
                tiledMap[selectSlotX][selectSlotY] = splitSprite.getTexture(1, 8);
                cellTower[selectSlotX][selectSlotY] = null;
                gameWorld.getTower().remove(i);
                shopUpdateTower.setVisible(false);
            }
        }
    }

    public static void updateTower() {
        for (int i = 0; i < gameWorld.getTower().size(); i++) {
            Tower tower = gameWorld.getTower().get(i);
            if(tower.getPos().x == selectSlotX && tower.getPos().y == selectSlotY) {
                if(tower.getMaxLevelUpdate() == tower.getLevelUpdate()) return;
                tower.addDamage(5);
                tower.addDistantion(50);
                tower.removeTimeCouldown(0.35f);
                tower.addLevelUpdate();
                updateCircleTower(tower);
            }
        }
    }

    public static void deleteGoldMine() {
        for (int i = 0; i < gameWorld.getGoldMine().size(); i++) {
            if(gameWorld.getGoldMine().get(i).getPos().x == selectSlotX &&
                    gameWorld.getGoldMine().get(i).getPos().y == selectSlotY) {
                gameWorld.getGoldMine().remove(i);
                tiledMap[selectSlotX][selectSlotY] = splitSprite.getTexture(1, 9);
                cellGoldMine[selectSlotX][selectSlotY] = null;
            }
        }
    }

    public static void updateGoldMine() {
        for (int i = 0; i < gameWorld.getGoldMine().size(); i++) {
            GoldMine goldMine = gameWorld.getGoldMine().get(i);
            if(goldMine.getPos().x == selectSlotX && goldMine.getPos().y == selectSlotY) {
                if(goldMine.getMaxLevelUpdate() == goldMine.getLevelUpdate()) return;
                goldMine.addMineMoney(10);
                goldMine.removeSpeed(0.5f);
                goldMine.addLevelUpdate();
            }
        }
    }

}