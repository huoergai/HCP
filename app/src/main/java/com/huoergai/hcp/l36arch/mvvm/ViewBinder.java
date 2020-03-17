package com.huoergai.hcp.l36arch.mvvm;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import timber.log.Timber;

public class ViewBinder {
    public static void bind(final EditText et, final StringModel data) {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.equals(s, data.getValue())) {
                    data.setValue(s.toString());
                    Timber.d("data change by view: %s", s.toString());
                }
            }
        });

        data.setListener(newValue -> {
            if (!TextUtils.equals(newValue, et.getText())) {
                et.setText(newValue);
                Timber.d("view change by data: %s", newValue);
            }
        });

    }
}
