package com.denglibin.secret.net;

import com.denglibin.secret.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by DengLibin on 2016/4/10 0010.
 */
public class Publish {
    public Publish(String phone_md5,String token,String msg,final SuccessCallback successCallback,final FailCallback failCallback) {
        new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);

                    switch (jsonObject.getInt(Config.STATUS_KEY)) {
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
        }, Config.ACTION_KEY,Config.ACTION_PUBLISH,
                Config.PHONE_MD5,phone_md5,
                Config.KEY_TOKEN,token,
                Config.MSG_ID,msg);
    }

    public static interface SuccessCallback{
        void onSuccess();
    }

    public static interface FailCallback{
        void onFail(int errorCode);
    }
}
