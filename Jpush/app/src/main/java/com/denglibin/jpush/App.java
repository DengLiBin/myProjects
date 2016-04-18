package com.denglibin.jpush;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by DengLibin on 2016/4/1 0001.
 */
public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("调用了jpush初始化方法");
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
