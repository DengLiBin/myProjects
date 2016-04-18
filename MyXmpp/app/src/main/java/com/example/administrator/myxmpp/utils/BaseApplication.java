package com.example.administrator.myxmpp.utils;

import android.app.Application;
import android.content.Context;

import org.jivesoftware.smack.SmackAndroid;


/**
 * 代表当前应用程序,需要在清单文件中配置
 * Created by DengLibin on 2016/3/14 0014.
 */
public class BaseApplication extends Application {
    private static BaseApplication application;
    private  static int mainPid;
    @Override
    public void onCreate() {
        super.onCreate();
        application=this;
        mainPid=android.os.Process.myPid();
        SmackAndroid.init(this);//初始化
    }
    public static Context getApplication(){
        return application;
    }
    public static int getPid(){
        return mainPid;
    }
}
