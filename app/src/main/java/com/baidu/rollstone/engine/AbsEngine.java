package com.baidu.rollstone.engine;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.ShapeDef;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

/**
 * Created by wuxinting on 15/8/10.
 * Abstract Engine Class
 */
public abstract class AbsEngine {

    protected AABB aabb;
    protected World world;

    protected EngineConfig config;

    public AbsEngine(AABB aabb, EngineConfig config) {
        this.aabb = aabb;
        this.config = config;

        world = new World(aabb, config.getGravity(), true);
    }

    public abstract void step();

    public Body addBody(BodyDef bodyDef, ShapeDef shapeDef, ShapeConfig config) {
        Body body = world.createBody(bodyDef);

        if (config != null) {
            shapeDef.friction = config.getFriction();
            shapeDef.density = config.getDensity();
            shapeDef.restitution = config.getRestitution();
            shapeDef.userData = config.getData();
        }
        body.createShape(shapeDef);
        body.setMassFromShapes();

        return body;
    }

    public AABB getAabb() {
        return aabb;
    }

    public Body getBodyList() {
        return world.getBodyList();
    }

    public int getBodyCount() {
        return world.getBodyCount();
    }

    public World getWorld() {
        return world;
    }

    public static class EngineConfig {
        private float timeStep = 1.0f / 60.0f;
        private int iterations = 10;
        private Vec2 gravity = new Vec2(0.0f, -1.8f);

        public float getTimeStep() {
            return timeStep;
        }

        public void setTimeStep(float timeStep) {
            this.timeStep = timeStep;
        }

        public int getIterations() {
            return iterations;
        }

        public void setIterations(int iterations) {
            this.iterations = iterations;
        }

        public Vec2 getGravity() {
            return gravity;
        }

        public void setGravity(Vec2 gravity) {
            this.gravity = gravity;
        }
    }

    public static class ShapeConfig {
        private float friction;
        private float density;
        private float restitution;
        private Object data;

        public ShapeConfig(float friction, float density) {
            this.friction = friction;
            this.density = density;
        }

        public ShapeConfig() {}

        public float getFriction() {
            return friction;
        }

        public void setFriction(float friction) {
            this.friction = friction;
        }

        public float getDensity() {
            return density;
        }

        public void setDensity(float density) {
            this.density = density;
        }

        public float getRestitution() {
            return restitution;
        }

        public void setRestitution(float restitution) {
            this.restitution = restitution;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }
}
