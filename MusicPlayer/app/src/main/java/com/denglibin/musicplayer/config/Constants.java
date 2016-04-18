package com.denglibin.musicplayer.config;

/**
 * 定义一些常量
 * Created by DengLibin on 2016/4/14 0014.
 */
public class Constants {
    public static final class StatePlay{
        public static final int STATE_STOP=1001;//停止播放状态
        public static final int STATE_PLAYING=1002;//正在播放状态
        public static final int STATE_PAUSE=1003;//暂停状态
        public static final int STATE_COMPLETED=1004;//播放完成
    }
    public static final class PlayModel{
        public static final int MODEL_NORMAL=2001;
        public static final int MODEL_REPEAT=2002;
        public static final int MODEL_SINGLE=2003;
        public static final int MODEL_RANDOM=2004;
    }

}
