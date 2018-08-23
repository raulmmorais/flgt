package com.tutorial.game.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.tutorial.game.entity.components.BulletComponent;
import com.tutorial.game.entity.components.CollisionComponent;
import com.tutorial.game.entity.components.EnemyComponent;
import com.tutorial.game.entity.components.Mapper;
import com.tutorial.game.entity.components.PlayerComponent;
import com.tutorial.game.entity.components.TypeComponent;

public class CollisionSystem extends IteratingSystem {
    ComponentMapper<CollisionComponent> cm;
    ComponentMapper<PlayerComponent> pm;

    public CollisionSystem(){
        super(Family.all(CollisionComponent.class).get());

        cm = ComponentMapper.getFor(CollisionComponent.class);
        pm = ComponentMapper.getFor(PlayerComponent.class);
    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // get collision for this entity
        CollisionComponent cc = cm.get(entity);
        //get collided entity
        Entity collidedEntity = cc.collisionEntity;

        TypeComponent thisType = entity.getComponent(TypeComponent.class);

        //Do Player Collisions
        if (thisType.type == TypeComponent.PLAYER){
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
                        case TypeComponent.BULLET:
                            Gdx.app.log("Collide", "Player just shot. bullet in player atm");
                            break;
                        default:
                            Gdx.app.log("Collide", "No matching type found");
                    }
                    cc.collisionEntity = null;
                }else {
                    Gdx.app.log("Collide", "type == null");
                }
            }
        }else if (thisType.type == TypeComponent.ENEMY){ //Do Enemy Collisions
            if (collidedEntity != null){
                TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
                if (type != null){
                    switch (type.type){
                        case TypeComponent.PLAYER:
                            Gdx.app.log("Collide", "enemy hit player");
                            break;
                        case TypeComponent.ENEMY:
                            Gdx.app.log("Collide", "enemy hit enemy");
                            break;
                        case TypeComponent.SCENERY:
                            Gdx.app.log("Collide", "enemy hit scenery");
                            break;
                        case TypeComponent.SPRING:
                            Gdx.app.log("Collide", "enemy hit spring: bounce up");
                            break;
                        case TypeComponent.OTHER:
                            Gdx.app.log("Collide", "enemy hit other");
                            break;
                        case TypeComponent.BULLET:
                            EnemyComponent enemy = Mapper.enemyCom.get(entity);
                            enemy.isDead = true;
                            BulletComponent bullet = Mapper.bulletCom.get(collidedEntity);
                            bullet.isDead = true;
                            System.out.println("enemy got shot");
                            break;
                        default:
                            System.out.println("No matching type found");
                    }
                    cc.collisionEntity = null; // collision handled reset component
                }else{
                    System.out.println("Enemy: collidedEntity.type == null");
                }
            }
        }else{
            cc.collisionEntity = null;
        }

    }
}
