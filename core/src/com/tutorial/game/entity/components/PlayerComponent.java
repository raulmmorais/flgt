package com.tutorial.game.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Pool;

public class PlayerComponent implements Component, Pool.Poolable {
    public OrthographicCamera cam = null;
    public boolean onPlatform = false;
    public boolean onSpring = false;
    public boolean isDead = false;
    public float shootDeley = 0.5f;
    public float timeSinceLastShot = 0f;

    @Override
    public void reset() {
        cam = null;
        onPlatform = false;
        onSpring = false;
        isDead = false;
        shootDeley = 0.5f;
        timeSinceLastShot = 0f;
    }
}
