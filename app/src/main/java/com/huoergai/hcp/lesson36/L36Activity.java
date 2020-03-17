package com.huoergai.hcp.lesson36;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.huoergai.hcp.R;
import com.huoergai.hcp.lesson36.mvc.MvcActivity;
import com.huoergai.hcp.lesson36.mvp.MvpActivity;
import com.huoergai.hcp.lesson36.mvvm.MvvmActivity;

/**
 * D&T: 2020/2/18 13:43
 * DES:
 */
public class L36Activity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l36);

        Button btnMvc = findViewById(R.id.btn_mvc);
        Button btnMvp = findViewById(R.id.btn_mvp);
        Button btnMvvm = findViewById(R.id.btn_mvvm);

        btnMvc.setOnClickListener(this);
        btnMvp.setOnClickListener(this);
        btnMvvm.setOnClickListener(this);
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
