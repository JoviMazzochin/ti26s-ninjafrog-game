package com.utfpr.ti16s.ninjafroggame.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.utfpr.ti16s.ninjafroggame.NinjaFrogGame;
import com.utfpr.ti16s.ninjafroggame.Scenes.Hud;


public class Coin extends InteractiveTileObject{
//        private static TiledMapTileSet tileSet;
//        private final int COLLECTED_COIN = 1;

    public Coin(World world, TiledMap map, Rectangle bounds){
        super(world, map, bounds);
//        tileSet = map.getTileSets().getTileSet("collected");
        fixture.setUserData(this);
        setCategoryFilter(NinjaFrogGame.COIN_BIT);
    }

    @Override
    public void onHit() {
        Gdx.app.log("Coin", "Collision");
        setCategoryFilter(NinjaFrogGame.DESTROYED_BIT);
        getCell().setTile(null);
        Hud.addScore(200);
    }
}
