package com.baidu.rollstone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.baidu.rollstone.entity.StoneWorld;

/**
 * Created by wuxinting on 15/8/11.
 */
public class WorldView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder holder;
    private Paint paint;
    private DrawThread drawing;

    private StoneWorld stoneWorld;

    public WorldView(Context context) {
        super(context);
        initialize();
    }

    public WorldView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public WorldView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        holder = getHolder();
        paint = new Paint();

        holder.addCallback(this);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(3.0f);
        paint.setAntiAlias(true);
    }

    public boolean bind(StoneWorld stoneWorld) {
        this.stoneWorld = stoneWorld;
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        drawing = new DrawThread();
        drawing.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (drawing != null) {
            drawing.interrupt();
        }
    }

    private void draw() {
        Canvas canvas = holder.lockCanvas();
        if (canvas == null) return;

        canvas.drawColor(Color.BLUE);

        if (stoneWorld != null) {
            stoneWorld.draw(canvas);
        }

        holder.unlockCanvasAndPost(canvas);
    }

    private class DrawThread extends Thread {

        @Override
        public void run() {
            for (;;) {
                draw();
                if (stoneWorld != null) {
                    stoneWorld.step();
                }
            }
        }
    }
}
