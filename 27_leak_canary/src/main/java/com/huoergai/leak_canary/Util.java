package com.huoergai.leak_canary;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Debug;
import android.os.SystemClock;
import android.view.View;

import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Util {

    public static final ArrayList<View> leakViews = new ArrayList<>(6);

    public static void requestPermission(Activity activity) {
        int checkRet = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (PackageManager.PERMISSION_GRANTED != checkRet) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,}, 1);
        }
    }

    public static void gc() {
        new Thread() {
            @Override
            public void run() {
                System.out.println("start gc...");
                Runtime.getRuntime().gc();
                SystemClock.sleep(100L);
                System.out.println("end gc...");
            }
        }.start();
    }

    public static void dump(Context context) {
        new Thread() {
            @Override
            public void run() {
                File file = new File("" +"");
                try {
                    Debug.dumpHprofData(file.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
