package com.huoergai.hcp.l25;

public class CustomizableThreadDemo implements TestDemo {
    private CustomizableThread thread = new CustomizableThread();

    @Override
    public void runTest() {
        thread.start();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

       /* thread.setTask(new Runnable() {
            @Override
            public void run() {
                System.out.println("coming....");
            }
        });*/
        thread.quit();
    }

    class CustomizableThread extends Thread {
        private Runnable task;
        private volatile boolean quit;

        synchronized void setTask(Runnable task) {
            this.task = task;
        }

        void quit() {
            this.quit = true;
        }

        @Override
        public void run() {
            while (!quit) {
                synchronized (this) {
                    if (task != null) {
                        task.run();
                        task = null;
                    }
                }
            }
        }
    }
}
