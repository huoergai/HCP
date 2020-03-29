package com.huoergai.hcp.l25;

import android.os.SystemClock;

/**
 * 线程间通信和Android 多线程
 */
public class ThreadInteractionDemo implements TestDemo {

    @Override
    public void runTest() {
        // 子线程
        Thread thread = new Thread() {
            @Override
            public void run() {
                // for (int i = 0; i < 1_000_000; i++) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // 外界调用了 interrupt() 方法，请求中断，遂进行收尾工作并退出
                    return;
                }
                // SystemClock.sleep(100);
                /*if (isInterrupted()) {
                    // 收尾工作
                    return;
                }*/
                // System.out.println("number: " + i);
                System.out.println("number: ");
                // }
            }
        };
        thread.start();

        // 主线程休眠
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // 调用一个休眠中的的线程的 interrupt() 方法则会异常
            e.printStackTrace();
        }
        // 在主线程，调用子线程停止
        // 方式1 及时有效且粗暴，容易导致不可测的结果
        // thread.stop();

        // 方式2 此处仅作标记，表示希望线程结束，在其它地方用 isInterrupted() 检测该标记
        // 再根据具体情况执行相应操作
        thread.interrupt();
    }
}
