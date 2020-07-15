package com.huoergai.hcp.l36arch.mvp;

import com.huoergai.hcp.l36arch.data.DataCenter;

public class Presenter {
    private final IView iView;

    public Presenter(IView iView) {
        this.iView = iView;
    }

    public void load() {
        String[] datas = DataCenter.load();
        iView.show(datas[0], datas[1]);
    }

    interface IView {
        void show(String s1, String s2);
    }
}
