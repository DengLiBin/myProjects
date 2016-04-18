package com.example.administrator.myxmpp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.myxmpp.config.Constant;

import java.util.List;

/**
 * Created by Administrator on 2016/4/18.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> fragments;
    public ViewPagerAdapter(FragmentManager fm,List<Fragment> fragments) {
        super(fm);
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
