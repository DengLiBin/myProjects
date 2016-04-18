package com.denglibin.googleplay.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.denglibin.googleplay.domain.AppInfo;
import com.denglibin.googleplay.utils.UiUtils;

import java.util.List;

/**
 * 本类即是一个BaseAdapter，又是一个监听器，在构造方法中ListView添加该监听器
 * Created by DengLibin on 2016/3/19 0019.
 */

public class DefaultAdapter<Data> extends BaseAdapter implements OnItemClickListener{
    protected static final int DEFALUT_VIEW=0;
    protected static final int MORE_VIEW=1;//两种item
    protected List<Data> datas;
    protected ListView lv;
    public DefaultAdapter(List<Data> datas,ListView lv){
        this.datas=datas;
        this.lv=lv;
        // 给ListView设置条目的点击事件
        lv.setOnItemClickListener(this);//一定不能少
    }
    public void setDatas(List<Data> datas) {
        this.datas = datas;
    }

    public List<Data> getDatas() {
        return datas;
    }


    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //当前listView有几种不同的条目类型
    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount()+1;//2种item
    }
    //根据位置判断当前条目是什么类型
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
      /*  设置不同的位置用什么类型的item
        if(position%4==1){
            return MORE_VIEW;
        }else{
            return DEFALUT_VIEW;
        }
        */
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      /*  根据当前位置判断是什么类型的item，然后返回相应的view
        switch (getItemViewType(position)){
            case MORE_VIEW:
                设置view
                break;
            case DEFALUT_VIEW:
                设置view
                break;
            default:
                break;
        }
        */
        return null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        position=position-lv.getHeaderViewsCount();// listView的位置是从headerView开始计算的，所以要去掉,修正
        onInnerItemClick(position);
    }
    /**在该方法去处理条目的点击事件,由子类实现*/
    public void onInnerItemClick(int position){

    }


}
