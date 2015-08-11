package com.baidu.rollstone.engine;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.CircleDef;
import org.jbox2d.collision.PolygonDef;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;

/**
 * Created by wuxinting on 15/8/10.
 */
public class Engine extends AbsEngine {

    public Engine(AABB aabb, EngineConfig config) {
        super(aabb, config);
    }

    public Engine(AABB aabb) {
        super(aabb, new AbsEngine.EngineConfig());
    }

    @Override
    public void step() {
        world.step(config.getTimeStep(), config.getIterations());
    }

    public Body addRectBody(Vec2 position, Vec2 size, ShapeConfig config) {
        BodyDef def = new BodyDef();
        def.position.set(position);

        PolygonDef pDef = new PolygonDef();
        pDef.setAsBox(size.x, size.y);

        return addBody(def, pDef, config);
    }

    public Body addCircleBody(Vec2 position, float radius, ShapeConfig config) {
        BodyDef def = new BodyDef();
        def.position.set(position);

        CircleDef cDef = new CircleDef();
        cDef.radius = radius;

        return addBody(def, cDef, config);
    }
}
