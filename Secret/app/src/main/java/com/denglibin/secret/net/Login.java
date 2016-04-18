package com.denglibin.secret.net;

import com.denglibin.secret.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 登录：根据接口文档设定要传给服务器的参数
 * Created by DengLibin on 2016/4/9 0009.
 */
public class Login {
    public Login(String phone_md5,String code,final Login.SuccessCallBack successCallBack,
                 final Login.FailCallBack failCallBack){

       new NetConnection(Config.SERVER_URL, HttpMethod.POST,
               new NetConnection.SuccessCallback() {
                   @Override
                   public void onSuccess(String result) {
                       try {
                           JSONObject jsonObject=new JSONObject(result);
                           switch (jsonObject.getInt(Config.STATUS_KEY)){
                               case 1://标识登录成功
                                   if(successCallBack!=null){
                                       successCallBack.onSuccess(jsonObject.getString(Config.KEY_TOKEN));//传入服务器返回的token标识
                                   }
                           }
                       } catch (JSONException e) {
                           e.printStackTrace();
                           if(failCallBack!=null){
                               failCallBack.onFail();
                           }
                       }
                   }
               },
               new NetConnection.FailCallback() {
                   @Override
                   public void onFail() {
                       if(failCallBack!=null){
                           failCallBack.onFail();
                       }
                   }
               },Config.ACTION_KEY,Config.ACITON_LOGIN,Config.PHONE_MD5,phone_md5,Config.CODE,code);
    }
    public static interface SuccessCallBack {
        void onSuccess(String token);
    }
    public static interface FailCallBack {
        void onFail();
    }
}
