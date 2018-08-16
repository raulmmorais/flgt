package com.tutorial.game.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.tutorial.game.entity.components.TransformComponent;

import java.util.Comparator;

class ZComparator implements Comparator<Entity> {
    private ComponentMapper<TransformComponent> cmTrans;

    public ZComparator(){
        cmTrans = ComponentMapper.getFor(TransformComponent.class);
    }
    @Override
    public int compare(Entity entityA, Entity entityB) {
        float az = cmTrans.get(entityA).position.z;
        float bz = cmTrans.get(entityB).position.z;
        int res = 0;
        if (az > bz){
            res = 1;
        }else if (az < bz){
            res = -1;
        }
        return res;
    }
}
