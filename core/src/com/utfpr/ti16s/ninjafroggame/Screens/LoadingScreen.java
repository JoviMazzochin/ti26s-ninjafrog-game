package com.utfpr.ti16s.ninjafroggame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.utfpr.ti16s.ninjafroggame.NinjaFrogGame;

public class LoadingScreen extends ScreenAdapter {

    private static final float PROGRESS_BAR_WIDTH = NinjaFrogGame.V_WIDTH;
    private static final float PROGRESS_BAR_HEIGHT = 25f;

    private final NinjaFrogGame game;
    private final AssetManager assetManager;
    private final ShapeRenderer shapeRenderer;

    public LoadingScreen(NinjaFrogGame game) {
        this.game = game;
        this.assetManager = game.getAssetManager();
        this.shapeRenderer = game.getShapeRenderer();
    }

    @Override
    public void show() {
        super.show();
        assetManager.load("audio/musics/music2.mp3", Music.class);
        assetManager.load("audio/sounds/jump.wav", Sound.class);
        assetManager.load("audio/sounds/coin.wav", Sound.class);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        renderProgressBar();

        if (assetManager.update()) {
            // Comment this out if you just want to see the progress bar. As this can be quite quick on desktop.
            game.setScreen(new PlayScreen(game));
        }
    }

    private void renderProgressBar() {
        float progress = assetManager.getProgress();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(
                (NinjaFrogGame.V_WIDTH / 6f),
                (NinjaFrogGame.V_HEIGHT - PROGRESS_BAR_HEIGHT)/2,
                PROGRESS_BAR_WIDTH * progress,
                PROGRESS_BAR_HEIGHT
        );
        shapeRenderer.end();
    }
}
