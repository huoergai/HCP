package com.huoergai.hcp;

import android.app.Application;

import com.huoergai.hcp.lesson38.L38Activity;

/**
 * D&T: 2020/2/15 13:31
 * DES:
 */
public class CourseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        L38Activity.applyHotfix(this);
    }
}
