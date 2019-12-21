package com.huoergai.hcp.lesson36.mvvm;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.huoergai.hcp.R;
import com.huoergai.hcp.base.BaseActivity;

import org.jetbrains.annotations.Nullable;

public class MvvmActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arch);

        AppCompatEditText et1 = findViewById(R.id.arch_et_1);
        AppCompatEditText et2 = findViewById(R.id.arch_et_2);
        AppCompatButton btn = findViewById(R.id.arch_btn);

        new ViewModel(et1, et2).load();
    }
}
