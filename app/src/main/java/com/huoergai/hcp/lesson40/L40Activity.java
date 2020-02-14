package com.huoergai.hcp.lesson40;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.a40_lib.Binding;
import com.huoegai.annotation.BindView;
import com.huoergai.hcp.R;

/**
 * D&T: 2020/2/11 14:09
 * DES:
 */
public class L40Activity extends AppCompatActivity {
    @BindView(R.id.l40_tv)
    TextView tv;
    @BindView(R.id.l40_btn)
    AppCompatButton btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l40);
        // ButterKnife.bind(this);
        // 视图绑定
        Binding.bind(this);
        // L40ActivityBinding.binding(this);
        // ButterKnife.bind(this);
        tv.setText(R.string.app_name);
        // btn.setText(R.string.submit);
        btn.setText(R.string.submit);
    }

}
