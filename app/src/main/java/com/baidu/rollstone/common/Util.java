package com.baidu.rollstone.common;

import android.graphics.Path;
import android.graphics.Point;

import org.jbox2d.common.Vec2;

/**
 * Created by wuxinting on 15/8/13.
 */
public class Util {

    public static Path vtex2Path(Vec2 pos, Vec2[] vecs) {
        Path path = new Path();

        Vec2 vec = vecs[0].add(pos);
        path.moveTo(vec.x, vec.y);

        for (int i = 1; i < vecs.length; i++) {
            vec = vecs[i].add(pos);
            path.lineTo(vec.x, vec.y);
        }
        return path;
    }
}
