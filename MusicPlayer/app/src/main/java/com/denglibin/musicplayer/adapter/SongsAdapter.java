package com.denglibin.musicplayer.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.denglibin.musicplayer.R;
import com.denglibin.musicplayer.bean.Music;
import com.denglibin.musicplayer.utils.MediaUtils;

import java.util.List;

/**
 * Created by DengLibin on 2016/4/13 0013.
 */
public class SongsAdapter extends BaseAdapter{
    private Context context;
    private List<Music> list;
    public SongsAdapter(Context context, List<Music> list){
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        if (list!=null){
            return list.size();
        }
        return 0;
    }

    @Override
    public Music getItem(int position) {
        if(list!=null){
            return list.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /**--------视图初始化---------------**/
        ViewHolder holder=null;
        if(convertView==null){
            convertView=View.inflate(context, R.layout.item_song_list,null);
            holder=new ViewHolder();
            convertView.setTag(holder);

            holder.tv_title= (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_artist= (TextView) convertView.findViewById(R.id.tv_artist);
        }else{
            holder= (ViewHolder) convertView.getTag();//用VeiwHoder不用每次都findViewById(id)
        }
        /**------------拿到数据-----------**/
        Music music=getItem(position);
        /**----------------设置数据-----------**/


        holder.tv_title.setText(music.getTitle());
        holder.tv_title.setTag(position);//设置tag，方便反查，相当于给每一item的TextView设置了一个position的标记
        holder.tv_artist.setText(music.getArtist());



        if(MediaUtils.currentSongId==position){//这个地方，有if一定要有else
            holder.tv_title.setTextColor(context.getResources().getColor(R.color.colorSongTitle));
            //因为convertView的复用，所以滑出屏幕的item和刚滑入的item，颜色会是一样的，所以要重新置为白色
        }else{
            holder.tv_title.setTextColor(Color.WHITE);
        }
        return convertView;
    }

    class ViewHolder{

        TextView tv_title;
        TextView tv_artist;
    }
}
