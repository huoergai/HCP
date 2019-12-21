package com.huoergai.hcp.lesson37;

import android.os.Bundle;

import com.huoergai.hcp.R;
import com.huoergai.hcp.base.BaseActivity;

import org.jetbrains.annotations.Nullable;

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
 * 组件化、插件化(反射)
 *
 * dex: delvik executable
 *
 * dex
 * odex: optimized dex
 * oat: optimized android file type
 *
 * AOT: ahead-of-time compilation
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class L37Activity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l37);

        // 方式1
        /*try {
            Class utilClass = Util.class;
            Object util = utilClass.newInstance();
            Method helloMethod = utilClass.getDeclaredMethod("hello");
            helloMethod.setAccessible(true);
            helloMethod.invoke(util);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        */

        // 方式2 类的可用性限制大于其内部成员的可用性限制
       /* try {
            // Class utilClass = Util.class;
            Class utilClass = Class.forName("com.huoergai.hcp.lesson37.sub.Util");
            Constructor utilConstructor = utilClass.getDeclaredConstructors()[0];
            utilConstructor.setAccessible(true);
            // Object util = utilClass.newInstance();
            Object util = utilConstructor.newInstance();
            Method helloMethod = utilClass.getDeclaredMethod("hello");
            helloMethod.setAccessible(true);
            helloMethod.invoke(util);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        */

        // 方式3
        File pluginApk = new File(getCacheDir() + "/plugin001.apk");
        try (
                Source source = Okio.source(getAssets().open("37pluginable-debug.apk"));
                BufferedSink sink = Okio.buffer(Okio.sink(pluginApk))
        ) {
            sink.writeAll(source);
        } catch (IOException e) {
            e.printStackTrace();
        }


        DexClassLoader dexClassLoader = new DexClassLoader(pluginApk.getPath(), getCacheDir().getPath(), null, null);
        try {
            Class pluginClass = dexClassLoader.loadClass("com.example.a37pluginable.RemotePlugin");
            Constructor constructor = pluginClass.getDeclaredConstructors()[0];
            Object remotePlugin = constructor.newInstance();
            Method helloMethod = pluginClass.getDeclaredMethod("hello");
            helloMethod.invoke(remotePlugin);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

    }
}
