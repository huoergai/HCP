package com.huoergai.hcp.l25;


import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

public class MainDemo {
    public static void main(String[] args) {
        //  new ThreadInteractionDemo().runTest();
        // new WaitDemo().runTest();
        new CustomizableThreadDemo().runTest();

        HandlerThread ht = new HandlerThread("");
        Handler handler = new Handler();
        Looper.prepare();
        Looper.loop();
        handler.sendMessage(Message.obtain());
        // handler.post()
    }
}
