package com.utfpr.ti16s.ninjafroggame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.utfpr.ti16s.ninjafroggame.NinjaFrogGame;
import com.utfpr.ti16s.ninjafroggame.Scenes.Hud;
import com.utfpr.ti16s.ninjafroggame.Sprites.NinjaFrog;
import com.utfpr.ti16s.ninjafroggame.Tools.B2WorldCreator;
import com.utfpr.ti16s.ninjafroggame.Tools.WorldContactListener;

public class PlayScreen implements Screen {
    private NinjaFrogGame game; // Reference to Game, user to set Screens
    private TextureAtlas atlas;

    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    private NinjaFrog player;

    private Music music;

    private float jumpDelta = 0;

    public PlayScreen(NinjaFrogGame game) {
        atlas = new TextureAtlas("ninjafrog.atlas");

        this.game = game;
        gameCam = new OrthographicCamera(); // Create cam used to follow NinjaFrog
        gamePort = new FitViewport(NinjaFrogGame.V_WIDTH / NinjaFrogGame.PPM, NinjaFrogGame.V_HEIGHT / NinjaFrogGame.PPM, gameCam); // Maintain virtual aspact ratio
        hud = new Hud(game.getBatch()); // create hame HUD for scores / level / timers info

        // Importing map from Tiled
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("GameMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / NinjaFrogGame.PPM);

        //setting gamecam
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        //start world
        world = new World(new Vector2(0,-9.91f), true);
        box2DDebugRenderer = new Box2DDebugRenderer();

        //generate the world
        new B2WorldCreator(world, map, renderer);

        //create Ninja in the game
        player = new NinjaFrog(world, this);

        world.setContactListener(new WorldContactListener());

//        music = NinjaFrogGame.manager.get("audio/musics/music1.mp3", Music.class);

//        music = NinjaFrogGame.getAssetManager().get("audio/musics/music2.mp3", Music.class);
//        music.setLooping(true);
//        music.setVolume(0.05f);
//        music.play();

    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public void handleInput(float dt) {
        if(Gdx.input.isKeyPressed(Input.Keys.UP))
            player.jump();
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
    }

    public void update(float dt) {
        handleInput(dt);

        world.step(1/60f, 6, 2);

        hud.update(dt);

        player.update(dt);

        gameCam.position.x = player.b2body.getPosition().x;

        //update gamecam with correct coordinates after changes
        gameCam.update();

        //tell renderer to draw only what our camera can see in our game world
        renderer.setView(gameCam);
    }



    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void render(float delta) {
        //separate update logic from render
        update(delta);

        //Clear screen
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        //renderer Box2DDegugLines
        box2DDebugRenderer.render(world, gameCam.combined);

        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
//        gameCam.update();
//        renderer.setView(gameCam);
        renderer.render();
        renderer.renderObjects(map.getLayers().get("coin"));
        renderer.renderObjects(map.getLayers().get("graphics"));
        player.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        if (getHud().getScore() == 5800) {
            game.setScreen(new WinScreen(game));
        }

        if(gameOver()){
            game.setScreen(new GameOverScreen(game));
            dispose();
        }
    }

    @Override
    public void show() {

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
        map.dispose();
        renderer.dispose();
        world.dispose();
        box2DDebugRenderer.dispose();
        hud.dispose();
    }

    public Hud getHud(){ return hud; }

    public void stopTimer() {
        getHud().stopTimer();
    }

    public boolean gameOver(){
        return player.currentState == NinjaFrog.State.DEAD;
    }
}
