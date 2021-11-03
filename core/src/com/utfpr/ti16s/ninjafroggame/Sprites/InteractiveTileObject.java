package com.utfpr.ti16s.ninjafroggame.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.utfpr.ti16s.ninjafroggame.NinjaFrogGame;

public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;

    public InteractiveTileObject(World world, TiledMap tiledMap, Rectangle bounds) {
        this.world = world;
        this.map = map;
        this.bounds = bounds;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / NinjaFrogGame.PPM,
                          (bounds.getY() + bounds.getHeight() / 2) / NinjaFrogGame.PPM);

        body = world.createBody(bdef);

        shape.setAsBox((bounds.getWidth() / 2) / NinjaFrogGame.PPM,
                       (bounds.getHeight() / 2) / NinjaFrogGame.PPM);
        fdef.shape = shape;
        body.createFixture(fdef);
    }
}
