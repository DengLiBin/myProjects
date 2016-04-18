package com.example.administrator.myxmpp.utils;

import android.os.Handler;

/**
 * Created by DengLibin on 2016/4/17 0017.
 */
public class ThreadUtils {
    private static Handler mHandler=new Handler();
    /**子线程执行的task*/
    public static void runInThread(Runnable task){
        new Thread(task).start();
    }
    /**主线程里面的handler*/
    public static void runInUIThread(Runnable task){
        mHandler.post(task);
    }
}
