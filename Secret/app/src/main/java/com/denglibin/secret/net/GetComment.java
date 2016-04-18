package com.denglibin.secret.net;

import com.denglibin.secret.Config;
import com.denglibin.secret.domain.MsgComment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取消息列表每条消息的评论
 * Created by DengLibin on 2016/4/10 0010.
 */
public class GetComment {
    public GetComment(String phone_md5,String token,int page,int perpage,String msgId,
                      final GetComment.SuccessCallback successCallback,
                      final GetComment.FailCallback failCallback){

        new NetConnection(Config.SERVER_URL, HttpMethod.POST,
                new NetConnection.SuccessCallback() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            JSONObject jsonObject=new JSONObject(result);
                            switch (jsonObject.getInt(Config.STATUS_KEY)){
                                case Config.RESULT_SUCCESS:
                                    List<MsgComment> comments=new ArrayList<MsgComment>();
                                    JSONArray jsonArray=jsonObject.getJSONArray(Config.ITEMS);
                                    for(int i=0;i<jsonArray.length();i++){
                                        JSONObject itemObj=jsonArray.getJSONObject(i);
                                        String content=itemObj.getString(Config.CONTENT);
                                        String phone_md5=itemObj.getString(Config.PHONE_MD5);
                                        comments.add(new MsgComment(content,phone_md5));
                                    }
                                    if(successCallback!=null){
                                        successCallback.onSuccess(jsonObject.getInt(Config.KEY_PAGE),jsonObject.getInt(Config.KEY_PERPAGE),
                                                jsonObject.getString(Config.MSG_ID),comments);
                                    }
                                    break;
                                default:
                                    if(failCallback!=null){
                                        failCallback.onFail(Config.RESULT_FAIL);
                                    }
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            if(failCallback!=null){
                                failCallback.onFail(Config.RESULT_FAIL);
                            }
                        }
                    }
                },
                new NetConnection.FailCallback() {
                    @Override
                    public void onFail() {
                        if(failCallback!=null){
                            failCallback.onFail(Config.RESULT_FAIL);
                        }
                    }
                },
                Config.ACTION_KEY, Config.GET_COMMENT, Config.PHONE_MD5, phone_md5,
                Config.KEY_TOKEN, token,Config.KEY_PAGE,page+"",Config.KEY_PERPAGE,perpage+"",
                Config.MSG_ID,msgId);
    }

    public static interface SuccessCallback{
        void onSuccess(int page,int perpage,String msgId,List<MsgComment> list);
    }
    public static interface FailCallback{
        void onFail(int errorcode);
    }
}
