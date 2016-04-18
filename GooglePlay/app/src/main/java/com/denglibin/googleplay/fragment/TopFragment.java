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
public class TopFragment extends BaseFragment {
    @Override
    protected LoadResult load(){
        return LoadResult.success;
    }
    @Override
    protected View createSuccessView(){
        TextView view=new TextView(getActivity());
        view.setText("访问成功...");
        view.setTextSize(16f);
        return view;
    }
}
