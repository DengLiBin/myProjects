package com.denglibin.weather.utils;

import android.content.Context;

import com.denglibin.weather.R;

/**
 * Created by DengLibin on 2016/4/5 0005.
 */
public class CitysSichuan {
    public Context context;
    public CitysSichuan(Context context){
        this.context=context;
    }
    public String[] getCitys(){
        return  context.getResources().getStringArray(R.array.citys);
    }

}
