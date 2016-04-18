package com.denglibin.musicplayer.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.denglibin.musicplayer.bean.Music;
import com.denglibin.musicplayer.config.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DengLibin on 2016/4/13 0013.
 */
public class MediaUtils {
    private static List<Music> songList=new ArrayList<Music>();
    public static int currentSongId=1;//正在播放的那首歌
    public static int currentDuration=0;//正在播放的那首歌的进度
    public static int currentTotal=0;//正在播放的那首歌的总进度
    public static boolean isPlaying=false;//是否正在播放,默认没播放
    public static int currentModel= Constants.PlayModel.MODEL_NORMAL;//当前播放的模式

    //加载手机里面的音乐
    public static List<Music> initSongList(Context context){
        songList.clear();
        Uri uri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;//拿到uri

        String[] projection={MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ARTIST,
        MediaStore.Audio.Media.DATA};
        Cursor cur=context.getContentResolver().query(uri,projection,null,null,null);

        while(cur.moveToNext()){
            String title=cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.TITLE));
            String artist=cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            String path=cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.DATA));
            Music music=new Music(title,artist,path);
            songList.add(music);
        }
        return songList;
    }

    public static String formatTime(int duration){
        String result="";
        int i=duration/1000;
        int min=i/60;
        int sec=i%60;
        if(min>9&&sec>9){
            result=min+":"+sec;
            return result;
        }else if(min<=9){
            if(sec>9){
                result="0"+min+":"+sec;
                return result;
            }else if(sec<=9){
                result="0"+min+":0"+sec;
                return result;
            }
        }

        return result;
    }
    //获取歌词文件，歌词文件和歌曲文件在同一个目录
    public static File getLrc(String path){
        File file;
        String filePath=path.replace(".mp3",".lrc");//与歌曲名相同的lrc文件
        file=new File(filePath);
        if(!file.exists()){
            filePath=path.replace(".mp3",".txt");//与歌曲名相同的txt文件
            file=new File(filePath);
            if(!file.exists()){
                return  null;
            }
        }
        return  file;


    }
}
