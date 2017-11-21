package com.baidu.rollstone;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.baidu.rollstone.engine.AbsEngine;
import com.baidu.rollstone.entity.Stone;
import com.baidu.rollstone.entity.StoneWorld;
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

    private Stone stone;
    private StoneWorld stoneWorld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //full screen
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Show view
//        worldView = new WorldView(this);
//        addContentView(worldView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        setContentView(R.layout.activity_world);
        worldView = (WorldView) findViewById(R.id.world);

        //get wh of screen, init EngineProxy
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        stoneWorld = new StoneWorld(display);
        stone = new Stone(20f);
        stoneWorld.setStone(stone);

        worldView.bind(stoneWorld);
    }

    @Override
    protected void onResume() {

//        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        }
        super.onResume();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                stone.applyForce(new Vec2(10000.0f, 0), stone.getBody().getWorldCenter());
//                stone.getBody().applyImpulse(new Vec2(10000.0f, 0), stone.getBody().getWorldCenter());
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:break;
        }
        return super.onTouchEvent(event);
    }
}
