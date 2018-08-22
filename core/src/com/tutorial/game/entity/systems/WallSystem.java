package com.tutorial.game.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Body;
import com.tutorial.game.entity.components.B2dBodyComponent;
import com.tutorial.game.entity.components.WallComponent;

public class WallSystem extends IteratingSystem {
    private Entity player;
    private ComponentMapper<B2dBodyComponent> bm = ComponentMapper.getFor(B2dBodyComponent.class);

    public WallSystem(Entity player) {
        super(Family.all(WallComponent.class).get());
        this.player = player;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        float currentLevel = player.getComponent(B2dBodyComponent.class).body.getPosition().y;

        Body bod = bm.get(entity).body;
        bod.setTransform(bod.getPosition().x, currentLevel, bod.getAngle());
    }
}
