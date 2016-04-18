package com.denglibin.weather.utils;

import com.denglibin.weather.domain.AirQuality;
import com.denglibin.weather.domain.FutureThreeDay;
import com.denglibin.weather.domain.WeatherBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DengLibin on 2016/4/4 0004.
 */
public class ParseJson {
    String city,time,weather;
    String temperature;//温度范围
    String temp;//温度
    String humidity;//湿度
    String wind_direction;
    String wind_strength;
    String uv_index;//紫外线强度
    String dressing_index;//穿衣指数

    //解析当天的天气数据
    public  WeatherBean parse(String json){

        try{
            JSONObject jsonObject=new JSONObject(json);
            JSONObject result=jsonObject.getJSONObject("result");
            JSONObject sk=result.getJSONObject("sk");
            humidity=sk.getString("humidity");
            temp=sk.getString("temp");
            time=sk.getString("time");
            wind_direction=sk.getString("wind_direction");
            wind_strength=sk.getString("wind_strength");

            JSONObject today=result.getJSONObject("today");
            city=today.getString("city");
            weather=today.getString("weather");
            temperature=today.getString("temperature");
            uv_index=today.getString("uv_index");
            dressing_index=today.getString("dressing_index");
            WeatherBean bean=new WeatherBean(city,time,weather,temperature,temp,humidity,wind_direction,wind_strength,uv_index,dressing_index);
            return bean;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //解析未来三天的天气数据
    public List<FutureThreeDay> parseNextThreedDaysJson(String json){
        List<FutureThreeDay> futures=new ArrayList<FutureThreeDay>();
        try{
            JSONObject jsonObject=new JSONObject(json);
            JSONObject result=jsonObject.getJSONObject("result");
            JSONArray future=result.getJSONArray("future");
            for(int i=0;i<3;i++){//只解析三天
                JSONObject day=future.getJSONObject(i);
                String temp=day.getString("temperature");
                String[]temps=temp.split("~");
                String topTemp=temps[1];
                String bottomTemp=temps[0];
                String weather=day.getString("weather");
                futures.add(new FutureThreeDay(weather,topTemp,bottomTemp));
            }
            return futures;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //解析空气质量数据,解析当天的
    public AirQuality parseAir(String json){
        try{
            JSONObject jsonObject=new JSONObject(json);
            JSONArray results=jsonObject.getJSONArray("result");

            JSONObject citynow=results.getJSONObject(0).getJSONObject("citynow");
            String city=citynow.getString("city");
            String AQI=citynow.getString("AQI");
            String quality=citynow.getString("quality");
            return new AirQuality(city,AQI,quality);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


}
