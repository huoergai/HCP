package com.huoergai.hcp.l29gradle;

import android.os.Bundle;

import com.huoergai.hcp.R;
import com.huoergai.hcp.base.BaseActivity;

import org.jetbrains.annotations.Nullable;

public class L29Activity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l29);

        Util.addBadge(this);

        /*
         * compile 有传递依赖，已废弃：A --> B ,B --> C,==> A --> C
         * implementation 没有传递依赖
         * api 有传递依赖
         */


    }
}
