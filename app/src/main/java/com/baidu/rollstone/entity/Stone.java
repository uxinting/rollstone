package com.baidu.rollstone.entity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

/**
 * Created by wuxinting on 15/8/11.
 */
public class Stone implements IDrawable {

    //if hp == 0, stone crush
    private float hp;
    private float radius;

    private Body body;

    private DrawConfig drawConfig;
    private Paint paint;

    //m
    public Stone(float radius) {
        hp = 100.0f;
        this.radius = radius;
        paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3.0f);
    }

    public void applyForce(Vec2 force, Vec2 point) {
        body.applyForce(force, point);
    }

    public void clearForce() {
        body.m_force.setZero();
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public void draw(Canvas canvas) {
        Vec2 pos = body.getPosition();
        canvas.drawCircle(pos.x, pos.y, getRadius(), paint);
        System.out.println("V: " + body.getLinearVelocity().x );
    }

    @Override
    public void setDrawConfig(DrawConfig config) {
        drawConfig = config;
    }

    public DrawConfig getDrawConfig() {
        return drawConfig;
    }
}
