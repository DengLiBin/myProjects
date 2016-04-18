package com.denglibin.weather.domain;

/**
 * Created by DengLibin on 2016/4/4 0004.
 */
public class AirQuality {
    private String city;
    private String AQI;
    private String quality;

    public AirQuality(String city, String AQI, String quality) {
        this.city = city;
        this.AQI = AQI;
        this.quality = quality;
    }

    public String getCity() {
        return city;
    }

    public String getAQI() {
        return AQI;
    }

    public String getQuality() {
        return quality;
    }
}
