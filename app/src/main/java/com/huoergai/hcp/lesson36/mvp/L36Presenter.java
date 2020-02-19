package com.huoergai.hcp.lesson36.mvp;

import com.huoergai.hcp.lesson36.DataModel;

/**
 * D&T: 2020/2/18 16:15
 * DES:
 */
public class L36Presenter {

    private IView iView;

    public L36Presenter(IView context) {
        this.iView = context;
    }

    public void start() {
        String data = DataModel.load("mvp");
        iView.show(data);
    }

    interface IView {
        void show(String data);
    }
}
