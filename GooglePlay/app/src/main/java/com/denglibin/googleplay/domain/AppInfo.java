package com.denglibin.googleplay.domain;

import java.util.List;

/**
 * Created by DengLibin on 2016/3/17 0017.
 */
public class AppInfo {
    private long id;
    private String name;
    private String packageName;
    private String iconUrl;
    private float stars;
    private long size;
    private String downloadUrl;
    private String des;
//-------------------detilProtocol额外用到的数据
    private String downloadNum;
    private String version;
    private String date;
    private String author;
    private List<String> screen;//app下载页预览图片地址（多张）
    private List<String> safeUrl;
    private List<String> safeDesUrl;

    public AppInfo(String downloadUrl, long id, String name, String packageName, String iconUrl, float stars, long size, String des, String downloadNum, String version, String date, String author, List<String> screen, List<String> safeUrl, List<String> safeDesUrl, List<Integer> safeDesColor) {
        this.downloadUrl = downloadUrl;
        this.id = id;
        this.name = name;
        this.packageName = packageName;
        this.iconUrl = iconUrl;
        this.stars = stars;
        this.size = size;
        this.des = des;
        this.downloadNum = downloadNum;
        this.version = version;
        this.date = date;
        this.author = author;
        this.screen = screen;
        this.safeUrl = safeUrl;
        this.safeDesUrl = safeDesUrl;
        this.safeDesColor = safeDesColor;
    }

    public String getDownloadNum() {
        return downloadNum;
    }

    public String getVersion() {
        return version;
    }

    public String getDate() {
        return date;
    }

    public String getAuthor() {
        return author;
    }

    public List<String> getScreen() {
        return screen;
    }

    public List<String> getSafeUrl() {
        return safeUrl;
    }

    public List<String> getSafeDesUrl() {
        return safeDesUrl;
    }

    public List<Integer> getSafeDesColor() {
        return safeDesColor;
    }

    private List<Integer> safeDesColor;

    public void setDownloadNum(String downloadNum) {
        this.downloadNum = downloadNum;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setScreen(List<String> screen) {
        this.screen = screen;
    }

    public void setSafeUrl(List<String> safeUrl) {
        this.safeUrl = safeUrl;
    }

    public void setSafeDesUrl(List<String> safeDesUrl) {
        this.safeDesUrl = safeDesUrl;
    }

    public void setSafeDesColor(List<Integer> safeDesColor) {
        this.safeDesColor = safeDesColor;
    }

    public AppInfo(long id, String name, String packageName, String iconUrl, float stars, long size, String downloadUrl, String des) {
        this.id = id;
        this.name = name;
        this.packageName = packageName;
        this.iconUrl = iconUrl;
        this.stars = stars;
        this.size = size;
        this.downloadUrl = downloadUrl;
        this.des = des;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public void setStarts(float starts) {
        this.stars = starts;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public float getStars() {
        return stars;
    }

    public long getSize() {
        return size;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public String getDes() {
        return des;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", packageName='" + packageName + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", starts=" + stars +
                ", size=" + size +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", des='" + des + '\'' +
                '}';
    }
}
