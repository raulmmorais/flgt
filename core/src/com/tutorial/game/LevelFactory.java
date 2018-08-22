package com.tutorial.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.tutorial.game.entity.components.B2dBodyComponent;
import com.tutorial.game.entity.components.CollisionComponent;
import com.tutorial.game.entity.components.PlayerComponent;
import com.tutorial.game.entity.components.StateComponent;
import com.tutorial.game.entity.components.TextureComponent;
import com.tutorial.game.entity.components.TransformComponent;
import com.tutorial.game.entity.components.TypeComponent;
import com.tutorial.game.entity.components.WallComponent;
import com.tutorial.game.entity.components.WaterFloorComponent;
import com.tutorial.game.simplexnoise.SimplexNoise;

public class LevelFactory {

    private BodyFactory bodyFactory;
    public World world;
    private PooledEngine engine;
    private SimplexNoise sim;
    public int currentLevel = 0;
    private TextureRegion floorTex;

    public LevelFactory(PooledEngine en, TextureRegion floorTexture){
        engine = en;
        floorTex = floorTexture;
        world = new World(new Vector2(0,-10f), true);
        world.setContactListener(new B2dContactListener());
        bodyFactory = BodyFactory.getInstance(world);
        // create a new SimplexNoise (size,roughness,seed)
        sim = new SimplexNoise(512, 0.85f, 1);
    }

    public void generateLevel(int ylevel){
        while(ylevel > currentLevel){
            // get noise      sim.getNoise(xpos,ypos,zpos) 3D noise
            float noise1 = (float)sim.getNoise(1, currentLevel, 0);
            float noise2 = (float)sim.getNoise(1, currentLevel, 100);
            float noise3 = (float)sim.getNoise(1, currentLevel, 200);
            float noise4 = (float)sim.getNoise(1, currentLevel, 300);
            float noise5 = (float)sim.getNoise(1, currentLevel, 1400);
            float noise6 = (float)sim.getNoise(1, currentLevel, 2500);
            float noise7 = (float)sim.getNoise(1, currentLevel, 2700);
            float noise8 = (float)sim.getNoise(1, currentLevel, 3000);
            if(noise1 > 0.2f){
                createPlatform(noise2 * 25 +2 ,currentLevel * 2);
                if (noise5 > 0.5f){
                    createBouncyPlatform(noise2 * 25 + 2 ,currentLevel * 2);
                }
            }
            if(noise3 > 0.2f){
                createPlatform(noise4 * 25 +2, currentLevel * 2);
                if (noise6 > 0.4f){
                    createBouncyPlatform(noise4 * 25 + 2 ,currentLevel * 2);
                }
            }
            currentLevel++;
        }
    }

    public void createPlatform(float x, float y) {
        Entity entity = engine.createEntity();
        B2dBodyComponent b2dbody = engine.createComponent(B2dBodyComponent.class);
        b2dbody.body = bodyFactory.makeBoxPolyBody(x, y, 1.5f, 0.2f, BodyFactory.STONE, BodyType.StaticBody);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = floorTex;
        TypeComponent type = engine.createComponent(TypeComponent.class);
        type.type = TypeComponent.SCENERY;
        b2dbody.body.setUserData(entity);
        entity.add(b2dbody);
        entity.add(texture);
        entity.add(type);
        engine.addEntity(entity);
    }

    public Entity createBouncyPlatform(float x, float y){
        Entity entity = engine.createEntity();
        B2dBodyComponent b2dbody = engine.createComponent(B2dBodyComponent.class);
        b2dbody.body = bodyFactory.makeBoxPolyBody(x, y, 0.5f, 0.5f, BodyFactory.STONE, BodyType.StaticBody);
        bodyFactory.makeAllFixturesSensors(b2dbody.body);

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = floorTex;

        TypeComponent type = engine.createComponent(TypeComponent.class);
        type.type = TypeComponent.SPRING;

        b2dbody.body.setUserData(entity);
        entity.add(b2dbody);
        entity.add(texture);
        entity.add(type);
        engine.addEntity(entity);

        return entity;
    }

    public void createFloor(TextureRegion tex){
        Entity entity = engine.createEntity();
        B2dBodyComponent b2dbody = engine.createComponent(B2dBodyComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);

        position.position.set(20, 0, 0);
        texture.region = tex;
        type.type = TypeComponent.SCENERY;
        b2dbody.body = bodyFactory.makeBoxPolyBody(20, 0, 40, 0.5f, BodyFactory.STONE, BodyType.StaticBody);

        entity.add(b2dbody);
        entity.add(texture);
        entity.add(position);
        entity.add(type);

        b2dbody.body.setUserData(entity);

        engine.addEntity(entity);
    }

    public Entity createPlayer(TextureRegion tex, OrthographicCamera cam){
        //create all entity and all components
        Entity entity = engine.createEntity();
        B2dBodyComponent b2dbody = engine.createComponent(B2dBodyComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        PlayerComponent player = engine.createComponent(PlayerComponent.class);
        CollisionComponent colComp = engine.createComponent(CollisionComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        StateComponent stateCom = engine.createComponent(StateComponent.class);

        player.cam = cam;
        b2dbody.body = bodyFactory.makeCirclePolyBody(10,1,1, BodyFactory.STONE, BodyType.DynamicBody,true);
        // set object position (x,y,z) z used to define draw order 0 first drawn
        position.position.set(10,1,0);
        texture.region = tex;
        type.type = TypeComponent.PLAYER;
        stateCom.set(StateComponent.STATE_NORMAL);
        b2dbody.body.setUserData(entity);

        entity.add(b2dbody);
        entity.add(position);
        entity.add(texture);
        entity.add(player);
        entity.add(colComp);
        entity.add(type);
        entity.add(stateCom);

        engine.addEntity(entity);

        return entity;
    }

    public void createWalls(TextureRegion tex) {
        for (int i = 0; i<2; i++){
            Entity entity = engine.createEntity();
            B2dBodyComponent b2dbody = engine.createComponent(B2dBodyComponent.class);
            TransformComponent position = engine.createComponent(TransformComponent.class);
            TextureComponent texture = engine.createComponent(TextureComponent.class);
            TypeComponent type = engine.createComponent(TypeComponent.class);
            WallComponent wallComp = engine.createComponent(WallComponent.class);

            //make wall
            b2dbody.body = bodyFactory.makeBoxPolyBody(0 + (i*40), 30, 1f, 60f, BodyFactory.STONE, BodyType.KinematicBody, true);
            position.position.set(0+(i*40), 30, 0);
            texture.region = tex;
            type.type = TypeComponent.SCENERY;

            entity.add(b2dbody);
            entity.add(position);
            entity.add(texture);
            entity.add(type);
            entity.add(wallComp);
            b2dbody.body.setUserData(entity);

            engine.addEntity(entity);
        }
    }

    public Entity createWaterFloor(TextureRegion tex){
        Entity entity = engine.createEntity();
        B2dBodyComponent b2dBody = engine.createComponent(B2dBodyComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        WaterFloorComponent waterFloor = engine.createComponent(WaterFloorComponent.class);

        type.type = TypeComponent.ENEMY;
        texture.region = tex;
        b2dBody.body = bodyFactory.makeBoxPolyBody(20,-15,40,10, BodyFactory.STONE, BodyType.KinematicBody,true);
        position.position.set(20, -15, 0);

        entity.add(b2dBody);
        entity.add(position);
        entity.add(texture);
        entity.add(type);
        entity.add(waterFloor);

        b2dBody.body.setUserData(entity);

        engine.addEntity(entity);

        return entity;
    }
}
