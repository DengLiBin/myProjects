package com.denglibin.secret.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.denglibin.secret.Config;
import com.denglibin.secret.R;
import com.denglibin.secret.atys.AtyMessage;
import com.denglibin.secret.atys.AtyTimeline;
import com.denglibin.secret.domain.Msg;

import java.util.List;

/**
 * Created by DengLibin on 2016/4/9 0009.
 */
public class MsglistAdpter extends RecyclerView.Adapter {
    private AtyTimeline atyTimeline;
    private List<Msg> list;
    private View view;
    public MsglistAdpter(AtyTimeline atyTimeline, List<Msg> list){
        this.atyTimeline=atyTimeline;
        this.list=list;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.timeline_item, null);
        System.out.println("执行了onCreateViewHolder方法");
        return new MyViewHolder(view);//返回一个ViewHolder,ViewHolder里面加载了一个布局
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        final Msg msg=list.get(position);
        ((MyViewHolder)holder).getTv_msg().setText(msg.getMsg());
        ((MyViewHolder)holder).getRoot().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(atyTimeline, AtyMessage.class);
                intent.putExtra(Config.PHONE_MD5,msg.getPhone_md5());
                intent.putExtra(Config.MSG_ID,msg.getMsgId());
                intent.putExtra(Config.KEY_TOKEN,atyTimeline.token);
                atyTimeline.startActivity(intent);
            }
        });
           System.out.println("执行了onBindViewHolder方法");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    //添加更多数据
    public void addData(List<Msg> data){
        list.addAll(data);
        notifyDataSetChanged();//刷新adpater
    }
    //清空数据
    public void clearData(){
        list.clear();
        notifyDataSetChanged();
    }
    static class MyViewHolder extends RecyclerView.ViewHolder{
        View root;
        TextView tv_msg;
        public MyViewHolder(View itemView) {
            super(itemView);
            root=itemView;
            tv_msg= (TextView) root.findViewById(R.id.tv_msg);

            System.out.println("执行了findViewById方法");
    }

        public TextView getTv_msg() {
            return tv_msg;
        }

        public View getRoot() {
            return root;
        }
    }
}
