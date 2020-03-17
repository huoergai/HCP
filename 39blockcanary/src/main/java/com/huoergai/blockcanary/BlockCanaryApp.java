package com.huoergai.blockcanary;

import android.app.Application;

/**
 * 当前版本的 block canary 如果运行在 28/29 等高版本上会报一系列的权限拒绝错误
 * java.io.FileNotFoundException: /proc/stat: open failed: EACCES (Permission denied)
 */
public class BlockCanaryApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

       // BlockCanary.install(this, new BlockCanaryContext()).start();

        // logging.println(">>>>> Dispatching to " + msg.target + " " + msg.callback + ": " + msg.what);
        // msg.target.dispatchMessage(msg);
        // logging.println("<<<<< Finished to " + msg.target + " " + msg.callback);

        //  Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
        //      @Override
        //      public void doFrame(long frameTimeNanos) {
        //          Log.d("doFrame", "nanoTime=" + frameTimeNanos);
        //          Choreographer.getInstance().postFrameCallback(this);
        //      }
        //  });
    }
}
