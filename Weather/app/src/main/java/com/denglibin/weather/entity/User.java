package com.denglibin.weather.entity;

import cn.bmob.v3.BmobUser;

/**
 * Created by DengLibin on 2016/4/5 0005.
 */
public class User extends BmobUser {
    private String info;
    public String getInfo(){
        return info;
    }
    public void setInfo(String info){
        this.info=info;
    }
}
