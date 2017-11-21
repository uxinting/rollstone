package com.baidu.rollstone.proxy;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Handler;
import com.baidu.rollstone.engine.AbsEngine;
import com.baidu.rollstone.engine.Engine;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.CircleShape;
import org.jbox2d.collision.PolygonShape;
import org.jbox2d.collision.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

/**
 * Created by wuxinting on 15/8/10.
 * For Engine and View
 */
public class EngineProxy {

    private Screen screen;

    private Engine engine;

    public EngineProxy(Screen screen) {
        this.screen = screen;

        AABB aabb = new AABB();
        aabb.lowerBound.set(toEngine(screen.getOrigin()));
        aabb.upperBound.set(screen.getSize());
        engine = new Engine(aabb);
    }

    public void draw(Canvas canvas, Paint paint) {
        Body body = engine.getBodyList();

        for (int i = 0; i < engine.getBodyCount(); i++) {
            drawBody(canvas, body, paint);
            body = body.getNext();
        }
    }


    private void drawBody(Canvas canvas, Body body, Paint paint) {
        Vec2 pos = body.getPosition();
        Point point = toScreen(pos);

        Shape shape = body.getShapeList();
        if (shape == null) return;

        paint.setColor(Color.WHITE);
        switch (shape.getType()) {
            case CIRCLE_SHAPE:
                canvas.drawCircle(point.x, point.y, ((CircleShape) shape).getRadius(), paint);
                paint.setColor(Color.RED);
                canvas.drawLine(point.x, point.y, (float) (point.x+Math.cos(body.getAngle())*10), (float) (point.y-Math.sin(body.getAngle())*10), paint);
                break;
            case POLYGON_SHAPE:
                PolygonShape pShape = (PolygonShape) shape;
                canvas.drawPath(vertices2Path(pos, pShape.getVertices()), paint);
                break;
            default:
                break;
        }
    }

    private Path vertices2Path(Vec2 pos, Vec2[] vecs) {
        Path path = new Path();

        Point point = toScreen(vecs[0].add(pos));
        path.moveTo(point.x, point.y);

        for (int i = 1; i < vecs.length; i++) {
            point = toScreen(vecs[i].add(pos));
            path.lineTo(point.x, point.y);
        }
        return path;
    }

    public void step() {
        for (int i = 0; i < 60; i++) {
            engine.step();
        }
    }

    public Body addRectBody(Vec2 size, Vec2 pos, AbsEngine.ShapeConfig config) {
        return engine.addRectBody(pos, size, config);
    }

    public Body addCircleBody(Vec2 pos, float radius, AbsEngine.ShapeConfig config) {
        return engine.addCircleBody(pos, radius, config);
    }

    public Engine getEngine() {
        return engine;
    }

    public Screen getScreen() {
        return screen;
    }

    //Convert Vector2d in world to Screen pixes
    private Point toScreen(Vec2 vec) {
        Point point = new Point();

        point.x = (int) ((vec.x - screen.getOrigin().x)*screen.getCx().x);
        point.y = (int) ((vec.y - screen.getOrigin().y)*screen.getCy().y);

        return point;
    }

    private Vec2 toEngine(Point point) {
        Vec2 vec = new Vec2();

        vec.x = point.x / screen.getCx().x + screen.getOrigin().x;
        vec.y = point.y / screen.getCy().y + screen.getOrigin().y;

        return vec;
    }

    /**
     * Screen Horizon
     */
    public static class Screen {

        private Vec2 cx;
        private Vec2 cy;
        private Point origin;
        private Vec2 size;

        /**
         * Construct
         * @param cx vector of x
         * @param cy vector of y
         * @param origin origin point of coordinate
         */
        public Screen(Vec2 cx, Vec2 cy, Point origin, Vec2 size) {
            this.cx = cx;
            this.cy = cy;
            this.origin = origin;
            this.size = size;
        }

        public Screen() {}

        public Vec2 getCx() {
            return cx;
        }

        public void setCx(Vec2 cx) {
            this.cx = cx;
        }

        public Vec2 getCy() {
            return cy;
        }

        public void setCy(Vec2 cy) {
            this.cy = cy;
        }

        public Point getOrigin() {
            return origin;
        }

        public void setOrigin(Point origin) {
            this.origin = origin;
        }

        public Vec2 getSize() {
            return size;
        }

        public void setSize(Vec2 size) {
            this.size = size;
        }
    }
}
