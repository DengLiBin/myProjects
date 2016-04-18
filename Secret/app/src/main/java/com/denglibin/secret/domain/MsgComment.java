package com.denglibin.secret.domain;

/**
 * Created by DengLibin on 2016/4/10 0010.
 */
public class MsgComment {
    String content;
    String phone_md5;

    public MsgComment(String content, String phone_md5) {
        this.content = content;
        this.phone_md5 = phone_md5;
    }

    public String getContent() {
        return content;
    }

    public String getPhone_md5() {
        return phone_md5;
    }
}
