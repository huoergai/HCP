package com.huoergai.hcp.l36arch.mvc;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.huoergai.hcp.R;

public class DataView extends ConstraintLayout implements MvcActivity.IView {
    private AppCompatEditText et1;
    private AppCompatEditText et2;
    private AppCompatButton btn;

    public DataView(Context context) {
        this(context, null);
    }

    public DataView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        et1 = findViewById(R.id.arch_et_1);
        et2 = findViewById(R.id.arch_et_2);
        btn = findViewById(R.id.arch_btn);
    }

    @Override
    public void show(String s1, String s2) {
        et1.setText(s1);
        et2.setText(s2);
    }
}
