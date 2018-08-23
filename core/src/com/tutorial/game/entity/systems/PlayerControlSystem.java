package com.tutorial.game.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.tutorial.game.LevelFactory;
import com.tutorial.game.controller.KeyboardController;
import com.tutorial.game.entity.components.B2dBodyComponent;
import com.tutorial.game.entity.components.PlayerComponent;
import com.tutorial.game.entity.components.StateComponent;

public class PlayerControlSystem extends IteratingSystem {

    private LevelFactory lvlFactory;
    ComponentMapper<PlayerComponent> pm;
    ComponentMapper<B2dBodyComponent> bodm;
    ComponentMapper<StateComponent> sm;
    KeyboardController controller;

    public PlayerControlSystem(KeyboardController keyCon, LevelFactory lvlf){
        super(Family.all(PlayerComponent.class).get());
        controller = keyCon;
        this.lvlFactory = lvlf;
        pm = ComponentMapper.getFor(PlayerComponent.class);
        bodm = ComponentMapper.getFor(B2dBodyComponent.class);
        sm = ComponentMapper.getFor(StateComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        B2dBodyComponent b2body = bodm.get(entity);
        StateComponent state = sm.get(entity);
        PlayerComponent player = pm.get(entity);

        if (b2body.body.getLinearVelocity().y < 0){
            state.set(StateComponent.STATE_FALLING);
        }
        if (b2body.body.getLinearVelocity().y == 0){
            if (state.get() == StateComponent.STATE_FALLING){
                state.set(StateComponent.STATE_NORMAL);
            }
            if (b2body.body.getLinearVelocity().x !=0){
                state.set(StateComponent.STATE_MOVING);
            }
        }

        if (player.onSpring){
            //b2body.body.applyLinearImpulse(0, 175f, b2body.body.getWorldCenter().x,b2body.body.getWorldCenter().y, true);
            b2body.body.setTransform(b2body.body.getPosition().x, b2body.body.getPosition().y + 10, b2body.body.getAngle());
            //state.set(StateComponent.STATE_JUMPING);
            player.onSpring = false;
        }

        if (controller.left){
            b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, -7f, 0.2f), b2body.body.getLinearVelocity().y);
        }
        if (controller.right){
            b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, 7f, 0.2f), b2body.body.getLinearVelocity().y);
        }
        if (!controller.left && !controller.right){
            b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, 0f, 0.1f), b2body.body.getLinearVelocity().y);
        }

        if (controller.up &&
                (state.get() == StateComponent.STATE_NORMAL || state.get() == StateComponent.STATE_MOVING)){
                    b2body.body.applyLinearImpulse(0, 15f, b2body.body.getWorldCenter().x, b2body.body.getWorldCenter().y, true);
                    state.set(StateComponent.STATE_JUMPING);
        }
        if (controller.isMouse1Down){
            if (player.timeSinceLastShot <=0){
                Vector3 mousePos = new Vector3(controller.mouseLocation.x, controller.mouseLocation.y, 0);
                player.cam.unproject(mousePos);
                float speed = 10f;
                float shooterX = b2body.body.getPosition().x;
                float shooterY = b2body.body.getPosition().y;
                float velx = mousePos.x - shooterX;
                float vely = mousePos.y - shooterY;

                float lenght = (float) Math.sqrt(velx * velx + vely * vely);

                if (lenght != 0){
                    velx = velx / lenght; // get required x velocity to aim at target
                    vely = vely / lenght; // get required y velocity to aim at target
                }
                // create a bullet
                lvlFactory.createBullet(shooterX, shooterY, velx*speed, vely*speed);
                player.timeSinceLastShot = player.shootDeley;
            }
        }
    }
}
