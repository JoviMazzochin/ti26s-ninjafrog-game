package com.utfpr.ti16s.ninjafroggame.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.utfpr.ti16s.ninjafroggame.NinjaFrogGame;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    private boolean timeUp;
    private Integer worldTimer;
    private float timeCount;
    private static Integer score;

    Label countDownLabel;
    static Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label ninjaFrogLabel;

    public Hud(SpriteBatch sb) {
        worldTimer = 300;
        timeCount = 0;
        score = 0;

        viewport = new FitViewport(NinjaFrogGame.V_WIDTH, NinjaFrogGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top(); // fix on top of the screen
        table.setFillParent(true); //size of the stage

        countDownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        ninjaFrogLabel = new Label("NINJA FROG", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(ninjaFrogLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countDownLabel).expandX();

        stage.addActor(table);
    }

    public void update(float dt){
        timeCount += dt;
        if(timeCount >= 1){
            if (worldTimer > 0){
                worldTimer--;
            } else {
                timeUp = true;
            }
            countDownLabel.setText(String.format("%03d", worldTimer));
            timeCount--;
        }

    }

    public static void addScore(int value) {
        score += value;
        scoreLabel.setText(String.format("%6d", score));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public boolean isTimeUp() { return timeUp; }

    public void stopTimer() {
        worldTimer = 0;
        countDownLabel.setText(String.format("%03d", worldTimer));
    }

    public int getScore() {
        return score;
    }
}
