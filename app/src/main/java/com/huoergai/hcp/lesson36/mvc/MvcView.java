package com.huoergai.hcp.lesson36.mvc;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;

import com.huoergai.hcp.R;

/**
 * D&T: 2020/2/18 16:03
 * DES:
 */
public class MvcView extends LinearLayoutCompat implements MvcActivity.IView {

    private TextView tv;

    public MvcView(Context context) {
        super(context);
    }

    public MvcView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tv = findViewById(R.id.l36_mvc_tv);
    }

    @Override
    public void show(String data) {
        tv.setText(data);
    }

}
