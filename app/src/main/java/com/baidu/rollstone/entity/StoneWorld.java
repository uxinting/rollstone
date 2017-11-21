package com.baidu.rollstone.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.view.Display;

import com.baidu.rollstone.engine.AbsEngine;
import com.baidu.rollstone.proxy.EngineProxy;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

/**
 * Created by wuxinting on 15/8/13.
 */
public class StoneWorld implements IDrawable {

    private Display display;
    private EngineProxy proxy;

    private Ground ground;
    private Sky sky;
    private Stone stone;

    private Bitmap bitmap;
    Matrix matrix = new Matrix();

    //from world to screen
    private final int rate = 30;

    public StoneWorld(Display display) {
        this.display = display;
        initialize();
    }

    private void initialize() {
        EngineProxy.Screen screen = new EngineProxy.Screen();
        screen.setCx(new Vec2(1.0f, 0));
        screen.setCy(new Vec2(0, -1.0f));
        screen.setSize(new Vec2(display.getWidth(), display.getHeight()));
        screen.setOrigin(new Point(0, display.getHeight()));
        proxy = new EngineProxy(screen);

        bitmap = Bitmap.createBitmap(display.getWidth(), display.getHeight(), Bitmap.Config.ARGB_8888);
        matrix.postScale(1, -1);

        createSky();
        createGround();
    }

    protected void createSky() {
        sky = new Sky();

        DrawConfig config = new DrawConfig();
        config.rate = rate;
        sky.setDrawConfig(config);
    }

    protected void createGround() {
        ground = new Ground();

        Vec2 size = new Vec2(display.getWidth()/2, 5);
        Vec2 pos = new Vec2(display.getWidth()/2, 115);
        AbsEngine.ShapeConfig config = new AbsEngine.ShapeConfig();
        config.setFriction(0.5f);
        ground.setBody(proxy.addRectBody(size, pos, config));

        DrawConfig dconfig = new DrawConfig();
        dconfig.rate = rate;
        ground.setDrawConfig(dconfig);
    }

    public void step() {
        proxy.step();
    }

    @Override
    public void draw(Canvas canvas) {
        if (canvas == null) return;

        Canvas canvas1 = new Canvas(bitmap);
        sky.draw(canvas1);
        ground.draw(canvas1);

        if (stone != null) {
            stone.draw(canvas1);
        }

        canvas.drawBitmap(Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true), 0, 0, null);
    }

    @Override
    public void setDrawConfig(DrawConfig config) {

    }

    public Ground getGround() {
        return ground;
    }

    public Sky getSky() {
        return sky;
    }

    public void setGround(Ground ground) {
        this.ground = ground;
    }

    public void setSky(Sky sky) {
        this.sky = sky;
    }

    public Stone getStone() {
        return stone;
    }

    public void setStone(Stone stone) {
        if (this.stone == null) {
            this.stone = stone;
            AbsEngine.ShapeConfig sc = new AbsEngine.ShapeConfig();
            sc.setRestitution(0.5f);
            sc.setDensity(0.1f);
            sc.setFriction(0.5f);
            sc.setData(this.stone);

            this.stone.setBody(proxy.addCircleBody(new Vec2(50, 210+stone.getRadius()*rate), stone.getRadius(), sc));

            DrawConfig config = new DrawConfig();
            config.rate = rate;
            this.stone.setDrawConfig(config);
        }
    }
}
