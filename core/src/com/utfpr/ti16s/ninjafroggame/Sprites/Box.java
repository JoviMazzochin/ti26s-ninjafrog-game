package com.utfpr.ti16s.ninjafroggame.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class Box extends InteractiveTileObject{
    public Box(World world, TiledMap tiledMap, Rectangle bounds) {
        super(world, tiledMap, bounds);
    }
}
