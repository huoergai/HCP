package com.huoergai.blockcanary;

import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Handler mh = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TraceCompat.beginSection("customTTrace");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // traceViewCatch();

        // systrace 在关键节点打点,几乎不影响性能
        // catchTrace();

        // TraceCompat.endSection();
    }

    private void traceViewCatch() {
        // TraceView 开销较大,有误差
        Debug.startMethodTracing("customTrace");
        catchTrace();
        Debug.stopMethodTracing();
    }

    private void catchTrace() {
        StackTraceElement[] traceElements = Looper.getMainLooper().getThread().getStackTrace();
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : traceElements) {
            //  sb.append(element.toString()).append(BlockInfo.SEPARATOR);
        }
        Log.d("catchTrace", sb.toString());

        m1();
        m2();
        m3();
    }

    // @DebugLog
    public void onClick(View v) {
       /* try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        m1();
        m2();
        m3();
    }

    private void m3() {
        SystemClock.sleep(200);
    }

    // @DebugLog
    private void m2() {
        SystemClock.sleep(20);
    }

    // @DebugLog
    private void m1() {
        SystemClock.sleep(780);
    }
}
