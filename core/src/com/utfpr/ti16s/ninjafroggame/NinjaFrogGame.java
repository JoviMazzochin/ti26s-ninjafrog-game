package com.utfpr.ti16s.ninjafroggame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.utfpr.ti16s.ninjafroggame.Screens.PlayScreen;

public class NinjaFrogGame extends Game {
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 320;
	public static final float PPM = 100;

	public SpriteBatch batch;

	public static AssetManager manager;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();
		manager.load("audio/musics/music.wav", Music.class);
		manager.load("audio/sounds/jump.wav", Sound.class);
		manager.load("audio/sounds/coin.wav", Sound.class);
		manager.finishLoading();

		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();


	}
}
