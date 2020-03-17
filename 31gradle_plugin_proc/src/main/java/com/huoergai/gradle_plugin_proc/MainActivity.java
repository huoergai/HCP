package com.huoergai.gradle_plugin_proc;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 31 课 gradle 构建流程
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String baseUrl = BuildConfig.base_url;

    }
}
