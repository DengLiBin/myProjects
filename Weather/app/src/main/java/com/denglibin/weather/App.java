package com.denglibin.weather;

import android.app.Application;

import com.thinkland.sdk.android.JuheSDKInitializer;

import cn.bmob.v3.Bmob;


/**
 * Created by DengLibin on 2016/4/3 0003.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JuheSDKInitializer.initialize(getApplicationContext());
        Bmob.initialize(getApplicationContext(),"a4ec42d3f58979895a51ca1a460c0cc6");
    }
}
