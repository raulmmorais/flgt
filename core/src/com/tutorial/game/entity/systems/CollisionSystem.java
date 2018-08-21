package com.tutorial.game.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.tutorial.game.entity.components.CollisionComponent;
import com.tutorial.game.entity.components.PlayerComponent;
import com.tutorial.game.entity.components.TypeComponent;

public class CollisionSystem extends IteratingSystem {
    ComponentMapper<CollisionComponent> cm;
    ComponentMapper<PlayerComponent> pm;

    public CollisionSystem(){
        super(Family.all(CollisionComponent.class, PlayerComponent.class).get());

        cm = ComponentMapper.getFor(CollisionComponent.class);
        pm = ComponentMapper.getFor(PlayerComponent.class);
    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CollisionComponent cc = cm.get(entity);

        Entity collidedEntity = cc.collisionEntity;
        if (collidedEntity != null){
            TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
            if (type != null){
                switch (type.type){
                    case TypeComponent.ENEMY:
                        Gdx.app.log("Collide", "player hit enemy");
                        PlayerComponent pl = pm.get(entity);
                        pl.isDead = true;
                        int score = (int) pl.cam.position.y;
                        Gdx.app.log("Score", String.valueOf(score));
                        break;
                    case TypeComponent.SCENERY:
                        Gdx.app.log("Collide", "player hit scenery");
                        pm.get(entity).onPlatform = true;
                        break;
                    case TypeComponent.SPRING:
                        Gdx.app.log("Collide", "player hit spring: bounce up");
                        pm.get(entity).onSpring = true;
                        break;
                    case TypeComponent.OTHER:
                        Gdx.app.log("Collide", "player hit other");
                        break;
                    default:
                        Gdx.app.log("Collide", "No matching type found");
                }
                cc.collisionEntity = null;
            }else {
                Gdx.app.log("Collide", "type == null");
            }
        }
    }
}
