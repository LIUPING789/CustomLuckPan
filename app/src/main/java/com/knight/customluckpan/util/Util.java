package com.knight.customluckpan.util;

import android.content.Context;

/**
 * description: ${TODO}
 * autour: Knight
 * new date: 24/01/2018 on 15:44
 * e-mail: 37442216knight@gmail.com
 * update: 24/01/2018 on 15:44
 * version: v 1.0
 */
public class Util {

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static double change(double a) {
        return a * Math.PI / 180;
    }

    public static double changeAngle(double a) {
        return a * 180 / Math.PI;
    }

}
