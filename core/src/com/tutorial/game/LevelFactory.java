package com.tutorial.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.tutorial.game.entity.components.AnimationComponent;
import com.tutorial.game.entity.components.B2dBodyComponent;
import com.tutorial.game.entity.components.BulletComponent;
import com.tutorial.game.entity.components.CollisionComponent;
import com.tutorial.game.entity.components.EnemyComponent;
import com.tutorial.game.entity.components.PlayerComponent;
import com.tutorial.game.entity.components.StateComponent;
import com.tutorial.game.entity.components.TextureComponent;
import com.tutorial.game.entity.components.TransformComponent;
import com.tutorial.game.entity.components.TypeComponent;
import com.tutorial.game.entity.components.WallComponent;
import com.tutorial.game.entity.components.WaterFloorComponent;
import com.tutorial.game.entity.systems.RenderingSystem;
import com.tutorial.game.simplexnoise.OpenSimplexNoise;
import com.tutorial.game.simplexnoise.SimplexNoise;

public class LevelFactory {

    private final TextureRegion platformTex;
    private BodyFactory bodyFactory;
    public World world;
    private PooledEngine engine;
    //private SimplexNoise sim;
    private final OpenSimplexNoise openSin;
    public int currentLevel = 0;
    private TextureRegion floorTex;
    private TextureRegion enemyTex;
    private TextureRegion bulletTex;
    private TextureAtlas atlas;

    public LevelFactory(PooledEngine en, TextureRegion floorTexture){
        engine = en;
        floorTex = DFUtils.makeTextureRegion(40*RenderingSystem.PPM, 0.5f*RenderingSystem.PPM, "111111FF");
        enemyTex = DFUtils.makeTextureRegion(1*RenderingSystem.PPM, 1*RenderingSystem.PPM, "331111FF");
        platformTex = DFUtils.makeTextureRegion(2*RenderingSystem.PPM, 0.1f*RenderingSystem.PPM, "221122FF");
        bulletTex = DFUtils.makeTextureRegion(10, 10, "444444FF");
        world = new World(new Vector2(0,-10f), true);
        world.setContactListener(new B2dContactListener());
        bodyFactory = BodyFactory.getInstance(world);
        // create a new SimplexNoise (size,roughness,seed)
        //sim = new SimplexNoise(512, 0.85f, 1);

        openSin = new OpenSimplexNoise(MathUtils.random(2000l));
    }

    public void generateLevel(int ylevel){
        while(ylevel > currentLevel){
            int range = 15;
            for (int i = 1; i < 5; i++){
                generateSingleColumn(genNForL(i * 1, currentLevel)
                        ,genNForL(i * 100, currentLevel)
                        ,genNForL(i * 200, currentLevel)
                        ,genNForL(i * 300, currentLevel)
                        ,range, i*10);
            }
            currentLevel++;
        }
    }

    private void generateSingleColumn(float n1, float n2, float n3, float n4, int range, int offset) {
        if (n1 > -0.8f){
            createPlatform(n2 * range + offset, currentLevel*2);
            if (n3 > 0.3f){
                createBouncyPlatform(n2 * range + offset, currentLevel*2);
            }
            if (n4 > 0.2f){
                createEnemy(enemyTex, n2 * range + offset, currentLevel*2 + 1);
            }
        }
    }

    private float genNForL(int level, int height) {
        return (float)openSin.eval(height, level);
    }

    public void createPlatform(float x, float y) {
        Entity entity = engine.createEntity();
        B2dBodyComponent b2dbody = engine.createComponent(B2dBodyComponent.class);
        b2dbody.body = bodyFactory.makeBoxPolyBody(x, y, 1.5f, 0.2f, BodyFactory.STONE, BodyType.StaticBody);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = platformTex;
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
        b2dbody.body = bodyFactory.makeCirclePolyBody(10,1,1, BodyFactory.STONE, BodyType.DynamicBody,false);
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

    public Entity createEnemy(TextureRegion tex, float x, float y){
        Entity entity = engine.createEntity();
        B2dBodyComponent b2dBody = engine.createComponent(B2dBodyComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        EnemyComponent enemy = engine.createComponent(EnemyComponent.class);
        CollisionComponent colComp = engine.createComponent(CollisionComponent.class);

        b2dBody.body = bodyFactory.makeCirclePolyBody(x, y, 1, BodyFactory.STONE, BodyType.KinematicBody, true);
        position.position.set(x, y, 0);
        texture.region = tex;
        enemy.xPosCenter = x;
        type.type = TypeComponent.ENEMY;

        entity.add(colComp);
        entity.add(b2dBody);
        entity.add(position);
        entity.add(texture);
        entity.add(type);
        entity.add(enemy);

        b2dBody.body.setUserData(entity);

        engine.addEntity(entity);

        return entity;
    }
    public Entity createBullet(float x, float y, float velX, float velY){
        Entity entity = engine.createEntity();
        B2dBodyComponent b2dBody = engine.createComponent(B2dBodyComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        AnimationComponent animCom = engine.createComponent(AnimationComponent.class);
        StateComponent stateCom = engine.createComponent(StateComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        CollisionComponent colComp = engine.createComponent(CollisionComponent.class);
        BulletComponent bul = engine.createComponent(BulletComponent.class);

        b2dBody.body = bodyFactory.makeCirclePolyBody(x, y, 0.5f, BodyFactory.STONE, BodyType.DynamicBody, true);
        b2dBody.body.setBullet(true);// increase physics computation to limit body travelling through other objects
        bodyFactory.makeAllFixturesSensors(b2dBody.body);// make bullets sensors so they don't move player
        position.position.set(x, y, 0);
        texture.region = bulletTex;
        Animation anim = new Animation(0.05f, DFUtils.spritesheetToFrames(atlas.findRegion("FlameSpriteAnimation"), 7, 1));
        anim.setPlayMode(Animation.PlayMode.LOOP);
        animCom.animations.put(0, anim);

        type.type = TypeComponent.BULLET;
        bul.xVel = velX;
        bul.yVel = velY;

        entity.add(bul);
        entity.add(colComp);
        entity.add(b2dBody);
        entity.add(position);
        entity.add(texture);
        entity.add(animCom);
        entity.add(stateCom);
        entity.add(type);

        b2dBody.body.setUserData(entity);

        engine.addEntity(entity);
        return entity;
    }
}
