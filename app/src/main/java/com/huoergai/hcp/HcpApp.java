package com.huoergai.hcp;

import android.app.Application;
import android.content.Context;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.PathClassLoader;
import timber.log.Timber;

public class HcpApp extends Application {
    private File hotfixApk;
    private File hotfixDex;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        hotfixApk = new File(getCacheDir(), "/hotfix.apk");
        hotfixDex = new File(getCacheDir(), "/hotfix.dex");
        hotfixUpdate();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }

    private void hotfixUpdate() {
     /*   if (hotfixApk.exists()) {
            // 方式1 全量加载更新
            wholeUpdate();
            return;
        }*/

        if (hotfixDex.exists()) {
            // 方式2 增量加载更新
            partiallyUpdate();
        }
    }


    @SuppressWarnings({"rawtypes", "JavaReflectionMemberAccess"})
    private void partiallyUpdate() {
        ClassLoader classLoader = getClassLoader();
        Class<BaseDexClassLoader> baseDexClassLoaderClass = BaseDexClassLoader.class;
        try {
            // pathList
            Field pathListField = baseDexClassLoaderClass.getDeclaredField("pathList");
            pathListField.setAccessible(true);
            Object pathList = pathListField.get(classLoader);
            Class dexPathListClass = pathList.getClass();
            // dexElement
            Field dexElementsField = dexPathListClass.getDeclaredField("dexElements");
            dexElementsField.setAccessible(true);
            Object dexElements = dexElementsField.get(pathList);

            // load the new dex file
            PathClassLoader pathClassLoader = new PathClassLoader(hotfixDex.getPath(), null);
            Object newPathList = pathListField.get(pathClassLoader);
            Object newDexElements = dexElementsField.get(newPathList);

            // new combined dexElements
            int oldLen = Array.getLength(dexElements);
            int newLen = Array.getLength(newDexElements);
            Object combinedDexElements = Array.newInstance(dexElements.getClass().getComponentType(), newLen + oldLen);
            // first fill new combinedDexElements with newDexElements
            for (int i = 0; i < newLen; i++) {
                Array.set(combinedDexElements, i, Array.get(newDexElements, i));
            }
            // then fill the rest space with old dexElements
            for (int i = 0; i < oldLen; i++) {
                Array.set(combinedDexElements, newLen + i, Array.get(dexElements, i));
            }

            // update classLoader with new combinedDexElements
            dexElementsField.set(pathList, combinedDexElements);
            System.out.println("hotfix partially update finished...");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }


    }

    private void wholeUpdate() {
        ClassLoader classLoader = getClassLoader();
        Class<BaseDexClassLoader> baseDexClassLoaderClass = BaseDexClassLoader.class;
        try {
            // get pathList field
            Field pathListField = baseDexClassLoaderClass.getDeclaredField("pathList");
            pathListField.setAccessible(true);
            Object pathList = pathListField.get(classLoader);
            Class dexPathListClass = pathList.getClass();

            // get dexElements field
            Field dexElementsField = dexPathListClass.getDeclaredField("dexElements");
            dexElementsField.setAccessible(true);

            // get new dexElements from hotfix
            PathClassLoader pathClassLoader = new PathClassLoader(hotfixApk.getPath(), null);
            Object newPathList = pathListField.get(pathClassLoader);
            Object newDexElements = dexElementsField.get(newPathList);
            // replace dexElements with new value
            dexElementsField.set(pathList, newDexElements);
            System.out.println("hotfix wholly update finished...");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
