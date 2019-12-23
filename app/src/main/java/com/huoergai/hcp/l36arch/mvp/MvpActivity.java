package com.huoergai.hcp.l36arch.mvp;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.huoergai.hcp.R;
import com.huoergai.hcp.base.BaseActivity;

import org.jetbrains.annotations.Nullable;

public class MvpActivity extends BaseActivity implements Presenter.IView {

    private AppCompatEditText et1;
    private AppCompatEditText et2;
    private AppCompatButton btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arch);

        et1 = findViewById(R.id.arch_et_1);
        et2 = findViewById(R.id.arch_et_2);
        btn = findViewById(R.id.arch_btn);

        new Presenter(this).load();
    }

    @Override
    public void show(String s1, String s2) {
        et1.setText(s1);
        et2.setText(s2);
    }
}
