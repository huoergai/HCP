package com.huoergai.hcp.lesson36.mvvm;

import android.text.TextUtils;

/**
 * D&T: 2020/2/19 10:49
 * DES:
 */
public class StringWrapper {
    private String data;
    private OnChangeListener listener;

    public StringWrapper() {
        this(null, null);
    }

    public StringWrapper(String data) {
        this(data, null);
    }

    public StringWrapper(String data, OnChangeListener listener) {
        this.data = data;
        this.listener = listener;
    }

    public void setData(String data) {
        if (TextUtils.equals(this.data, data)) {
            return;
        }
        this.data = data;
        if (listener != null) {
            listener.onChange(data);
        }
    }

    public String getData() {
        return data;
    }

    public void setListener(OnChangeListener changeListener) {
        this.listener = changeListener;
    }

    interface OnChangeListener {
        void onChange(String newVal);
    }
}
