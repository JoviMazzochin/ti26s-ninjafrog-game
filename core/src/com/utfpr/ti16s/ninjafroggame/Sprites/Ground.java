package com.utfpr.ti16s.ninjafroggame.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.utfpr.ti16s.ninjafroggame.NinjaFrogGame;

public class Ground extends InteractiveTileObject{
    public Ground(World world, TiledMap tiledMap, Rectangle bounds) {
        super(world, tiledMap, bounds);
    }

    @Override
    public void onHit() {

    }
}
