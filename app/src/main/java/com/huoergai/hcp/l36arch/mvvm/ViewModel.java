package com.huoergai.hcp.l36arch.mvvm;

import androidx.appcompat.widget.AppCompatEditText;

import com.huoergai.hcp.l36arch.data.DataCenter;

public class ViewModel {
    private final StringModel data1 = new StringModel();
    private final StringModel data2 = new StringModel();

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
