package com.huoergai.leak_canary;

import android.view.View;

import java.util.ArrayList;

public class LeakThread extends Thread {
    final ArrayList<View> leakViews = new ArrayList<>(5);

    @Override
    public void run() {
        while (true) {
            try {
                sleep(1000);
                System.out.println(System.currentTimeMillis() + " : " + leakViews.get(0));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
