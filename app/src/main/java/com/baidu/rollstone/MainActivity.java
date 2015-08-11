package com.baidu.rollstone;

import android.graphics.Point;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;

import com.baidu.rollstone.engine.AbsEngine;
import com.baidu.rollstone.engine.Engine;
import com.baidu.rollstone.proxy.EngineProxy;

import org.jbox2d.collision.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button mock;
    private MockView mockView;
    private EngineProxy engine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mock = (Button) findViewById(R.id.mock);
        mockView = (MockView) findViewById(R.id.mockView);

        mockView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                int width = mockView.getMeasuredWidth();
                int height = mockView.getMeasuredHeight();

                if (engine == null) {
                    EngineProxy.Screen screen = new EngineProxy.Screen();
                    screen.setCx(new Vec2(1.0f, 0f));
                    screen.setCy(new Vec2(0f, -1.0f));
                    screen.setOrigin(new Point(0, height));
                    screen.setSize(new Vec2(width, height));

                    engine = new EngineProxy(screen);
                    AbsEngine.ShapeConfig sc = new AbsEngine.ShapeConfig(0.3f, 1.0f);
                    sc.setRestitution(0.6f);
                    engine.addRectBody(new Vec2(200, 50), new Vec2(500, 100), null);
//                    engine.addRectBody(new Vec2(20, 20), new Vec2(500, 1000), sc);
                    engine.addCircleBody(new Vec2(500, 1000), 10, sc);
                    bind(engine, mockView);
                }
                return true;
            }
        });

        mock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    private int steps = 0;

                    @Override
                    public void run() {
                        for(; steps < 500; steps++) {
                            engine.step();
                            try {
                                Thread.sleep(60);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });
    }

    private void bind(EngineProxy proxy, MockView view) {
        view.bind(proxy);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
