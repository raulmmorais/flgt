package com.tutorial.game.entity.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.tutorial.game.entity.components.PlayerComponent;

public class WallSystem extends IteratingSystem {
    private Entity player;


    public WallSystem(Entity player) {
        super(Family.all(PlayerComponent.class).get());
        this.player = player;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }
}
