package com.huoergai.hcp.l38hotfix;

import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.huoergai.hcp.R;
import com.huoergai.hcp.base.BaseActivity;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;

import okio.BufferedSink;
import okio.Okio;
import okio.Source;
import timber.log.Timber;

@SuppressWarnings({"rawtypes", "unchecked"})
public class L38Activity extends BaseActivity implements View.OnClickListener {

    private EditText et;
    private File pluginFile;
    private File hotfixApk;
    private File hotfixDex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l38);

        pluginFile = new File(getCacheDir(), "/plugin.apk");
        hotfixApk = new File(getCacheDir(), "/hotfix.apk");
        hotfixDex = new File(getCacheDir(), "/hotfix.dex");

        et = findViewById(R.id.l38_et);

        Button btnShow = findViewById(R.id.l38_btn_show);
        // Button btnLoadPlugin = findViewById(R.id.l38_btn_plugin);
        Button btnHotfix = findViewById(R.id.l38_btn_hotfix);
        Button btnRemove = findViewById(R.id.l38_btn_remove);
        Button btnKill = findViewById(R.id.l38_btn_kill_process);

        btnShow.setOnClickListener(this);
        // btnLoadPlugin.setOnClickListener(this);
        btnHotfix.setOnClickListener(this);
        btnRemove.setOnClickListener(this);
        btnKill.setOnClickListener(this);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.l38_btn_show:
                StrUtil util = new StrUtil();
                et.setText(util.getString());
                break;
            /*case R.id.l38_btn_plugin:
                try (
                        Source source = Okio.source(getAssets().open("plugin.apk"));
                        BufferedSink sink = Okio.buffer(Okio.sink(pluginFile))
                ) {
                    sink.writeAll(source);
                    Timber.d("plugin load successfully");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                DexClassLoader pluginDexLoader = new DexClassLoader(pluginFile.getPath(), getCacheDir().getPath(), null, null);

                try {
                    Class strUtilClass = pluginDexLoader.loadClass("com.huoergai.hcp.l38hotfix.StrUtil");
                    Object strUtil = strUtilClass.newInstance();
                    Method getString = strUtilClass.getDeclaredMethod("getString");
                    String str = (String) getString.invoke(strUtil);
                    et.setText(str);
                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;*/
            case R.id.l38_btn_hotfix:
                // 方式1. load local hotfix apk file
                /*try (
                        Source source = Okio.source(getAssets().open("hotfix.apk"));
                        BufferedSink sink = Okio.buffer(Okio.sink(hotfixApk))
                ) {
                    sink.writeAll(source);
                    Timber.d("hotfix apk load successfully");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                */

                // 方式2. load local hotfix dex file
                try (
                        Source source = Okio.source(getAssets().open("hotfix.dex"));
                        BufferedSink sink = Okio.buffer(Okio.sink(hotfixDex))
                ) {
                    sink.writeAll(source);
                    Timber.d("hotfix dex load successfully");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // 方式3 load hotfix dex file online
                /*OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("https://api.hencoder.com/patch/upload/hotfix.dex").get().build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        try (
                                BufferedSink bufferSink = Okio.buffer(Okio.sink(hotfixDex));
                                Source source = Okio.source(response.body().byteStream())
                        ) {
                            bufferSink.writeAll(source);
                            Timber.d("hotfix dex load successfully");

                        }

                    }
                });
                */

                break;
            case R.id.l38_btn_remove:
                if (hotfixApk.exists()) {
                    hotfixApk.delete();
                    Timber.d("hotfix cache file deleted...");
                }

                if (hotfixDex.exists()) {
                    hotfixDex.delete();
                    Timber.d("hotfix cache file deleted...");
                }
                break;
            case R.id.l38_btn_kill_process:
                Process.killProcess(Process.myPid());
                break;
        }
    }
}
