package com.denglibin.weather.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Calendar;

/**
 * 每隔一段时间通知MainActivity更新数据
 * Created by DengLibin on 2016/4/4 0004.
 */
public class UpdateDataService extends Service {
    private boolean flag;
    private long startTime;
    private long currentTime;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
       return  null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        flag=true;
        startTime=System.currentTimeMillis();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag) {
                    try {
                        Thread.sleep(60 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    currentTime=System.currentTimeMillis();
                    if(currentTime-startTime>=10*60*1000){
                        //发送广播
                        Intent intent=new Intent();
                        intent.putExtra("update", "update");
                        intent.setAction("com.denglibin.update");
                        sendBroadcast(intent);
                        startTime=currentTime;
                    }

                }
            }
        }).start();

    }

    @Override
    public boolean onUnbind(Intent intent) {
        flag=false;
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
