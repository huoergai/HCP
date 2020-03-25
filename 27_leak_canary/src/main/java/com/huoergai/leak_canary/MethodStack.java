package com.huoergai.leak_canary;

import android.os.SystemClock;

public class MethodStack implements Runnable {
    private Object ref;

    public MethodStack(Object ref) {
        this.ref = ref;
    }

    static void startWithTarget(Object obj) {
        new Thread(new MethodStack(obj)).start();
    }

    @Override
    public void run() {
        // 成员变量复制给局部变量,成员变量再置为 null
        Object obj = this.ref;
        this.ref = null;
        while (true) {
            SystemClock.sleep(1000);
            System.out.println("MethodStack ref:" + this.ref);
        }
    }
}
