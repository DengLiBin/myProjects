package com.denglibin.secret;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 封装字符串常量
 * Created by DengLibin on 2016/4/7 0007.
 */
public class Config {
    //public static final String SERVER_URL="http://demo.eoeschool.com/api/v1/nimings/io";
    public static final String SERVER_URL="http://192.168.1.105:8080/test/test.jsp";
    public static final String APP_ID="com.denglibin.secret";
    public static final String KEY_TOKEN="token";//客户端的一个标记
    public static final String CHARSET="utf-8";//字符编码格式
    public static final String ACTION_KEY="action";
    public static final String ACITON_GET_CODE="send_pass";
    public static final String ACITON_LOGIN="login";
    public static final String PHONE_MD5="phone_md5";
    public static final String PHONE_KEY="phone";
    public static final String UPLOAD_CONTACTS="upload_contacts";
    public static final String CODE="code";
    public static final String CONTACTS="contacts";
    public static final String CONTENT="content";
    public static final String ITEMS="items";
    public static final String STATUS_KEY="status";
    public static final String TIMELINE="timeline";
    public static final String KEY_PAGE="page";
    public static final String KEY_PERPAGE="perpage";
    public static final String KEY_ITEMS="items";
    public static final String MSG="msg";
    public static final String MSG_ID="msgId";
    public static final String GET_COMMENT="get_comment";
    public static final String ACTION_PUB_COMMENT="pub_comment";
    public static final String ACTION_PUBLISH="publish";
    public static final int RESULT_SUCCESS=1;
    public static final int RESULT_FAIL=0;
    public static final int TOKEN_INVALID=2;

    //获取标记
    public static String getCacheToken(Context context){
        return context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).getString(KEY_TOKEN,null);
    }

    //设置标记
    public static void setCacheToken(Context context,String token){
        SharedPreferences.Editor editor=context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).
                edit();
        editor.putString(KEY_TOKEN,token);
        editor.commit();
    }
    //获取号码
    public static String getCacheNumber(Context context){
        return context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).getString(PHONE_KEY,null);
    }

    //设置号码
    public static void setCacheNumber(Context context,String number){
        SharedPreferences.Editor editor=context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).
                edit();
        editor.putString(PHONE_KEY,number);
        editor.commit();
    }
}
