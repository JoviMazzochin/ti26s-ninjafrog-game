package com.utfpr.ti16s.ninjafroggame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.utfpr.ti16s.ninjafroggame.NinjaFrogGame;
import com.utfpr.ti16s.ninjafroggame.Scenes.Hud;

public class PlayScreen implements Screen {
    private NinjaFrogGame game; // Reference to Game, user to set Screens

    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthoCachedTiledMapRenderer renderer;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    public PlayScreen(NinjaFrogGame game) {
        this.game = game;
        gameCam = new OrthographicCamera(); // Create cam used to follow NinjaFrog
        gamePort = new FitViewport(NinjaFrogGame.V_WIDTH, NinjaFrogGame.V_HEIGHT, gameCam); // Maintain virtual aspact ratio
        hud = new Hud(game.batch); // create hame HUD for scores / level / timers info

        // Importing map from Tiled
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("GameMap.tmx");
        renderer = new OrthoCachedTiledMapRenderer(map);

        //setting gamecam
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0,0), true);
        box2DDebugRenderer = new Box2DDebugRenderer();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fDef = new FixtureDef();
        Body body;

        //creating ground bodies/fixtures
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
            fDef.shape = shape;
            body.createFixture(fDef);
        }

        // creating trap bodies/fixtures
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
            fDef.shape = shape;
            body.createFixture(fDef);
        }
    }

    public void handleInput(float dt) {
        if(Gdx.input.isTouched()) gameCam.position.x += 100 * dt;
    }

    public void update(float dt) {
        handleInput(dt);

        gameCam.update();
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

    }
}
