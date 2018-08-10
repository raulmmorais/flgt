package com.tutorial.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class BodyFactory {
    private static BodyFactory thisInstance;
    private World world;
    private final float DEGTORAD = 0.0174533f;
    public static final int STEEL = 0;
    public static final int WOOD = 1;
    public static final int RUBBER = 2;
    public static final int STONE = 3;

    private BodyFactory(World world) {
        this.world = world;
    }

    public static BodyFactory getInstance(World world){
        if (thisInstance == null){
            thisInstance = new BodyFactory(world);
        }
        return thisInstance;
    }

    static public FixtureDef makeFixture(int material, Shape shape){
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        switch (material){
            case STEEL:
                fixtureDef.density = 1f;
                fixtureDef.friction = 0.3f;
                fixtureDef.restitution = 0.1f;
                break;
            case WOOD:
                fixtureDef.density = 0.5f;
                fixtureDef.friction = 0.7f;
                fixtureDef.restitution = 0.3f;
                break;
            case RUBBER:
                fixtureDef.density = 1f;
                fixtureDef.friction = 0f;
                fixtureDef.restitution = 1f;
                break;
            case STONE:
                fixtureDef.density = 1f;
                fixtureDef.friction = 0.9f;
                fixtureDef.restitution = 0.1f;
                break;
            default:
                fixtureDef.density = 7f;
                fixtureDef.friction = 0.5f;
                fixtureDef.restitution = 0.3f;
        }

        return fixtureDef;
    }

    public Body makeCirclePolyBody (float posX, float posY, float radius, int material, BodyDef.BodyType bodyType, boolean fixedRotation){
        BodyDef boxBodyDef = getBodyDef(posX, posY, bodyType, fixedRotation);

        Body boxBody = world.createBody(boxBodyDef);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(radius/2);
        boxBody.createFixture(makeFixture(material, circleShape));
        circleShape.dispose();

        return boxBody;
    }

    /***
     *
     * @param posX - Posição x
     * @param posY - Posição y
     * @param radius - Raio do circulo
     * @param material - Densidade
     * @param bodyType - Tipo específico de corpo
     * @return
     */
    public Body makeCirclePolyBody (float posX, float posY, float radius, int material, BodyDef.BodyType bodyType){
        return makeCirclePolyBody(posX, posY, radius, material, bodyType, false);
    }

    public Body makeCirclePolyBody (float posX, float posY, float radius, int material){
        return makeCirclePolyBody(posX, posY, radius, material, BodyDef.BodyType.DynamicBody);
    }

    public Body makeBoxPolyBody(float posX, float posY, float width, float height, int material, BodyDef.BodyType bodyType){
        return makeBoxPolyBody(posX, posY, width, height, material, bodyType, false);
    }

    public Body makeBoxPolyBody(float posX, float posY, float width, float height, int material){
        return makeBoxPolyBody(posX, posY, width, height, material, BodyDef.BodyType.DynamicBody);
    }

    public Body makeBoxPolyBody(float posX, float posY, float width, float height, int material, BodyDef.BodyType bodyType, boolean fixedRotation){
        BodyDef boxBodyDef = getBodyDef(posX, posY, bodyType, fixedRotation);

        Body boxBody = world.createBody(boxBodyDef);
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(width / 2, height / 2);
        boxBody.createFixture(makeFixture(material, poly));
        poly.dispose();

        return boxBody;
    }

    public Body makePolygonShapeBody(Vector2[] vertices, float posx, float posy, int material){
        return makePolygonShapeBody(vertices, posx, posy, material, BodyDef.BodyType.DynamicBody);
    }

    public Body makePolygonShapeBody(Vector2[] vertices, float posx, float posy, int material, BodyDef.BodyType bodyType){
        return makePolygonShapeBody(vertices, posx, posy, material, bodyType, false);
    }

    public Body makePolygonShapeBody(Vector2[] vertices, float posx, float posy, int material, BodyDef.BodyType bodyType, boolean fixedRotation){
        BodyDef boxBodyDef = getBodyDef(posx, posy, bodyType, fixedRotation);

        Body boxBody = world.createBody(boxBodyDef);
        PolygonShape polygon = new PolygonShape();
        polygon.set(vertices);
        boxBody.createFixture(makeFixture(material, polygon));
        polygon.dispose();

        return boxBody;
    }

    public void makeConeSensor(Body body, float size){
        FixtureDef fixtureDef = new FixtureDef();
        //fixtureDef.isSensor = true; //Use in future

        PolygonShape polygon = new PolygonShape();

        float radius = size;
        Vector2[] vertices = new Vector2[5];
        vertices[0] = new Vector2(0, 0);
        for (int i = 2; i < 6; i++){
            float angle = (float)(i / 6.0 * 145 * DEGTORAD);
            vertices[i - 1] = new Vector2(radius * ((float)Math.cos(angle)), radius * ((float)Math.sin(angle)));
        }
        polygon.set(vertices);
        fixtureDef.shape = polygon;
        body.createFixture(fixtureDef);
        polygon.dispose();

    }

    private BodyDef getBodyDef(float posX, float posY, BodyDef.BodyType bodyType, boolean fixedRotation) {
        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = bodyType;
        boxBodyDef.position.x = posX;
        boxBodyDef.position.y = posY;
        boxBodyDef.fixedRotation = fixedRotation;
        return boxBodyDef;
    }

}
