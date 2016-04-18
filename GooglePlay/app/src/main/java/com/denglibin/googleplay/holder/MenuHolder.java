package com.denglibin.googleplay.holder;

import android.view.View;

import com.denglibin.googleplay.R;
import com.denglibin.googleplay.domain.UserInfo;
import com.denglibin.googleplay.utils.UiUtils;

/**
 * Created by DengLibin on 2016/3/20 0020.
 */
public class MenuHolder extends BaseHolder<UserInfo> {

    @Override
    public View initView() {
        View view=View.inflate(UiUtils.getContext(), R.layout.menu_holder,null);
        return view;
    }

    @Override
    public void refreshView(UserInfo data) {

    }
}
