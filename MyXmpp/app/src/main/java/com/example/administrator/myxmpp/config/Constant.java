package com.example.administrator.myxmpp.config;

import android.provider.BaseColumns;

import org.jivesoftware.smack.XMPPConnection;

/**
 * Created by DengLibin on 2016/4/17 0017.
 */
public class Constant {
    public static final String SERVER_URL = "192.168.1.103";
    public static final int SERVER_PORT = 5222;
    public static final String SERVER_NAME = "my_open_fire";
    public static XMPPConnection conn=null;
    public static final String TABLE_CONTACT="t_contacts";

    public class ContactTable implements BaseColumns {//默认会给我们添加一列 _id
        public static final String ACCTOUN="account";//账号
        public static final String NICKNAME="nickname";//昵称
        public static final String AVATAR="avatar";//头像
        public static final String PINYIN="pinyin";//账号拼音

    }
}