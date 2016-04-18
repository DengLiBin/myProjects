package com.denglibin.googleplay.utils;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * Created by DengLibin on 2016/3/15 0015.
 */
public class ViewUtils {
    //从父控件中移除子View
    public static void removeFromParent(View view){
        ViewParent viewParent= view.getParent();
        if(viewParent instanceof ViewGroup){
            ViewGroup group=(ViewGroup)viewParent;
            group.removeView(view);
        }
    }
}
