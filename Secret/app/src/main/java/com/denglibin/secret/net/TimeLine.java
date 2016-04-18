package com.denglibin.secret.net;

import com.denglibin.secret.Config;
import com.denglibin.secret.domain.Msg;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 向服务器请求朋友圈的最新消息
 * 需要传给服务器的参数action,phone_md5,token,page,perpage(接口文档)
 * Created by DengLibin on 2016/4/9 0009.
 */
public class TimeLine {
    public TimeLine(String phone_md5,String token,int page,int perpage,
                    final  TimeLine.SuccessCallback successCallback,
                    final TimeLine.FailCallback failCallback){
        new NetConnection(Config.SERVER_URL, HttpMethod.POST,
                new NetConnection.SuccessCallback() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            JSONObject obj=new JSONObject(result);

                            switch (obj.getInt(Config.STATUS_KEY)){
                                case Config.RESULT_SUCCESS:
                                    if(successCallback!=null){
                                        List<Msg> list=new ArrayList<Msg>();
                                        JSONArray jsonArray=obj.getJSONArray(Config.KEY_ITEMS);
                                        for(int i=0;i<jsonArray.length();i++){
                                            JSONObject msgObj=jsonArray.getJSONObject(i);
                                            String message=msgObj.getString(Config.MSG);
                                            String msgId=msgObj.getString(Config.MSG_ID);
                                            String msg_phone_md5=msgObj.getString(Config.PHONE_MD5);
                                            list.add(new Msg(message,msgId,msg_phone_md5));
                                        }
                                        successCallback.OnSuccess(obj.getInt(Config.KEY_PAGE),obj.getInt(Config.KEY_PERPAGE),
                                               list);
                                    }
                                    break;
                                case Config.TOKEN_INVALID:
                                    if(failCallback!=null){
                                        failCallback.OnFail(Config.TOKEN_INVALID);
                                    }
                                    break;
                                default:
                                    if(failCallback!=null){
                                        failCallback.OnFail(Config.RESULT_FAIL);
                                    }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new NetConnection.FailCallback() {
                    @Override
                    public void onFail() {
                        if(failCallback!=null){
                            failCallback.OnFail(Config.RESULT_FAIL);
                        }
                    }
        },Config.ACTION_KEY,Config.TIMELINE,
                Config.PHONE_MD5,phone_md5,
                Config.KEY_TOKEN,token,
                Config.KEY_PAGE,page+"",
                Config.KEY_PERPAGE,perpage+"");//将int变成字符串
    }

    public interface SuccessCallback{
        void OnSuccess(int page ,int perpage ,List<Msg> list);
    }
    public  interface FailCallback{
        void OnFail(int errorcode);
    }
}
