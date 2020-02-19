package com.huoergai.hcp.lesson36.mvvm;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

/**
 * D&T: 2020/2/19 10:46
 * DES:
 */
public class Binder {
    public static void bind(EditText et, StringWrapper dataWrapper) {
        dataWrapper.setListener(newVal -> {
            if (TextUtils.equals(newVal, et.getText())) {
                return;
            }
            Log.d("Binder", "memory data changed: " + newVal);
            et.setText(newVal);
        });
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.equals(s, dataWrapper.getData())) {
                    return;
                }
                Log.d("Binder", "UI data changed: " + s.toString());
                dataWrapper.setData(s.toString());
            }
        });
    }

}
