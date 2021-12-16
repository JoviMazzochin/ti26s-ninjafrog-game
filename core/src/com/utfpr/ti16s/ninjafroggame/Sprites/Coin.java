package com.utfpr.ti16s.ninjafroggame.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.utfpr.ti16s.ninjafroggame.NinjaFrogGame;
import com.utfpr.ti16s.ninjafroggame.Scenes.Hud;


public class Coin extends InteractiveTileObject{
    public Coin(World world, TiledMap map, Rectangle bounds){
        super(world, map, bounds);
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
