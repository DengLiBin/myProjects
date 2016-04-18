package com.denglibin.secret.net;

import com.denglibin.secret.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by DengLibin on 2016/4/7 0007.
 */
public class GetCode {
    public  GetCode(String phone,final SuccessCallback successCallback,final FailCallback failCallback){
        new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
               try{
                   JSONObject jsonObject=new JSONObject(result);
                   switch (jsonObject.getInt(Config.STATUS_KEY)){
                       case Config.RESULT_SUCCESS:
                            //访问成功
                           if(successCallback!=null){
                               successCallback.onSuccess(result);
                           }
                           break;
                       default:
                           break;
                   }
               }catch (JSONException e){
                   e.printStackTrace();
                   if(failCallback!=null){
                       failCallback.onFail();
                   }
               }

            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {
                if(failCallback!=null){
                    failCallback.onFail();
                }
            }
        },Config.ACTION_KEY,Config.ACITON_GET_CODE,Config.PHONE_KEY,phone);

    }
    public static interface SuccessCallback{
        void onSuccess(String result);
    }
    public static interface FailCallback{
        void onFail();
    }
}
