package com.tutorial.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.tutorial.game.controller.KeyboardController;
import com.tutorial.game.loader.B2dAssetManager;

public class B2DModel {
    public World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    private OrthographicCamera camera;
    private Body bodyd;
    private Body bodys;
    private Body bodyk;
    public boolean isSwimming = false;
    private KeyboardController controller;
    private B2dAssetManager assMan;
    private Sound ping;
    private Sound boing;
    public Body player;

    public final static int BOING_SOUND = 0;
    public final static int PING_SOUND = 1;

    public B2DModel(KeyboardController controller, OrthographicCamera cam, B2dAssetManager assetManager){
        this.controller = controller;
        this.camera = cam;
        this.assMan = assetManager;
        world = new World(new Vector2(0, -10f), true);
        world.setContactListener(new B2dContactListener(this));
        createFloor();
        //createObject();
        //createMovingObject();

        assMan.queueAddSounds();
        assMan.manager.finishLoading();

        ping = assMan.manager.get(B2dAssetManager.PING_SOUND, Sound.class);
        boing = assMan.manager.get(B2dAssetManager.BOING_SOUND, Sound.class);

        BodyFactory bodyFactory = BodyFactory.getInstance(world);
        //Add a player
        player = bodyFactory.makeBoxPolyBody(1, 1, 2, 2, BodyFactory.RUBBER);

        //add some watter
        Body watter = bodyFactory.makeBoxPolyBody(1, -6, 40, 6, BodyFactory.RUBBER, BodyDef.BodyType.StaticBody);

        //make the watter a sensor
        bodyFactory.makeAllFixturesSensors(watter);

        watter.setUserData("IAMTHESEA");

        /*
        bodyFactory.makeCirclePolyBody(2, 1,2, BodyFactory.RUBBER, BodyDef.BodyType.DynamicBody);
        //bodyFactory.makeCirclePolyBody(-3, 2,2, BodyFactory.RUBBER, BodyDef.BodyType.DynamicBody);
        bodyFactory.makeCirclePolyBody(4, 1,1.8f, BodyFactory.STEEL, BodyDef.BodyType.DynamicBody);
        bodyFactory.makeCirclePolyBody(-4, 1,3, BodyFactory.STONE, BodyDef.BodyType.DynamicBody);
        bodyFactory.makeCirclePolyBody(-2, 1,2.2f, BodyFactory.WOOD, BodyDef.BodyType.DynamicBody);
*/
        //bodyFactory.makeBoxPolyBody(5, 2, 1, 2, BodyFactory.RUBBER);
        //bodyFactory.makeBoxPolyBody(-5, 9, 0.1f, 2, BodyFactory.RUBBER);
        //bodyFactory.makeBoxPolyBody(-5, 10, 4, 0.4f, BodyFactory.RUBBER);

        //bodyFactory.makeBoxPolyBody(3, 10, 4, 0.2f, 10);
    }

    public void logicStep(float delta){
        if (controller.left){
            player.applyForceToCenter(-10, 0,true);
        }else if (controller.right){
            player.applyForceToCenter(10, 0,true);
        }else if (controller.up){
            player.applyForceToCenter(0, 10,true);
        }else if (controller.down){
            player.applyForceToCenter(0, -10,true);
        }
        if (isSwimming){
            player.applyForceToCenter(0, 40,true);
        }
        if (controller.isMouse1Down && pointIntersectsBody(player, controller.mouseLocation)){
            Gdx.app.log("controll", "Player was clicked");
        }
        world.step(delta, 3, 3);
    }

    public boolean pointIntersectsBody(Body body, Vector2 mouseLocation){
        Vector3 mousePos = new Vector3(mouseLocation, 0);
        camera.unproject(mousePos);
        if (body.getFixtureList().first().testPoint(mouseLocation.x, mouseLocation.y)){
            return true;
        }
        return false;
    }

    public void playSound(int sound){
        switch (sound){
            case BOING_SOUND:
                boing.play();
                break;
            case PING_SOUND:
                ping.play();
                break;
            default:
                ping.play();
        }
    }

    private void createObject(){

        //create a new body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0, 0);

        //add it to the world
        bodyd = world.createBody(bodyDef);

        //set the shape
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1, 1);

        // set the properties of the object ( shape, weight, restitution(bouncyness)
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        // create the physical object in our body)
        // without this our body would just be data in the world
        bodyd.createFixture(shape, 0f);

        //dispose shape
        shape.dispose();
    }

    private void createFloor(){
        // create a new body definition (type and location)
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, -10);

        //add it to world
        bodys = world.createBody(bodyDef);
        // set the shape (here we use a box 50 meters wide, 1 meter tall )
        PolygonShape shape = new PolygonShape();

        shape.setAsBox(50, 1);
        // create the physical object in our body)
        // without this our body would just be data in the world
        bodys.createFixture(shape, 0f);

        //this part it's to make tests
        Vector2[] vertices = new Vector2[4];
        vertices[0] = new Vector2(-16, 0.8f);
        vertices[1] = new Vector2(-16, 20);
        vertices[2] = new Vector2(-15.5f, 20);
        vertices[3] = new Vector2(-15.5f, 0.8f);
        shape.set(vertices);
        bodys.createFixture(shape, 0f);
        vertices[0] = new Vector2(15.5f, 0.8f);
        vertices[1] = new Vector2(15.5f, 20);
        vertices[2] = new Vector2(16, 20);
        vertices[3] = new Vector2(16, 0.8f);
        shape.set(vertices);
        bodys.createFixture(shape, 05.f);
        vertices[0] = new Vector2(-16, 21);
        vertices[1] = new Vector2(16, 21);
        vertices[2] = new Vector2(16, 20);
        vertices[3] = new Vector2(-16, 20);
        shape.set(vertices);
        bodys.createFixture(shape, 05.f);

        //dispose shape
        shape.dispose();
    }

    private void createMovingObject(){
        //create a new body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(0, -12);

        //add it to the world
        bodyk = world.createBody(bodyDef);

        //set the shape
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1, 1);

        // set the properties of the object ( shape, weight, restitution(bouncyness)
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        // create the physical object in our body)
        // without this our body would just be data in the world
        bodyk.createFixture(shape, 0f);

        //dispose shape
        shape.dispose();

        bodyk.setLinearVelocity(0, 0.75f);
    }
}
