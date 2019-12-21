package com.huoergai.hcp.lesson36.mvvm;

public class StringModel {
    private String value;
    private OnChangeListener listener;

    public StringModel() {
    }

    public StringModel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        if (listener != null) {
            listener.onChange(value);
        }
    }

    public void setListener(OnChangeListener listener) {
        this.listener = listener;
    }

    interface OnChangeListener {
        void onChange(String newValue);
    }
}
