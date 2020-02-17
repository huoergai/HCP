package com.huoergai.hcp.lesson37;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.huoergai.hcp.R;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * D&T: 2020/2/14 15:25
 * DES: 插件化
 */
public class L37Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l37);

        TextView tv = findViewById(R.id.l37_tv);

        loadDex();

        // Button btn = findViewById(R.id.l37_btn);

    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void loadDex() {
        File dexFile = new File(getCacheDir() + "/extraPlugin.apk");
        // copy dex from asset to cache director
        try (Source source = Okio.source(getAssets().open("37_plugin-debug.apk"));
             BufferedSink sink = Okio.buffer(Okio.sink(dexFile)
             )) {
            sink.writeAll(source);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ClassLoader loader = new DexClassLoader(dexFile.getPath(), getCacheDir().getPath(), null, null);
        Log.d("path log", "dexPath = " + dexFile.getPath());
        Log.d("path log", "cachePath = " + getCacheDir().getPath());

        try {
            // Class<?> aClass = Class.forName("com.huoergai.hcp.lesson37_38.TestUtil");
            Class aClass = loader.loadClass("com.huoergai.plugin.PluginUtil");
            Constructor[] constructors = aClass.getConstructors();
            Object obj = constructors[0].newInstance();

            Method reflectMsg = aClass.getDeclaredMethod("testMsg");
            reflectMsg.invoke(obj);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    private void loadText() {
        try {
            // Class<?> aClass = Class.forName("com.huoergai.hcp.lesson37_38.TestUtil");
            Class<?> aClass = Class.forName("com.huoergai.plugin.PluginUtil");
            Constructor<?>[] constructors = aClass.getConstructors();
            Object obj = constructors[0].newInstance();

            Method reflectMsg = aClass.getDeclaredMethod("testMsg");
            reflectMsg.invoke(obj);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取插件中的 resources 资源
     *
     * @param dexPath 插件路径
     */
    public AssetManager createAssetManager(String dexPath) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            Object obj = addAssetPath.invoke(assetManager, dexPath);
            return assetManager;
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

}
