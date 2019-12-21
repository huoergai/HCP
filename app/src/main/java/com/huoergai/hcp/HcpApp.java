package com.huoergai.hcp;

import android.app.Application;

import timber.log.Timber;

public class HcpApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
