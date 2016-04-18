package com.denglibin.googleplay.Manager;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by DengLibin on 2016/3/16 0016.
 */
public class ThreadManager {
    private ThreadPoolProxy longPool;
    private ThreadPoolProxy localPool;
    //单例模式
    private ThreadManager(){

    }
    private static ThreadManager instance=new ThreadManager();
    public static  ThreadManager getInstance(){
        return instance;
    }

    //联网比较耗时，用一个线程池
    //读取本地文件也单独用一个线程池
    //开多少个线程效率高：cpu核数*2+1

    /**
     * 联网线程池
     */
    public ThreadPoolProxy createLongPool(){
        if(longPool==null){
            longPool=new ThreadPoolProxy(5,5,5000L);//5个线程
        }
        return longPool;
    }
     /**
     * 读取本地线程池
     */
    public ThreadPoolProxy createLocalPool(){
        if(localPool==null){
            localPool=new ThreadPoolProxy(3,3,5000L);//3个线程
        }
        return localPool;
    }




    public class ThreadPoolProxy{

        private ThreadPoolExecutor pool;//线程池
        private int corePoolSize;//线程池管理线程的数量
        private int maxmumPoolSize; //如果排队满了，额外开的线程数
        private long time; //如果线程池没有执行的任务，存活多久

        public ThreadPoolProxy(int corePoolSize,int maxmumPoolSize,long time){
            this.corePoolSize=corePoolSize;
            this.maxmumPoolSize=maxmumPoolSize;
            this.time=time;
        }

        public void execute(Runnable runnable){
            if(pool==null){
                //创建线程池
                /*
                参数1：线程池里面管理多少个线程
                参数2：如果排队满了，额外开的线程数
                参数3：如果线程池没有执行的任务，存活多久
                参数4：时间单位
                参数5：如果线程池里管理的线程都已经用了，剩下的任务临时存到LinkedBlockingQuene对象中排列
                 */
                pool=new ThreadPoolExecutor(corePoolSize,maxmumPoolSize,time, TimeUnit.MILLISECONDS,
                        new LinkedBlockingDeque<Runnable>(10));
            }
            pool.execute(runnable);//调用线程池执行异步任务
        }

        public void cancel (Runnable runnable){
            if(pool!=null&&!pool.isShutdown()&&!pool.isTerminated()){
                pool.remove(runnable);//取消异步任务
            }
        }
    }
}
