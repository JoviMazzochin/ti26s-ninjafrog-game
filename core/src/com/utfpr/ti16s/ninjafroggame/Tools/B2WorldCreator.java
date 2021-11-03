package com.utfpr.ti16s.ninjafroggame.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.utfpr.ti16s.ninjafroggame.NinjaFrogGame;
import com.utfpr.ti16s.ninjafroggame.Sprites.Box;
import com.utfpr.ti16s.ninjafroggame.Sprites.Trap;

public class B2WorldCreator {
    public B2WorldCreator(World world, TiledMap map) {
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fDef = new FixtureDef();
        Body body;



        //creating ground bodies/fixtures
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / NinjaFrogGame.PPM, (rect.getY() + rect.getHeight() / 2) / NinjaFrogGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2) / NinjaFrogGame.PPM, (rect.getHeight() / 2) / NinjaFrogGame.PPM);
            fDef.shape = shape;
            body.createFixture(fDef);
        }

        // creating trap bodies/fixtures
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Trap(world, map, rect);
        }

        // creating box bodies/fixtures
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Box(world, map, rect);
        }
    }
}
