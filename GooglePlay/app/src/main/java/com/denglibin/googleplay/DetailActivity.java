package com.denglibin.googleplay;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.denglibin.googleplay.fragment.BaseFragment;

/**
 * Created by DengLibin on 2016/3/14 0014.
 */
public class DetailActivity extends BaseActivity {
    private View view;
    @Override
    protected void initView() {
        BaseFragment detailFragment=new BaseFragment() {
            @Override
            protected LoadResult load() {
                return null;
            }

            @Override
            protected View createSuccessView() {
                //返回一个View,并将该View赋值给view;
                return null;
            }
        };
        //setContentView(view);
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }
}
