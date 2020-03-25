package com.huoergai.leak_canary;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.requestPermission(this);
        setContentView(R.layout.activity_main);
    }

    @SuppressWarnings({"AccessStaticViaInstance"})
    public void onClick(View view) {
        switch (view.getId()) {
            // 静态变量
            case R.id.static_variable:
                Util.leakViews.add(view);
                break;
            // 局部变量
            case R.id.stack:
                MethodStack.startWithTarget(view);
                break;
            // 线程对象
            case R.id.thread:
                LeakThread leakThread = new LeakThread();
                leakThread.leakViews.add(view);
                leakThread.start();
                break;
            // application
            case R.id.application:
                ((App) getApplication()).leakViews.add(view);
                break;
            // dump
            case R.id.dump:
                Util.dump(this);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity", "onDestory.....");
        // Util.gc();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        Log.d("MainActivity", "finalize......");
    }
}
