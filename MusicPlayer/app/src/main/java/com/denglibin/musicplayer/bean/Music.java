package com.denglibin.musicplayer.bean;

import java.io.File;

/**
 * Created by DengLibin on 2016/4/13 0013.
 */
public class Music  {
    private String title;
    private String artist;
    private String path;

    public Music(String title, String artist, String path) {
        this.title = title;
        this.artist = artist;
        this.path = path;

    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getPath() {
        return path;
    }


}
