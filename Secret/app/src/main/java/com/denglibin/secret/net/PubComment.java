package com.denglibin.secret.net;

import com.denglibin.secret.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 给指定的消息发表评论
 * Created by DengLibin on 2016/4/10 0010.
 */
public class PubComment {

    public PubComment(String phone_md5,String token,String content,
                      String msgId,final SuccessCallback successCallback,final FailCallback failCallback) {
        new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    switch (obj.getInt(Config.STATUS_KEY)) {
                        case Config.RESULT_SUCCESS:
                            if (successCallback!=null) {
                                successCallback.onSuccess();
                            }
                            break;
                        case Config.TOKEN_INVALID:
                            if (failCallback!=null) {
                                failCallback.onFail(Config.TOKEN_INVALID);
                            }
                            break;
                        default:
                            if (failCallback!=null) {
                                failCallback.onFail(Config.RESULT_FAIL);
                            }
                            break;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                    if (failCallback!=null) {
                        failCallback.onFail(Config.RESULT_FAIL);
                    }
                }
            }
        }, new NetConnection.FailCallback() {

            @Override
            public void onFail() {
                if (failCallback!=null) {
                    failCallback.onFail(Config.RESULT_FAIL);
                }
            }
        }, Config.ACTION_KEY,Config.ACTION_PUB_COMMENT,
                Config.KEY_TOKEN,token,
                Config.PHONE_MD5,phone_md5,
                Config.MSG_ID,msgId);
    }


    public static interface SuccessCallback{
        void onSuccess();
    }

    public static interface FailCallback{
        void onFail(int errorCode);
    }
}

