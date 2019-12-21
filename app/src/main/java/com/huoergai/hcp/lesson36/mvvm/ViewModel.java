package com.huoergai.hcp.lesson36.mvvm;

import androidx.appcompat.widget.AppCompatEditText;

import com.huoergai.hcp.lesson36.data.DataCenter;

public class ViewModel {
    private StringModel data1 = new StringModel();
    private StringModel data2 = new StringModel();

    public ViewModel(AppCompatEditText et1, AppCompatEditText et2) {
        ViewBinder.bind(et1, data1);
        ViewBinder.bind(et2, data2);
    }

    public void load() {
        String[] datas = DataCenter.load();
        data1.setValue(datas[0]);
        data2.setValue(datas[1]);
    }
}
