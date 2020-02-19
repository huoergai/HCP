package com.huoergai.hcp.lesson36.mvp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.huoergai.hcp.R;

/**
 * D&T: 2020/2/18 15:48
 * DES:
 */
public class MvpActivity extends AppCompatActivity implements L36Presenter.IView {

    private TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);

        tv = findViewById(R.id.l36_mvp_tv);

        new L36Presenter(this).start();
    }

    @Override
    public void show(String data) {
        tv.setText(data);
    }
}
