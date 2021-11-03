package com.utfpr.ti16s.ninjafroggame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.utfpr.ti16s.ninjafroggame.NinjaFrogGame;
import com.utfpr.ti16s.ninjafroggame.Scenes.Hud;
import com.utfpr.ti16s.ninjafroggame.Sprites.NinjaFrog;
import com.utfpr.ti16s.ninjafroggame.Tools.B2WorldCreator;

public class PlayScreen implements Screen {
    private NinjaFrogGame game; // Reference to Game, user to set Screens
    private TextureAtlas atlas;

    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthoCachedTiledMapRenderer renderer;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    private NinjaFrog player;

    public PlayScreen(NinjaFrogGame game) {
        atlas = new TextureAtlas("ninjafrog.atlas");

        this.game = game;
        gameCam = new OrthographicCamera(); // Create cam used to follow NinjaFrog
        gamePort = new FitViewport(NinjaFrogGame.V_WIDTH / NinjaFrogGame.PPM, NinjaFrogGame.V_HEIGHT / NinjaFrogGame.PPM, gameCam); // Maintain virtual aspact ratio
        hud = new Hud(game.batch); // create hame HUD for scores / level / timers info

        // Importing map from Tiled
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("GameMap.tmx");
        renderer = new OrthoCachedTiledMapRenderer(map, 1 / NinjaFrogGame.PPM);

        //setting gamecam
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        //start world
        world = new World(new Vector2(0,-10), true);
        box2DDebugRenderer = new Box2DDebugRenderer();

        //generate the world
        new B2WorldCreator(world, map);

        //create Ninja in the game
        player = new NinjaFrog(world, this);

    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public void handleInput(float dt) {
        if(Gdx.input.isKeyPressed(Input.Keys.UP))
            player.b2body.applyLinearImpulse(new Vector2(0, 0.4f), player.b2body.getWorldCenter(), true);
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
    }

    public void update(float dt) {
        handleInput(dt);

        world.step(1/60f, 6, 2);

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
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render game map
        renderer.render();

        //renderer Box2DDegugLines
        box2DDebugRenderer.render(world, gameCam.combined);

        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
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
}
