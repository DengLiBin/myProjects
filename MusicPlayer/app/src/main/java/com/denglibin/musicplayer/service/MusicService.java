package com.denglibin.musicplayer.service;

import android.app.Service;
import android.content.Intent;
import android.drm.DrmErrorEvent;
import android.drm.DrmManagerClient;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.denglibin.musicplayer.config.Constants;
import com.denglibin.musicplayer.utils.MediaUtils;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by DengLibin on 2016/4/13 0013.
 */
public class MusicService extends Service implements MediaPlayer.OnErrorListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener,AudioManager.OnAudioFocusChangeListener {
    private Messenger messenger;
    private Timer timer;
    MediaPlayer mPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {//多次启动也只调用一次
        mPlayer = new MediaPlayer();
        mPlayer.setOnErrorListener(this);//设置资源的时候出错了
        mPlayer.setOnPreparedListener(this);//设置资源的时候出错了
        mPlayer.setOnCompletionListener(this);//设置资源的时候出错了
        super.onCreate();
    }

    //每次启动都为来到此方法
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String option = intent.getStringExtra("options");
        if ("play".equals(option)) {
            String path = intent.getStringExtra("path");
            messenger = (Messenger) intent.getExtras().get("messenger");
            play(path);//调用播放方法
        } else if ("pause".equals(option)) {
            pause();
        } else if ("continue".equals(option)) {
            continuePlay();
        } else if ("stop".equals(option)) {
            stopPlay();
        } else if ("progress".equals(option)) {
            int progress = Integer.parseInt(intent.getStringExtra("progress"));//默认值-1
            System.out.println("当前进度为:" + progress);
            seekPlay(progress);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    //销毁时调用该方法
    @Override
    public void onDestroy() {
        mPlayer.stop();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        MediaUtils.isPlaying = false;
        super.onDestroy();
    }
    /**-----------封装音乐播放常见的方法begin-------------**/
    /**
     * 开始播放
     */
    public void play(String path) {
        try {
            mPlayer.reset();
            mPlayer.setDataSource(path);//设置歌曲路径
            mPlayer.prepare();//准备，本地音乐使用同步准备就可以了
            mPlayer.start();//开始播放
            MediaUtils.isPlaying = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }/**-----------封装音乐播放常见的方法pause-------------**/
    /**
     * 暂停
     */
    public void pause() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.pause();
            MediaUtils.isPlaying = false;
        }
    }
/**-----------封装音乐播放常见的方法continuePlay-------------**/
    /**
     * 继续播放
     */
    public void continuePlay() {
        if (mPlayer != null) {
            mPlayer.start();
            MediaUtils.isPlaying = true;
        }
    }
    /**-----------封装音乐播放常见的方法seekTo-------------**/

    /**
     * 跳转到指定的进度播放
     */
    public void seekPlay(int progress) {
        if (mPlayer != null) {
            System.out.println("已经执行跳转到进度播放" + progress);
            mPlayer.seekTo(progress);
        }
    }

    /**-----------封装音乐播放常见的方法stop-------------**/

    /**
     * 停止
     */
    public void stopPlay() {
        if (mPlayer != null) {
            mPlayer.stop();
            MediaUtils.isPlaying = false;
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
        }
    }

    /**
     * -----------相关回调方法-------------
     **/
    @Override
    public void onCompletion(MediaPlayer mp) {
        try {
            //service发送消息.告诉activity,当前的歌曲播放完了
            Message msg = Message.obtain();
            msg.what = Constants.StatePlay.STATE_COMPLETED;
            //发送消息
            messenger.send(msg);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    @Override
    public void onPrepared(MediaPlayer mp) {
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    //1.准备好的时候.告诉activity,当前歌曲的总时长
                    int currentPosition = mPlayer.getCurrentPosition();
                    int totalDuration = mPlayer.getDuration();
                    Message msg = Message.obtain();
                    msg.what = Constants.StatePlay.STATE_PLAYING;
                    msg.arg1 = currentPosition;
                    msg.arg2 = totalDuration;

                    //发送消息
                    messenger.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1000);

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Toast.makeText(getApplicationContext(), "亲,资源有问题!", Toast.LENGTH_SHORT).show();
        return true;
    }

    /**---------------音频焦点处理相关的方法,例如播放音乐的时候来电话了,需要对音频焦点处理---------------**/
    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN://你已经得到了音频焦点。
                System.out.println("-------------AUDIOFOCUS_GAIN---------------");
                // resume playback
                mPlayer.start();
                mPlayer.setVolume(1.0f, 1.0f);
                break;
            case AudioManager.AUDIOFOCUS_LOSS://你已经失去了音频焦点很长时间了。你必须停止所有的音频播放
                System.out.println("-------------AUDIOFOCUS_LOSS---------------");
                // Lost focus for an unbounded amount of time: stop playback and release media player
                if (mPlayer.isPlaying())
                    mPlayer.stop();
                mPlayer.release();
                mPlayer = null;
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT://你暂时失去了音频焦点
                System.out.println("-------------AUDIOFOCUS_LOSS_TRANSIENT---------------");
                // Lost focus for a short time, but we have to stop
                // playback. We don't release the media player because playback
                // is likely to resume
                if (mPlayer.isPlaying())
                    mPlayer.pause();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK://你暂时失去了音频焦点，但你可以小声地继续播放音频（低音量）而不是完全扼杀音频。
                System.out.println("-------------AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK---------------");
                // Lost focus for a short time, but it's ok to keep playing
                // at an attenuated level
                if (mPlayer.isPlaying())
                    mPlayer.setVolume(0.1f, 0.1f);
                break;
        }

    }

}
