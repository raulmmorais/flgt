package com.tutorial.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class B2dContactListener implements ContactListener {
    private B2DModel parent;

    public B2dContactListener(B2DModel parent) {
        this.parent = parent;
    }

    @Override
    public void beginContact(Contact contact) {
        Gdx.app.log("contact", "contact");
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        Gdx.app.log("contact", fa.getBody().getType() + "has hit" + fb.getBody().getType());

        if(fa.getBody().getUserData() == "IAMTHESEA"){
            parent.isSwimming = true;
            return;
        }else if(fb.getBody().getUserData() == "IAMTHESEA"){
            parent.isSwimming = true;
            return;
        }

        if (fa.getBody().getType() == BodyDef.BodyType.StaticBody){
            this.shootUpInTheAir(fa, fb);
        }else if (fb.getBody().getType() == BodyDef.BodyType.StaticBody){
            this.shootUpInTheAir(fb, fa);
        }else {
            //nothing to do
        }
    }

    private void shootUpInTheAir(Fixture staticFixture, Fixture otherFixture){
        Gdx.app.log("contact", "Adding Force");
        otherFixture.getBody().applyForceToCenter(new Vector2(-1000, -1000), true);
        parent.playSound(B2DModel.BOING_SOUND);
    }

    @Override
    public void endContact(Contact contact) {
        Gdx.app.log("contact", "endContact");
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if(fa.getBody().getUserData() == "IAMTHESEA"){
            parent.isSwimming = false;
            return;
        }else if(fb.getBody().getUserData() == "IAMTHESEA"){
            parent.isSwimming = false;
            return;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}