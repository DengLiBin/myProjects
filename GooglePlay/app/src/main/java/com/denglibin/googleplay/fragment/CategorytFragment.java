package com.denglibin.googleplay.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by DengLibin on 2016/3/5.
 */
public class CategorytFragment extends BaseFragment {
    //返回一个访问服务器的状态
    @Override
    protected LoadResult load(){
        return LoadResult.empty;
    }
    //访问服务器成功后显示的界面
    @Override
    protected View createSuccessView(){
        TextView view=new TextView(getActivity());
        view.setText("访问成功...");
        view.setTextSize(16f);
        return view;
    }
}
