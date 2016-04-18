package com.denglibin.googleplay.domain;

/**
 * Created by DengLibin on 2016/3/17 0017.
 */
public class SubjectInfo {
    private String des;
    private String url;

    public SubjectInfo(String des, String url) {
        this.des = des;
        this.url = url;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDes() {
        return des;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "SubjectInfo{" +
                "des='" + des + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
