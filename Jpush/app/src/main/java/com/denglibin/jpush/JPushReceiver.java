package com.denglibin.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义的广播接收者，在清单文件中配置，监听JPush的动作
 * Created by DengLibin on 2016/4/1 0001.
 */
public class JPushReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("显示toast");
        if(intent.getAction().equals(JPushInterface.ACTION_MESSAGE_RECEIVED)){//表示收到了消息
            Bundle bundle=intent.getExtras();
            String title=bundle.getString(JPushInterface.EXTRA_TITLE);
            String message=bundle.getString(JPushInterface.EXTRA_MESSAGE);

            Toast.makeText(context,"标题："+title+"内容："+message,Toast.LENGTH_LONG).show();

        }
    }
}
