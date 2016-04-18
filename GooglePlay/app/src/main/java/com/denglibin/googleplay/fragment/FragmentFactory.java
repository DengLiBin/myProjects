package com.denglibin.googleplay.fragment;


import android.support.v4.app.Fragment;//注意是导V4包的Fragment
import java.util.HashMap;
import java.util.Map;

/**
 * 工厂模式
 * Created by DengLibin on 2016/3/14 0014.
 */
public class FragmentFactory {

    //用缓存，把创建的fragment放入到集合中，下次要使用就直接从集合中取出来
    private static Map<Integer, Fragment> mFragments=new HashMap<Integer,Fragment>();
    public static Fragment createFragment(int position){
        Fragment fragment=null;
        fragment=mFragments.get(position);//首先从集合中取，集合中没有才new出来，这样不用每次都new
        if(fragment==null){
            if(position==0){
                fragment = new HomeFragment();
            }else if(position==1){
                fragment = new AppFragment();
            }else if(position==2){
                fragment = new GameFragment();
            }else if(position==3){
                fragment = new SubjectFragment();
            }else if(position==4){
                fragment = new CategorytFragment();
            }else if(position==5){
                fragment = new TopFragment();
            }
            if(fragment!=null){
                mFragments.put(position,fragment);
            }
        }

        return  fragment;
    }
}
