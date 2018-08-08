package com.tutorial.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class B2DModel {
    public World world;

    public B2DModel(){
        world = new World(new Vector2(0, -10f), true);
    }

    public void logicStep(float delta){
        world.step(delta, 3, 3);
    }
}
