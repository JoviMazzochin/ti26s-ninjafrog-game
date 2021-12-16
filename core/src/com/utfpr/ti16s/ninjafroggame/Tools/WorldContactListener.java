package com.utfpr.ti16s.ninjafroggame.Tools;

import com.badlogic.gdx.physics.box2d.*;
import com.utfpr.ti16s.ninjafroggame.NinjaFrogGame;
import com.utfpr.ti16s.ninjafroggame.Sprites.InteractiveTileObject;
import com.utfpr.ti16s.ninjafroggame.Sprites.NinjaFrog;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){
            case NinjaFrogGame.NINJA_BIT | NinjaFrogGame.COIN_BIT:
                if(fixA.getFilterData().categoryBits == NinjaFrogGame.NINJA_BIT)
                    ((InteractiveTileObject) fixB.getUserData()).onHit();
                else
                    ((InteractiveTileObject) fixA.getUserData()).onHit();
                break;
            case NinjaFrogGame.NINJA_BIT | NinjaFrogGame.TRAP_BIT:
                if(fixA.getFilterData().categoryBits == NinjaFrogGame.NINJA_BIT)
                    ((NinjaFrog) fixA.getUserData()).dieOnHit();
                else
                    ((NinjaFrog) fixB.getUserData()).dieOnHit();
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
