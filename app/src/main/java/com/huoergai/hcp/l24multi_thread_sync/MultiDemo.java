package com.huoergai.hcp.l24multi_thread_sync;

public class MultiDemo implements ITest {
    private volatile int num = 0;

    private synchronized void updateNum(String threadName) {
        //private void updateNum(String threadName) {
        num++;
        System.out.println(threadName + " num=" + num);
    }

    @Override
    public void runTest() {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    updateNum("T1");
                    // System.out.println("t1 num=" + num);
                }
            }
        };
        Thread t2 = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    updateNum("T2");
                    // System.out.println("t2 num=" + num);
                }
            }
        };
        t1.start();
        t2.start();

        try {
            Thread.sleep(9000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t1.interrupt();
        t2.interrupt();
    }
}
