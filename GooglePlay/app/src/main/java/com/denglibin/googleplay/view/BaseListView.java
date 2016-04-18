package com.denglibin.googleplay.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.denglibin.googleplay.R;

/**
 * Created by DengLibin on 2016/3/17 0017.
 */
public class BaseListView extends ListView {
    public BaseListView(Context context) {
        super(context);
        init();
    }

    public BaseListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    protected void init(){
        this.setSelector(R.drawable.nothing);//什么都没有
        this.setCacheColorHint(R.drawable.nothing);
    }
}
