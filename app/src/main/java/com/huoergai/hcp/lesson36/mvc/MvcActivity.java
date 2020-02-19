package com.huoergai.hcp.lesson36.mvc;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.huoergai.hcp.R;
import com.huoergai.hcp.lesson36.DataModel;

/**
 * D&T: 2020/2/18 15:47
 * DES:
 */
public class MvcActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvc);

        IView mvcView = findViewById(R.id.l36_mvc_mvcView);

        String data = DataModel.load("mvc");

        mvcView.show(data);
    }

    interface IView {
        void show(String data);
    }
}
