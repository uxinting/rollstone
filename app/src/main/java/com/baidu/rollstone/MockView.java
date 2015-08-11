package com.baidu.rollstone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import com.baidu.rollstone.proxy.EngineProxy;

import org.jbox2d.common.Vec2;

/**
 * Created by wuxinting on 15/8/10.
 */
public class MockView extends View {

    private Handler handler;
    private EngineProxy proxy;

    private Paint paint;

    public MockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public MockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        handler = new EngineHandler();
        paint = new Paint();
    }

    public void bind(EngineProxy proxy) {
        proxy.setHandler(handler);
        this.proxy = proxy;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.BLACK);

        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3);

        if (proxy != null) {
            proxy.draw(canvas, paint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private class EngineHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            invalidate();
        }
    }
}
