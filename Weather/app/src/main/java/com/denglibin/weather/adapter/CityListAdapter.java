package com.denglibin.weather.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.denglibin.weather.R;

import java.util.List;

/**
 * Created by DengLibin on 2016/4/5 0005.
 */
public class CityListAdapter extends BaseAdapter {
    private String[] citys;
    private LayoutInflater mInflater;

    public CityListAdapter(String[] citys, LayoutInflater mInflater) {
        this.citys = citys;
        this.mInflater = mInflater;
    }

    @Override
    public int getCount() {
        return citys.length;
    }

    @Override
    public String getItem(int position) {
        return citys[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        ViewHolder holder;
        if(convertView==null){
            rowView=mInflater.inflate(R.layout.item_city_list,null);
            holder=new ViewHolder();
            holder.tv_city= (TextView) rowView.findViewById(R.id.tv_city);
            rowView.setTag(holder);
        }else{
            rowView=convertView;
            holder= (ViewHolder) rowView.getTag();
        }
        String cityName=this.getItem(position);
        holder.tv_city.setText(cityName);
        return rowView;
    }
    final class ViewHolder{
        public TextView tv_city;
    }
}
