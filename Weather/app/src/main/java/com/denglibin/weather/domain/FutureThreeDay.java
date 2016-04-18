package com.denglibin.weather.domain;

/**
 * Created by DengLibin on 2016/4/4 0004.
 */
public class FutureThreeDay {
    private String topTemp;
    private String bottomTemp;
    private String weather;

    public FutureThreeDay(String weather, String bottomTemp, String topTemp) {
        this.weather = weather;
        this.bottomTemp = bottomTemp;
        this.topTemp = topTemp;
    }

    public String getTopTemp() {
        return topTemp;
    }

    public String getBottomTemp() {
        return bottomTemp;
    }

    public String getWeather() {
        return weather;
    }
}
