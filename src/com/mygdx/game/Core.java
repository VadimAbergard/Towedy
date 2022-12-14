package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.api.TextGame;
import com.mygdx.game.game_elements.InfoAboutPlayer;
import com.mygdx.game.scene.*;


public final class Core extends Game {

	private TextGame cordinat;
	private SpriteBatch batch;
	private Texture mouse;
	private boolean debug = false;

	@Override
	public void create () {
		try {
			InfoAboutPlayer.loadOptions();
		} catch (NullPointerException ignored) {}

		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		Gdx.input.setCursorCatched(true);
		batch = new SpriteBatch();
		cordinat = new TextGame(10);
		mouse = new Texture("sprites\\elements\\mouse.png");

		this.setScreen(new Title(this));
	}

	@Override
	public void render () {
		super.render();
		batch.begin();
		if(debug) cordinat.draw(batch, Gdx.input.getX() + ", " + (Gdx.graphics.getHeight() - Gdx.input.getY()), Gdx.input.getX() - 20, Gdx.graphics.getHeight() - Gdx.input.getY() + 20);
		if(Gdx.input.getX() >= Gdx.graphics.getWidth()) Gdx.input.setCursorPosition(Gdx.graphics.getWidth(), Gdx.input.getY());
		if(Gdx.input.getX() <= 0) Gdx.input.setCursorPosition(0, Gdx.input.getY());
		if(Gdx.input.getY() >= Gdx.graphics.getHeight()) Gdx.input.setCursorPosition(Gdx.input.getX(), Gdx.graphics.getHeight());
		if(Gdx.input.getY() <= 0) Gdx.input.setCursorPosition(Gdx.input.getX(), 0);
		batch.draw(mouse, Gdx.input.getX() - 15, Gdx.graphics.getHeight() - Gdx.input.getY() - 15, 32, 32);
		batch.end();
	}
	
	@Override
	public void dispose () {
		super.dispose();
	}
}
