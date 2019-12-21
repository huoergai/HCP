package com.huoergai.hcp.lesson36.mvc;

import android.os.Bundle;

import com.huoergai.hcp.R;
import com.huoergai.hcp.base.BaseActivity;
import com.huoergai.hcp.lesson36.data.DataCenter;

import org.jetbrains.annotations.Nullable;

public class MvcActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arch);

        IView dataView = findViewById(R.id.arch_dv);
        String[] datas = DataCenter.load();
        dataView.show(datas[0], datas[1]);
    }

    interface IView {
        void show(String s1, String s2);
    }
}
