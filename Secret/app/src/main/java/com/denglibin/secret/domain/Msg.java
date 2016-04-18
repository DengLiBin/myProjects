package com.denglibin.secret.domain;

/**
 * Created by DengLibin on 2016/4/9 0009.
 */
public class Msg {
    private String msg,phone_md5,msgId;
    public Msg(String msg, String phone_md5, String msgId) {
        this.msg = msg;
        this.phone_md5 = phone_md5;
        this.msgId = msgId;
    }

    public String getMsg() {
        return msg;
    }

    public String getPhone_md5() {
        return phone_md5;
    }

    public String getMsgId() {
        return msgId;
    }


}
