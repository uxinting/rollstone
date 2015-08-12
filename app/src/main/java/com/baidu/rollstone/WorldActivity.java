package com.baidu.rollstone;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.baidu.rollstone.engine.AbsEngine;
import com.baidu.rollstone.entity.Stone;
import com.baidu.rollstone.proxy.EngineProxy;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wuxinting on 15/8/11.
 */
public class WorldActivity extends Activity {

    private WorldView worldView;

    private EngineProxy proxy;
    private Stone stone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Show view
        worldView = new WorldView(this);
        addContentView(worldView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        //get wh of screen, init EngineProxy
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        EngineProxy.Screen screen = new EngineProxy.Screen();
        screen.setCx(new Vec2(1.0f, 0));
        screen.setCy(new Vec2(0, -1.0f));
        screen.setSize(new Vec2(display.getWidth(), display.getHeight()));
        screen.setOrigin(new Point(0, display.getHeight()));
        proxy = new EngineProxy(screen);

        //add bodies
        stone = new Stone();
        proxy.addRectBody(new Vec2(display.getWidth()/2, 10), new Vec2(display.getWidth()/2, 5), null);
        AbsEngine.ShapeConfig sc = new AbsEngine.ShapeConfig();
        sc.setRestitution(1.0f);
        sc.setDensity(0.3f);
        sc.setFriction(1.0f);
        sc.setData(stone);

        Body body = proxy.addCircleBody(new Vec2(50, 900), 20, sc);
        stone.setBody(body);
        body.applyForce(new Vec2(100.0f, 0), body.getWorldCenter());

        worldView.bind(proxy);
    }

    @Override
    protected void onResume() {

        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        proxy.step();
        Body body = stone.getBody();
        body.applyForce(new Vec2(10.0f, 0), body.getWorldCenter());
        return super.onTouchEvent(event);
    }
}
