package com.utfpr.ti16s.ninjafroggame.Sprites;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.utfpr.ti16s.ninjafroggame.NinjaFrogGame;
import com.utfpr.ti16s.ninjafroggame.Screens.PlayScreen;

public class NinjaFrog extends Sprite {
    public enum State {FALLING, JUMPING, STANDING, RUNNING};
    public State currentState;
    public State previousState;

    public World world;
    public Body b2body;
    private TextureRegion ninjaFrogStand;
    private Animation ninjaFrogRun;
    private Animation ninjaFrogJump;
    private Animation ninjaFrogFalling;
    private Animation ninjaFrogStanding;
    private float stateTimer;
    private boolean runningRight;

//    private Sound sound;


    public NinjaFrog(World world, PlayScreen screen) {
        super(screen.getAtlas().findRegion("Run (32x32)"));
        this. world = world;

        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 1; i < 12; i++) // running animation
            frames.add(new TextureRegion(getTexture(), i * 32, 0, 32, 32));
        ninjaFrogRun = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 12; i < 23; i++) // standing animation
            frames.add(new TextureRegion(getTexture(), i * 32, 0, 32, 32));
        ninjaFrogStanding = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 23; i < 24; i++) // jumping animation
            frames.add(new TextureRegion(getTexture(), i * 32, 0, 32, 32));
        ninjaFrogJump = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 8; i < 9; i++) // falling animation
            frames.add(new TextureRegion(getTexture(), i * 32, 0, 32, 32));
        ninjaFrogFalling = new Animation(0.1f, frames);
        frames.clear();

        defineNinjaFrog();
        ninjaFrogStand = new TextureRegion(getTexture(), 0, 0 , 32, 32);
        setBounds(0, 0, 32 / NinjaFrogGame.PPM, 32 / NinjaFrogGame.PPM);
        setRegion(ninjaFrogStand);
    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    private TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region;
        switch (currentState) {
            case JUMPING:
//                sound = NinjaFrogGame.manager.get("audio/sounds/jump.wav", Sound.class);
//                sound.play();
                region = (TextureRegion) ninjaFrogJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = (TextureRegion) ninjaFrogRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
                region = (TextureRegion) ninjaFrogFalling.getKeyFrame(stateTimer);
                break;
            case STANDING:
            default:
                region = (TextureRegion) ninjaFrogStanding.getKeyFrame(stateTimer, true);
                break;
        }
        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    private State getState() {
        if(b2body.getLinearVelocity().y > 0)
            return State.JUMPING;
        else if(b2body.getLinearVelocity().y < 0 && previousState != State.RUNNING)
            return State.FALLING;
        else if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }

    public void defineNinjaFrog() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(50 / NinjaFrogGame.PPM, 50 / NinjaFrogGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / NinjaFrogGame.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
