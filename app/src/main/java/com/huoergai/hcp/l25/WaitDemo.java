package com.huoergai.hcp.l25;

/**
 * wait()：当前线程进入等待状态，交出执行权；
 * notify()/notifyAll()： 换新等待状态的线程，使之继续往下执行；
 * xxxThread.join(): 中断当前线程并进入等待状态，直至 xxxThread 执行完成；
 * Thread.yield(): 让渡出一次执行权给同级别的其它线程；
 * 1. wait(),notifyAll() 都是 Object 中的方法, 亦即 monitor 的方法, 由 monitor 通知线程进行休眠、苏醒；
 * 2. wait(),notifyAll() 都需要在 synchronized 修饰的方法中, 否则报运行时异常 IllegalMonitorStateException
 * 3.
 */
public class WaitDemo implements TestDemo {
    private String sharedString;

    private synchronized void initString() {
        sharedString = "wo ------- ow";
        // notifyAll();
    }

    private synchronized void printString() {
        // if (sharedString == null) {
        // 不能使用 if, 因为可能是别处调用了 notifyAll() 而被唤醒，但此时共享数据依然没有初始化；因此需要反复检查
        // while (sharedString == null) {
        //  wait();
        // }
        System.out.println("String: " + sharedString);
    }

    @Override
    public void runTest() {
        Thread thread2 = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                initString();
            }
        };
        thread2.start();

        Thread thread1 = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    thread2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Thread.yield();

                printString();
            }
        };
        thread1.start();


    }
}
