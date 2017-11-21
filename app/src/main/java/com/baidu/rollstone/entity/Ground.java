package com.baidu.rollstone.entity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import com.baidu.rollstone.common.Util;

import org.jbox2d.collision.PolygonShape;
import org.jbox2d.collision.Shape;
import org.jbox2d.collision.ShapeType;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuxinting on 15/8/13.
 */
public class Ground implements IDrawable {

    private List<Obstacle> obstacles;
    private Body body;

    private DrawConfig drawConfig;
    private Paint paint;

    public Ground() {
        obstacles = new ArrayList<>();
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3.0f);
    }

    public void addObstacle(Obstacle obstacle) {
        obstacles.add(obstacle);
    }

    @Override
    public void draw(Canvas canvas) {

        canvas.drawPath(getPath(), paint);

        for (Obstacle obstacle : obstacles) {
            obstacle.draw(canvas);
        }
    }

    @Override
    public void setDrawConfig(DrawConfig config) {
        drawConfig = config;
        for (Obstacle obstacle : obstacles) {
            obstacle.setDrawConfig(config);
        }
    }

    public Body getBody() {
        return body;
    }

    public Vec2 getPosition() {
        return body.getPosition();
    }

    public Path getPath() {
        PolygonShape shape = (PolygonShape) body.getShapeList();
        if (shape != null) {
            return Util.vtex2Path(getPosition(), shape.getVertices());
        }
        return null;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public DrawConfig getDrawConfig() {
        return drawConfig;
    }
}
