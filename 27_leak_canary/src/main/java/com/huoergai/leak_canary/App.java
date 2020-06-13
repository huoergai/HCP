package com.huoergai.leak_canary;

import android.app.Application;
import android.util.Log;
import android.view.View;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;

import leakcanary.AppWatcher;

public class App extends Application {
    public static final ArrayList<View> leakViews = new ArrayList<>(6);

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("App", "is : " + Application.class.isAssignableFrom(App.class));
        AppWatcher.INSTANCE.getObjectWatcher().watch(this, "the app");

        ReferenceQueue<Object> q = new ReferenceQueue<>();
        PhantomReference<Object> pr = new PhantomReference<Object>(new LeakThread(), q);
    }
}
