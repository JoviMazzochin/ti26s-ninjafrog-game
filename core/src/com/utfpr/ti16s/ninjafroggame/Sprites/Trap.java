package com.utfpr.ti16s.ninjafroggame.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.utfpr.ti16s.ninjafroggame.NinjaFrogGame;

public class Trap extends InteractiveTileObject {
    public Trap(World world, TiledMap tiledMap, Rectangle bounds) {
        super(world, tiledMap, bounds);
        fixture.setUserData(this);
        setCategoryFilter(NinjaFrogGame.TRAP_BIT);
    }

    @Override
    public void onHit() {
        Gdx.app.log("Trap", "Collision");


    }
}
