package com.tutorial.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import javax.swing.BorderFactory;

public class B2DModel {
    public World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    private OrthographicCamera camera;
    private Body bodyd;
    private Body bodys;
    private Body bodyk;

    public B2DModel(){
        world = new World(new Vector2(0, -10f), true);
        createFloor();
        createObject();
        createMovingObject();

        BodyFactory bodyFactory = BodyFactory.getInstance(world);

        bodyFactory.makeCirclePolyBody(2, 1,2, BodyFactory.RUBBER, BodyDef.BodyType.DynamicBody);
        bodyFactory.makeCirclePolyBody(-3, 2,2, BodyFactory.RUBBER, BodyDef.BodyType.DynamicBody);
        bodyFactory.makeCirclePolyBody(-6, 3,2, BodyFactory.RUBBER, BodyDef.BodyType.DynamicBody);
        bodyFactory.makeCirclePolyBody(7, 4,2, BodyFactory.RUBBER, BodyDef.BodyType.DynamicBody);
        bodyFactory.makeCirclePolyBody(8, 1,2, BodyFactory.RUBBER, BodyDef.BodyType.DynamicBody);
        bodyFactory.makeCirclePolyBody(4, 1,2, BodyFactory.STEEL, BodyDef.BodyType.DynamicBody);
        bodyFactory.makeCirclePolyBody(-4, 1,2, BodyFactory.STONE, BodyDef.BodyType.DynamicBody);
        bodyFactory.makeCirclePolyBody(-2, 1,2, BodyFactory.WOOD, BodyDef.BodyType.DynamicBody);

        bodyFactory.makeBoxPolyBody(5, 2, 1, 2, BodyFactory.RUBBER);
        bodyFactory.makeBoxPolyBody(-5, 9, 0.1f, 2, BodyFactory.RUBBER);
        bodyFactory.makeBoxPolyBody(-5, 10, 4, 0.4f, BodyFactory.RUBBER);

        bodyFactory.makeBoxPolyBody(3, 10, 4, 0.4f, 10);
    }

    public void logicStep(float delta){
        world.step(delta, 3, 3);
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
