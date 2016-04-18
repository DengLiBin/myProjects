package com.denglibin.googleplay;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by DengLibin on 2016/3/14 0014.
 */
public class BaseActivity extends ActionBarActivity {
    //管理所有运行的Activity，如退出时销毁所有的Activity
    //一定要用静态,将所有的Activity添加到集合中,退出时,遍历集合，将Activity销毁。
    //另一种方式：注册一个广播接受者，指定要接收的广播内容，收到广播后就销毁（要退出时发送这个广播）。
    final  static List<BaseActivity> mActivityList=new LinkedList<BaseActivity>();//LinkedList增删比较快，
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //如果打开一个Activity还来不及添加到集合中又马上退出，会出现异常，用同步代码块解决，添加和移除都加同一把锁
        synchronized (mActivityList){
            mActivityList.add(this);//将所有的Activity添加到集合中
        }
        initData();
        initView();
        initActionBar();
    }

    protected void initData(){

    }

    protected void initView() {
    }

    protected void initActionBar(){

    }

    //销毁所有的Activity，如点击退出按钮
    public void killAll(){
        LinkedList<BaseActivity> copy;
        //复制出一个集合,其实是将集合中的对象的地址复制了一份，
        synchronized (mActivityList){
            copy=new LinkedList<BaseActivity>(mActivityList);
        }

        //finish（）方法会调用onDestroyed方法，而onDestroyed方法会调用集合的remove方法，
        //然而该集合在遍历的时候不能调用他的方法，所以可以复制一个集合，通过这个集合来拿到Activity，并finish()
        for(BaseActivity activity:copy){
            activity.finish();

        }
        //杀死当前的进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized(mActivityList){//添加移除用同一把锁
            mActivityList.remove(this);//Activity销毁的时候，将该Activity从集合中移除
        }

    }
}
