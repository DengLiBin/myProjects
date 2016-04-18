package com.denglibin.musicplayer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;

import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.denglibin.musicplayer.adapter.SongsAdapter;
import com.denglibin.musicplayer.bean.Music;
import com.denglibin.musicplayer.config.Constants;
import com.denglibin.musicplayer.service.MusicService;
import com.denglibin.musicplayer.utils.LrcUtil;
import com.denglibin.musicplayer.utils.MediaUtils;
import com.denglibin.musicplayer.view.LyricShow;
import com.denglibin.musicplayer.view.QuickIndexBar;
import com.denglibin.musicplayer.view.ScrollableViewGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView lrc_mini, current_duration, total_duration;
    private ScrollableViewGroup scrollVG;
    private int[] childViewId = new int[]{R.id.ib_top_play, R.id.ib_top_list, R.id.ib_top_lrc};
    private ImageButton ib_top_play,
            ib_top_list,
            ib_top_lrc,
            ib_bottom_model,
            ib_bottom_last,
            ib_bottom_play,
            ib_bottom_next,
            ib_bottom_update;
    private LrcUtil mLrcUtil;
    private ImageView iv_model, iv_last, iv_play, iv_next, iv_update;
    private List<Music> list;
    private ListView listViewSongs;
    private int media_state = Constants.StatePlay.STATE_STOP;//默认是停止状态
    private SeekBar seekBar;
    private LyricShow lrcView;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.StatePlay.STATE_PLAYING:
                    int currentDuration =msg.arg1;
                    int totalDuration =msg.arg2;
                    current_duration.setText(MediaUtils.formatTime(currentDuration));
                    total_duration.setText(MediaUtils.formatTime(totalDuration));

                    seekBar.setMax(totalDuration);
                    seekBar.setProgress(currentDuration);

                    if (mLrcUtil == null) {
                        mLrcUtil = new LrcUtil(MainActivity.this);
                    }
                    //序列化歌词
                    File f = MediaUtils.getLrc(list.get(MediaUtils.currentSongId).getPath());
                    mLrcUtil.ReadLRC(f);
                    //使用功能
                    mLrcUtil.RefreshLRC(currentDuration);//更新mini歌词
                    //更新滚动歌词

                    lrcView.SetTimeLrc(LrcUtil.lrclist);//设置歌词源
                    lrcView.SetNowPlayIndex(currentDuration);
                    break;
                case Constants.StatePlay.STATE_COMPLETED:

                        //根据设置的播放模式做不同的播放处理
                        if (MediaUtils.currentModel == Constants.PlayModel.MODEL_NORMAL) {//当前是顺序播放
                            setSongTitleColor(Color.WHITE);//当前完成的歌曲置为白色
                            if(MediaUtils.currentSongId==list.size()-1) {//如果当前完成播放的歌曲是最后一曲，
                                MediaUtils.currentSongId = -1;
                            }
                            MediaUtils.currentSongId++;
                            setSongTitleColor(Color.GREEN);//置为绿色
                            playMusic();
                        } else if (MediaUtils.currentModel == Constants.PlayModel.MODEL_RANDOM) {//当前是随机播放
                            Random random=new Random();
                            setSongTitleColor(Color.WHITE);//当前完成的歌曲置为白色
                            MediaUtils.currentSongId=random.nextInt(list.size());
                            setSongTitleColor(Color.GREEN);//置为绿色
                            playMusic();

                        } else if (MediaUtils.currentModel == Constants.PlayModel.MODEL_REPEAT) {//当前是重复播放
                            setSongTitleColor(Color.WHITE);//当前完成的歌曲置为白色
                            MediaUtils.currentSongId=0;
                            setSongTitleColor(Color.GREEN);//置为绿色
                            playMusic();
                        } else if (MediaUtils.currentModel == Constants.PlayModel.MODEL_SINGLE) {//当前是单曲循环播放
                            playMusic();
                        }



                    break;
                default:
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        initListener();


    }

    private void initView() {
        lrc_mini = (TextView) findViewById(R.id.tv_mini_lrc);
        current_duration = (TextView) findViewById(R.id.tv_current_duration);
        total_duration = (TextView) findViewById(R.id.tv_total_duration);
        scrollVG = (ScrollableViewGroup) findViewById(R.id.scrollVG);

        ib_top_play = (ImageButton) findViewById(R.id.ib_top_play);
        ib_top_list = (ImageButton) findViewById(R.id.ib_top_list);
        ib_top_lrc = (ImageButton) findViewById(R.id.ib_top_lrc);

        listViewSongs = (ListView) findViewById(R.id.lv_songs);
        ib_bottom_model = (ImageButton) findViewById(R.id.ib_bottom_model);
        ib_bottom_last = (ImageButton) findViewById(R.id.ib_bottom_last);
        ib_bottom_play = (ImageButton) findViewById(R.id.ib_bottom_play);
        ib_bottom_next = (ImageButton) findViewById(R.id.ib_bottom_next);
        ib_bottom_update = (ImageButton) findViewById(R.id.ib_bottom_update);

        iv_model = (ImageView) findViewById(R.id.iv_model);
        iv_last = (ImageView) findViewById(R.id.iv_last);
        iv_play = (ImageView) findViewById(R.id.iv_play);
        iv_next = (ImageView) findViewById(R.id.iv_next);
        iv_update = (ImageView) findViewById(R.id.iv_update);

        lrcView = (LyricShow) findViewById(R.id.tv_lrc);
        seekBar = (SeekBar) findViewById(R.id.seekBar);


        setTopBtnSelected(R.id.ib_top_play);//初始化时,就选中play页面
    }

    private void initData() {
        list = new ArrayList<Music>();
        list = MediaUtils.initSongList(this);

        listViewSongs.setAdapter(new SongsAdapter(this, list));
    }


    private void initListener() {
        ib_top_play.setOnClickListener(this);
        ib_top_list.setOnClickListener(this);
        ib_top_lrc.setOnClickListener(this);

        ib_bottom_model.setOnClickListener(this);
        ib_bottom_last.setOnClickListener(this);
        ib_bottom_play.setOnClickListener(this);
        ib_bottom_next.setOnClickListener(this);
        ib_bottom_update.setOnClickListener(this);
        scrollVG.setOnCurrentViewChangedListener(new ScrollableViewGroup.OnCurrentViewChangedListener() {
            @Override
            public void onCurrentViewChanged(View view, int currentview) {
                System.out.println("当前页面id:" + currentview);
                setTopBtnSelected(childViewId[currentview]);//当前view改变时同时改变topBtn的状态
            }
        });

        listViewSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //1、先找到当前正在播放的歌曲,前提是在adapter中为每一个item的textView设置了tag
                //TextView tv_title= (TextView) listViewSongs.findViewWithTag(MediaUtils.currentSongId);
                setSongTitleColor(Color.WHITE);//置为白色
                //2、找到点击的歌曲，颜色改为绿色，播放

                MediaUtils.currentSongId = position;
                //listViewSongs.setSelection(MediaUtils.currentSongId);//定位到当前播放的歌曲
                //TextView last_title= (TextView) listViewSongs.findViewWithTag(MediaUtils.currentSongId);
                setSongTitleColor(Color.GREEN);
                playMusic();
                //修改图标
                iv_play.setImageResource(R.mipmap.appwidget_pause);
                media_state = Constants.StatePlay.STATE_PLAYING;//置为正在播放状态
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {//进度改变
                seekBar.setProgress(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {//触摸到拖拽按钮

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {//停止拖拽
                //音乐播放也要更新到该进度
                Intent intent = new Intent(MainActivity.this, MusicService.class);
                intent.putExtra("progress", seekBar.getProgress() + "");
                intent.putExtra("messenger", new Messenger(handler));
                intent.putExtra("options", "progress");
                startService(intent);
            }
        });


    }

    private void setTopBtnSelected(int selectedId) {
        //先将所有控件置为未选中状态
        findViewById(R.id.ib_top_play).setSelected(false);
        findViewById(R.id.ib_top_list).setSelected(false);
        findViewById(R.id.ib_top_lrc).setSelected(false);
        //将目标控件设为选中状态
        findViewById(selectedId).setSelected(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_top_play:
                scrollVG.setCurrentView(0);//显示第0个子页面
                setTopBtnSelected(R.id.ib_top_play);
                break;
            case R.id.ib_top_list:
                scrollVG.setCurrentView(1);//显示第1个子页面
                setTopBtnSelected(R.id.ib_top_list);
                break;
            case R.id.ib_top_lrc:
                scrollVG.setCurrentView(2);//现实第2个子页面
                setTopBtnSelected(R.id.ib_top_lrc);
                break;
            case R.id.ib_bottom_model:
                if (MediaUtils.currentModel == Constants.PlayModel.MODEL_NORMAL) {//当前是顺序播放
                    MediaUtils.currentModel = Constants.PlayModel.MODEL_RANDOM;//切换为随机播放
                    //更新ui
                    iv_model.setImageResource(R.mipmap.icon_playmode_shuffle);
                    //提示
                    Toast.makeText(getApplicationContext(), "随机播放", Toast.LENGTH_SHORT).show();

                } else if (MediaUtils.currentModel == Constants.PlayModel.MODEL_RANDOM) {//当前是随机播放
                    MediaUtils.currentModel = Constants.PlayModel.MODEL_REPEAT;//切换为重复播放
                    //更新ui
                    iv_model.setImageResource(R.mipmap.icon_playmode_repeat);
                    //提示
                    Toast.makeText(getApplicationContext(), "重复播放", Toast.LENGTH_SHORT).show();

                } else if (MediaUtils.currentModel == Constants.PlayModel.MODEL_REPEAT) {//当前是重复播放
                    MediaUtils.currentModel = Constants.PlayModel.MODEL_SINGLE;//切换为单曲循环播放
                    //更新ui
                    iv_model.setImageResource(R.mipmap.icon_playmode_single);
                    //提示
                    Toast.makeText(getApplicationContext(), "单曲循环", Toast.LENGTH_SHORT).show();

                } else if (MediaUtils.currentModel == Constants.PlayModel.MODEL_SINGLE) {//当前是单曲循环播放
                    MediaUtils.currentModel = Constants.PlayModel.MODEL_NORMAL;//切换为顺序播放
                    //更新ui
                    iv_model.setImageResource(R.mipmap.icon_playmode_normal);
                    //提示
                    Toast.makeText(getApplicationContext(), "顺序播放", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ib_bottom_last:

                //1、先找到当前正在播放的歌曲,前提是在adapter中为每一个item的textView设置了tag
                //TextView tv_title= (TextView) listViewSongs.findViewWithTag(MediaUtils.currentSongId);
                setSongTitleColor(Color.WHITE);//置为白色
                //2、找到上一首歌曲，颜色改为绿色，播放
                if (MediaUtils.currentSongId == 0) {
                    MediaUtils.currentSongId = list.size();
                }
                MediaUtils.currentSongId--;
                listViewSongs.setSelection(MediaUtils.currentSongId - 3);//定位到当前播放的歌曲,减3是为了，把正在播放的歌曲放到屏幕中间
                //TextView last_title= (TextView) listViewSongs.findViewWithTag(MediaUtils.currentSongId);
                setSongTitleColor(Color.GREEN);
                playMusic();
                //修改图标
                iv_play.setImageResource(R.mipmap.appwidget_pause);
                media_state = Constants.StatePlay.STATE_PLAYING;//置为正在播放状态
                break;
            case R.id.ib_bottom_play://定义变量记录当前播放状态
                if (media_state == Constants.StatePlay.STATE_STOP) {//默认是停止状态
                    playMusic();
                    //修改图标
                    iv_play.setImageResource(R.mipmap.appwidget_pause);
                    media_state = Constants.StatePlay.STATE_PLAYING;//置为正在播放状态

                } else if (media_state == Constants.StatePlay.STATE_PLAYING) {//如果是播放状态
                    Intent intent = new Intent(MainActivity.this, MusicService.class);
                    intent.putExtra("options", "pause");
                    startService(intent);
                    //修改图标
                    iv_play.setImageResource(R.mipmap.img_playback_bt_play);
                    media_state = Constants.StatePlay.STATE_PAUSE;//置为暂停状态

                } else if (media_state == Constants.StatePlay.STATE_PAUSE) {
                    Intent intent = new Intent(MainActivity.this, MusicService.class);
                    intent.putExtra("options", "continue");
                    startService(intent);
                    //修改图标
                    iv_play.setImageResource(R.mipmap.appwidget_pause);
                    media_state = Constants.StatePlay.STATE_PLAYING;//置为正在播放状态
                }

                break;
            case R.id.ib_bottom_next:
                //1、先找到当前正在播放的歌曲,前提是在adapter中为每一个item的textView设置了tag
                //TextView tv_title= (TextView) listViewSongs.findViewWithTag(MediaUtils.currentSongId);
                setSongTitleColor(Color.WHITE);//置为白色
                //2、找到下一首歌曲，颜色改为绿色，播放
                if (MediaUtils.currentSongId == list.size() - 1) {
                    MediaUtils.currentSongId = -1;
                }
                MediaUtils.currentSongId++;
                listViewSongs.setSelection(MediaUtils.currentSongId - 3);//定位到当前播放的歌曲
                //TextView last_title= (TextView) listViewSongs.findViewWithTag(MediaUtils.currentSongId);
                setSongTitleColor(Color.GREEN);
                playMusic();
                //修改图标
                iv_play.setImageResource(R.mipmap.appwidget_pause);
                media_state = Constants.StatePlay.STATE_PLAYING;//置为正在播放状态
                break;
            case R.id.ib_bottom_update:
                break;
            default:
                break;
        }

    }

    private void setSongTitleColor(int color) {
        //如果listView的item很多，超出屏幕，这时候currentSongId对应的item不在屏幕中,就拿不到tv_title,
        TextView last_title = (TextView) listViewSongs.findViewWithTag(MediaUtils.currentSongId);
        if (last_title != null) {
            last_title.setTextColor(color);
        }

    }

    private void playMusic() {
        Intent intent = new Intent(MainActivity.this, MusicService.class);
        intent.putExtra("messenger", new Messenger(handler));
        intent.putExtra("options", "play");
        intent.putExtra("path", list.get(MediaUtils.currentSongId).getPath());//播放选中的歌曲
        startService(intent);
    }

    //设置mini歌词,在LrcUtil中调用
    public void setMiniLrc(String lrcString) {
        lrc_mini.setText(lrcString);
    }

    @Override
    public void onBackPressed() {
        // TODO
        //1.回到桌面-->跳到桌面-->开启一个桌面隐式意图
        // 直接开启手机桌面
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
        //2.显示notification
        showNotification();
    }

    private void showNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager manager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        NotificationCompat.Builder notifyBuilder=new NotificationCompat.Builder(this);
        notifyBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notifyBuilder.setContentTitle("标题");
        notifyBuilder.setContentText("内容");
        notifyBuilder.setContentIntent(contentIntent);
        manager.notify(100, notifyBuilder.build());
    }

}

/*

2.创建一个点击跳转到其它Activity的Notification。


点击跳转到指定Activity


实例化NotificationManager以获取系统服务

        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

     点击的意图ACTION是跳转到Intent
        Intent resultIntent = new Intent(this, MainActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        notifyBuilder = new NotificationCompat.Builder(this)

设置large icon

        .setLargeIcon(bitmap)

设置small icon

        .setSmallIcon(R.drawable.ic_launcher)
            */
/*设置title*//*

        .setContentTitle("通知")
            */
/*设置详细文本*//*

        .setContentText("Hello world")
             */
/*设置发出通知的时间为发出通知时的系统时间*//*

        .setWhen(System.currentTimeMillis())
             */
/*设置发出通知时在status bar进行提醒*//*

        .setTicker("来自问月的祝福")
            */
/*setOngoing(boolean)设为true,notification将无法通过左右滑动的方式清除 * 可用于添加常驻通知，必须调用cancle方法来清除 *//*

        .setOngoing(true)
             */
/*设置点击后通知消失*//*

        .setAutoCancel(true)
             */
/*设置通知数量的显示类似于QQ那种，用于同志的合并*//*

        .setNumber(2)
             */
/*点击跳转到MainActivity*//*

        .setContentIntent(pendingIntent);

        manager.notify(121, notifyBuilder.build());
        }

   */
