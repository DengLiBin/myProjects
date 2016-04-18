package com.denglibin.weather.domain;

/**
 * Created by DengLibin on 2016/4/4 0004.
 */
public class WeatherBean {
    private String city;
    private String time;
    private String weather;
    private String temperature;//温度范围
    private String temp;//温度
    private String humidity;//湿度
    private String wind_direction;
    private String wind_strength;
    private String uv_index;//紫外线强度
    private String dressing_index;//穿衣指数
    public WeatherBean(String city, String time, String weather, String temperature, String temp, String humidity, String wind_direction, String wind_strength, String ux_index, String dressing_index) {
        this.city = city;
        this.time = time;
        this.weather = weather;
        this.temperature = temperature;
        this.temp = temp;
        this.humidity = humidity;
        this.wind_direction = wind_direction;
        this.wind_strength = wind_strength;
        this.uv_index = ux_index;
        this.dressing_index = dressing_index;
    }

    public String getCity() {
        return city;
    }

    public String getTime() {
        return time;
    }

    public String getWeather() {
        return weather;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getTemp() {
        return temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getWind_direction() {
        return wind_direction;
    }

    public String getWind_strength() {
        return wind_strength;
    }

    public String getUx_index() {
        return uv_index;
    }

    public String getDressing_index() {
        return dressing_index;
    }

    @Override
    public String toString() {
        return "WeatherBean{" +
                "city='" + city + '\'' +
                ", time='" + time + '\'' +
                ", weather='" + weather + '\'' +
                ", temperature='" + temperature + '\'' +
                ", temp='" + temp + '\'' +
                ", humidity='" + humidity + '\'' +
                ", wind_direction='" + wind_direction + '\'' +
                ", wind_strength='" + wind_strength + '\'' +
                ", ux_index='" + uv_index + '\'' +
                ", dressing_index='" + dressing_index + '\'' +
                '}';
    }
}
