package com.baidu.rollstone.entity;

import android.graphics.Canvas;
import android.graphics.Color;

/**
 * Created by wuxinting on 15/8/13.
 */
public class Sky implements IDrawable {

    private DrawConfig drawConfig;

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.BLUE);
    }

    @Override
    public void setDrawConfig(DrawConfig config) {
        drawConfig = config;
    }

    public DrawConfig getDrawConfig() {
        return drawConfig;
    }
}
