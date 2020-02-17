package com.huoergai.hcp.lesson38;

import android.content.Context;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.huoergai.hcp.R;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.PathClassLoader;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * D&T: 2020/2/16 9:19
 * DES: 热修复
 */
public class L38Activity extends AppCompatActivity implements View.OnClickListener {

    private static String hotfixFileName = "DiffHotfix.dex";

    private EditText et;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l38);

        et = findViewById(R.id.l38_et);

        Button btnShow = findViewById(R.id.l38_btn_show);
        Button btnHotfix = findViewById(R.id.l38_btn_hotfix);
        Button btnRemove = findViewById(R.id.l38_btn_remove);
        Button btnKill = findViewById(R.id.l38_btn_kill_process);

        btnShow.setOnClickListener(this);
        btnHotfix.setOnClickListener(this);
        btnRemove.setOnClickListener(this);
        btnKill.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.l38_btn_show:
                et.setText(HotfixTest.getMsg());
                break;
            case R.id.l38_btn_hotfix:
                loadHotfix();
                break;
            case R.id.l38_btn_remove:
                File file = new File(getCacheDir() + "/" + hotfixFileName);
                if (file.exists()) {
                    file.delete();
                }
                break;
            case R.id.l38_btn_kill_process:
                Process.killProcess(Process.myPid());
                break;
        }
    }

    private void loadHotfix() {
        File hotfixFile = new File(getCacheDir() + "/" + hotfixFileName);
        // try (Source source = Okio.source(getAssets().open("38_hotfix-debug.apk"));
        try (Source source = Okio.source(getAssets().open(hotfixFileName));
             BufferedSink bufferedSink = Okio.buffer(Okio.sink(hotfixFile))) {
            bufferedSink.writeAll(source);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Toast.makeText(this, "hotfix loaded", Toast.LENGTH_SHORT).show();
    }

    public static void applyHotfix(Context context) {
        File hotfixFile = new File(context.getCacheDir() + "/" + hotfixFileName);
        if (!hotfixFile.exists()) {
            return;
        }
        ClassLoader loader = context.getClassLoader();
        Class<BaseDexClassLoader> baseDexClassLoaderClass = BaseDexClassLoader.class;
        try {
            Field pathListField = baseDexClassLoaderClass.getDeclaredField("pathList");
            pathListField.setAccessible(true);
            // getClassLoader().pathList
            Object pathListObj = pathListField.get(loader);

            Class pathListClass = pathListObj.getClass();
            Field dexElementsField = pathListClass.getDeclaredField("dexElements");
            dexElementsField.setAccessible(true);

            PathClassLoader pathClassLoader = new PathClassLoader(hotfixFile.getPath(), null);
            // PathClassLoader.pathList
            Object hotfixPathListObj = pathListField.get(pathClassLoader);
            // PathClassLoader.pathList.dexElements
            Object hotfixDexElementsObj = dexElementsField.get(hotfixPathListObj);

            // 方式1 全量替换 getClassLoader().pathList.dexElements = new PathClassLoader().pathList.dexElements
            // dexElementsField.set(pathListObj, hotfixDexElementsObj);

            // 方式2 差量添加：将更新的 dex 内容插入到 dexElements 数组的最前面。
            Object dexElementsObj = dexElementsField.get(pathListObj);
            int elementsLen = Array.getLength(dexElementsObj);
            int hotfixElementsLen = Array.getLength(hotfixDexElementsObj);
            Object concatDexElementsObj = Array.newInstance(dexElementsObj.getClass().getComponentType(), elementsLen + hotfixElementsLen);

            // copy hotfix dex elements
            for (int i = 0; i < hotfixElementsLen; i++) {
                Array.set(concatDexElementsObj, i, Array.get(hotfixDexElementsObj, i));
            }

            // copy original dex elements
            for (int i = 0; i < elementsLen; i++) {
                Array.set(concatDexElementsObj, hotfixElementsLen + i, Array.get(dexElementsObj, i));
            }

            // update new concat dex elements object
            dexElementsField.set(pathListObj, concatDexElementsObj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
