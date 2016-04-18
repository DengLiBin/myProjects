package com.denglibin.weather.utils;

import java.net.URLEncoder;

/**
 * Created by DengLibin on 2016/4/4 0004.
 */
public class ToUtf8 {
    public static String getUtf8(String string){

       return URLEncoder.encode(string);
    }
}
