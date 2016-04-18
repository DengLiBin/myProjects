package com.denglibin.googleplay.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by DengLibin on 2016/3/14 0014.
 */
public class UiUtils {

    //获取到字符数组
    public static String[] getStringArray(int tabNames){
       return getResource().getStringArray(tabNames);
    }
    public static Resources getResource(){
        return BaseApplication.getApplication().getResources();
    }

    public static Context getContext(){
        return BaseApplication.getApplication();
    }
    /** dip转换px */
    public static int dip2px(int dip) {
        final float scale = getResource().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /** pxz转换dip */

    public static int px2dip(int px) {
        final float scale = getResource().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

}
