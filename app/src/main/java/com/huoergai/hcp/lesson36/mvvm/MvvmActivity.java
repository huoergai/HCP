package com.huoergai.hcp.lesson36.mvvm;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.huoergai.hcp.R;

/**
 * D&T: 2020/2/18 15:49
 * DES:
 */
public class MvvmActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvvm);

        EditText et = findViewById(R.id.l36_mvvm_et);
        Button btnUpdate = findViewById(R.id.l36_btn_update);

        new ViewModel(et,btnUpdate).start();

    }
}
