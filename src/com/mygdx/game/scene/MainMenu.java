package com.mygdx.game.scene;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.api.*;
import com.mygdx.game.game_elements.InfoAboutPlayer;
import com.mygdx.game.game_elements.Language;
import com.mygdx.game.game_elements.Save;
import com.mygdx.game.game_elements.gui.GuiSkills;
import com.mygdx.game.game_elements.music.ListMusic;
import com.mygdx.game.game_elements.music.TypeMusic;
import com.mygdx.game.game_elements.sound.ListSound;
import com.mygdx.game.game_elements.sound.TypeSound;

public class MainMenu implements Screen, InputProcessor {

    private final SpriteBatch batch = new SpriteBatch();
    private OrthographicCamera camera;
    private GameButton buttonVK;

    private final Game game;
    public MainMenu(Game game) {
        this.game = game;
    }

    private GameButton buttonNewGame;
    private GameButton buttonSkills;
    private GameButton buttonExit;
    private SelectList listSelectLanguages;
    private GUI guiSkills;
    private Music music;
    private boolean musicIntroduction;
    private static boolean newGame;
    private TextGame text20Size;
    private boolean selectLanguage = false;

    @Override
    public void show() {
        music = ListMusic.getMusic(TypeMusic.MENU_MUSIC);
        buttonVK = new GameButton();
        buttonVK.createButton(new Texture("sprites\\button\\vk.png") , new ClickEvent() {
            @Override
            public void click() {
                Gdx.net.openURI("https://vk.com/bublagames");
            }
        });
        buttonVK.setTextureOver(new Texture("sprites\\button\\vkDown.png"));
        buttonVK.setSoundOver(ListSound.getSound(TypeSound.OVER_BUTTON));

        buttonNewGame = new GameButton();
        buttonNewGame.createButton(new Texture("sprites\\button\\buttonPause.png"), new ClickEvent() {
            @Override
            public void click() {
                newGame = true;
                music.stop();
                dispose();
                game.setScreen(new GameScene(game));
            }
        }, new TextGame(18, Language.getText("newGame")));
        buttonNewGame.setTextureOver(new Texture("sprites\\button\\buttonPauseOver.png"));
        buttonNewGame.setSoundOver(ListSound.getSound(TypeSound.OVER_BUTTON));

        buttonSkills = new GameButton();
        buttonSkills.createButton(new Texture("sprites\\button\\buttonPause.png"), new ClickEvent() {
            @Override
            public void click() {
                guiSkills.setVisible(true);
            }
        }, new TextGame(20, Language.getText("skills")));
        buttonSkills.setTextureOver(new Texture("sprites\\button\\buttonPauseOver.png"));
        buttonSkills.setSoundOver(ListSound.getSound(TypeSound.OVER_BUTTON));

        buttonExit = new GameButton();
        buttonExit.createButton(new Texture("sprites\\button\\buttonPause.png"), new ClickEvent() {
            @Override
            public void click() {
                Save.save();
                Gdx.app.exit();
            }
        }, new TextGame(20, Language.getText("exit")));
        buttonExit.setTextureOver(new Texture("sprites\\button\\buttonPauseOver.png"));
        buttonExit.setSoundOver(ListSound.getSound(TypeSound.OVER_BUTTON));

        guiSkills = new GuiSkills();

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(new Vector3(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0));
        camera.update();

        Gdx.input.setInputProcessor(this);

        text20Size = new TextGame(20);


        Texture textureButton = new Texture("sprites\\button\\button.png");
        Texture textureButtonOver = new Texture("sprites\\button\\buttonOver.png");

        GameButton buttonListNormal = new GameButton();
        buttonListNormal.createButton(textureButton, new ClickEvent() {
            @Override
            public void click() {
                InfoAboutPlayer.setLanguage("en");
                selectLanguage = true;
            }
        }, new TextGame(18, Language.getText("en")));
        buttonListNormal.setTextureOver(textureButtonOver);
        buttonListNormal.setSoundOver(ListSound.getSound(TypeSound.OVER_BUTTON));

        GameButton buttonListHard = new GameButton();
        buttonListHard.createButton(textureButton, new ClickEvent() {
            @Override
            public void click() {
                InfoAboutPlayer.setLanguage("ru");
                selectLanguage = true;
            }
        }, new TextGame(20, Language.getText("ru")));
        buttonListHard.setTextureOver(textureButtonOver);
        buttonListHard.setSoundOver(ListSound.getSound(TypeSound.OVER_BUTTON));


        GameButton buttonList = new GameButton();
        buttonList.createButton(textureButton, null, new TextGame(16, Language.getText("language")));
        buttonList.setTextureOver(textureButtonOver);
        buttonList.setSoundOver(ListSound.getSound(TypeSound.OVER_BUTTON));

        listSelectLanguages = new SelectList(buttonList, new Texture("sprites\\button\\arrow.png"),
                buttonListNormal, buttonListHard);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.41f, 0.74f, 0.18f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        buttonVK.draw(batch, 10, 10, 50, 50);

        final float width = buttonNewGame.getTexture().getWidth() * 5.5f;
        final int height = buttonNewGame.getTexture().getHeight() * 4;
        int indent = 0;
        final float x = Gdx.graphics.getWidth() - width - 30;
        final int y = 400;
        if(!guiSkills.getVisible()) {
            buttonNewGame.draw(batch, x, y - (indent++ * height) - (indent * 10), width, height, 10, 47);
            buttonSkills.draw(batch, x, y - (indent++ * height) - (indent * 10), width, height, 10, 47);
            buttonExit.draw(batch, x, y - (indent++ * height) - (indent * 10), width, height, 10, 47);
        }

        text20Size.draw(batch, Language.getText("money") + ":_{YELLOW}" + InfoAboutPlayer.getMoneyGlobal(), 5, Gdx.graphics.getHeight() - 10);
        text20Size.draw(batch, Language.getText("level") + ":_{YELLOW}" + InfoAboutPlayer.getLevel(), 5, Gdx.graphics.getHeight() - 40);

        if(selectLanguage)
            text20Size.draw(batch, "{RED}" + Language.getText("notifyAboutReloadGame"), 230, Gdx.graphics.getHeight() - 175);

        guiSkills.draw(batch, Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 3.8f, 0);

        listSelectLanguages.draw(batch, 0, Gdx.graphics.getHeight() - 200, 200, 50);

        batch.end();


        batch.setProjectionMatrix(camera.combined);
        camera.update();

        overButton();

        if(!music.isPlaying()) {
            music.play();
            music.setVolume(0.5f);
            if(!musicIntroduction) {
                musicIntroduction = true;
                music.setVolume(0);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        music.setVolume(music.getVolume() + 0.5f / 100f);
                    }
                }, 0.1f, 0.1f, 100);
            }
        }
    }

    private void overButton() {
        buttonVK.over();
        guiSkills.over();
        listSelectLanguages.over();

        if(!guiSkills.getVisible()) {
            buttonNewGame.over();
            buttonSkills.over();
            buttonExit.over();
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportHeight = height;
        camera.viewportWidth = width;
        camera.position.set(new Vector3(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0));
        camera.update();
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

        buttonSkills.dispose();
        buttonExit.dispose();
        buttonNewGame.dispose();
        buttonVK.dispose();

        listSelectLanguages.dispose();

        guiSkills.dispose();

        music.dispose();

        text20Size.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            guiSkills.setVisible(false);
        }
        return false;
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
        guiSkills.click();
        listSelectLanguages.click();

        if(!guiSkills.getVisible()) {
            buttonVK.click();
            buttonNewGame.click();
            buttonExit.click();
            buttonSkills.click();
        }

        return true;
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
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public static boolean playerStartNewGame() {
        return newGame;
    }
}
