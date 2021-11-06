package com.utfpr.ti16s.ninjafroggame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.utfpr.ti16s.ninjafroggame.Screens.LoadingScreen;

public class NinjaFrogGame extends Game {
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 320;
	public static final float PPM = 100;

	public SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private Viewport viewport;

	private static final AssetManager assetManager = new AssetManager();
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		viewport = new StretchViewport(V_WIDTH, V_HEIGHT);
		shapeRenderer = new ShapeRenderer();
		setScreen(new LoadingScreen(this));
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		batch.setProjectionMatrix(viewport.getCamera().combined);
		shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
	}

	@Override
	public void render() {
		clearScreen();
		super.render();
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public ShapeRenderer getShapeRenderer() {
		return shapeRenderer;
	}

	public static AssetManager getAssetManager() {
		return assetManager;
	}

	private void clearScreen() {
		Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
}
