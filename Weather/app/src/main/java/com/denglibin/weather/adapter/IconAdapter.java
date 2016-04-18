package com.denglibin.weather.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.denglibin.weather.AddIconActivity;
import com.denglibin.weather.R;

/**
 * Created by DengLibin on 2016/4/6 0006.
 */
public class IconAdapter extends RecyclerView.Adapter {
    private AddIconActivity mAddIconActivity;
    private int[] icons;
    private int iconId;
    private SharedPreferences preferences;
    public IconAdapter(AddIconActivity addIconActivity){
        this.mAddIconActivity=addIconActivity;
        icons=new int[]{R.mipmap.icon1,R.mipmap.icon2,R.mipmap.icon3,R.mipmap.icon4,R.mipmap.icon5,
                R.mipmap.icon6,R.mipmap.icon7,R.mipmap.icon8,R.mipmap.icon9,R.mipmap.icon10,
                R.mipmap.icon11,R.mipmap.icon12,R.mipmap.icon13,R.mipmap.icon14,R.mipmap.icon15,
                R.mipmap.icon16,R.mipmap.icon17,R.mipmap.icon18,R.mipmap.icon19,R.mipmap.icon20,
                R.mipmap.icon21,R.mipmap.icon22,R.mipmap.icon23,R.mipmap.icon24,R.mipmap.icon25,
                R.mipmap.icon26,R.mipmap.icon27,R.mipmap.icon28,R.mipmap.icon29,R.mipmap.icon30,
                R.mipmap.icon31,R.mipmap.icon32,R.mipmap.icon33,R.mipmap.icon34};

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       //返回一个ViewHolder,ViewHolder里面加载了一个布局
        View itemView=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_icon, null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder mvh = (MyViewHolder) holder;//强转成自己的ViewHolder
        mvh.getIvIcon().setImageResource(icons[position]);
    }

    @Override
    public int getItemCount() {
        return icons.length;
    }

    //ViewHolder
     class MyViewHolder extends RecyclerView.ViewHolder{
        View root;
        ImageView ivIcon;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.root=itemView;
            ivIcon= (ImageView) root.findViewById(R.id.iv_icon_item);
            ivIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iconId=icons[getPosition()];
                    //Toast.makeText(mAddIconActivity,"头像的Id" +iconId, Toast.LENGTH_SHORT).show();
                    if(iconId!=0){
                        mAddIconActivity.icon_user.setImageResource(iconId);//将头像设置给userIcon
                        preferences=mAddIconActivity.getSharedPreferences("user",Context.MODE_PRIVATE);
                        String myIcon=preferences.getString("myIcon",null);
                        SharedPreferences.Editor editor=preferences.edit();
                        editor.putInt("iconId",iconId);
                        if(myIcon!=null){
                            editor.remove("myIcon");
                        }
                        editor.commit();


                    }
                }
            });
        }
        public ImageView getIvIcon(){
            return ivIcon;
        }
    }

}
