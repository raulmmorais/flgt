package com.tutorial.game.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;

public class B2dBodyComponent implements Component, Pool.Poolable {
    public Body body;
    public boolean isDead = false;

    @Override
    public void reset() {
        isDead = false;
    }
}
