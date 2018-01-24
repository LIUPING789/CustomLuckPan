package com.knight.customluckpan;

import android.app.Application;

import com.knight.customluckpan.util.Logger;

/**
 * description: ${TODO}
 * autour: Knight
 * new date: 24/01/2018 on 15:41
 * e-mail: 37442216knight@gmail.com
 * update: 24/01/2018 on 15:41
 * version: v 1.0
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Logger.setDebug(false);
    }
}
