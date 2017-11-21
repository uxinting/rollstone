package com.baidu.rollstone.entity;

import android.graphics.Canvas;

/**
 * Created by wuxinting on 15/8/13.
 */
public interface IDrawable {

    public void draw(Canvas canvas);

    public void setDrawConfig(DrawConfig config);

    public static class DrawConfig {
        public static float rate;
    }
}
