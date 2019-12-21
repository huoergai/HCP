package com.huoergai.hcp.lesson36;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;

import com.huoergai.hcp.R;
import com.huoergai.hcp.base.BaseActivity;
import com.huoergai.hcp.lesson36.mvc.MvcActivity;
import com.huoergai.hcp.lesson36.mvp.MvpActivity;
import com.huoergai.hcp.lesson36.mvvm.MvvmActivity;

import org.jetbrains.annotations.Nullable;

public class L36Activity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l36);

        AppCompatButton btn1 = findViewById(R.id.btn_mvc);
        AppCompatButton btn2 = findViewById(R.id.btn_mvp);
        AppCompatButton btn3 = findViewById(R.id.btn_mvvm);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_mvc:
                startActivity(new Intent(this, MvcActivity.class));
                break;
            case R.id.btn_mvp:
                startActivity(new Intent(this, MvpActivity.class));
                break;
            case R.id.btn_mvvm:
                startActivity(new Intent(this, MvvmActivity.class));
                break;
        }
    }
}
