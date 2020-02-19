package com.huoergai.hcp.lesson36.mvvm;

import android.widget.Button;
import android.widget.EditText;

import com.huoergai.hcp.Utils;
import com.huoergai.hcp.lesson36.DataModel;

/**
 * D&T: 2020/2/19 10:19
 * DES:
 */
public class ViewModel {
    private StringWrapper data;

    public ViewModel(EditText et, Button btnUpdate) {
        data = new StringWrapper();
        Binder.bind(et, data);

        // initial Listener
        btnUpdate.setOnClickListener(v -> data.setData(Utils.randomString(12)));
    }

    public void start() {
        String data = DataModel.load("mvvm");
        this.data.setData(data);
    }

}
