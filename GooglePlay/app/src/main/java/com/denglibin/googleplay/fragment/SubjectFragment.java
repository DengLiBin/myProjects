package com.denglibin.googleplay.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.denglibin.googleplay.R;
import com.denglibin.googleplay.adapter.DefaultAdapter;
import com.denglibin.googleplay.domain.AppInfo;
import com.denglibin.googleplay.domain.SubjectInfo;
import com.denglibin.googleplay.protocol.HomeProtocol;
import com.denglibin.googleplay.protocol.SubjectProtocol;
import com.denglibin.googleplay.utils.BitmapHelper;
import com.denglibin.googleplay.utils.UiUtils;
import com.denglibin.googleplay.view.BaseListView;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

import javax.security.auth.Subject;

/**
 * Created by DengLibin on 2016/3/5.
 */
public class SubjectFragment extends BaseFragment {
    private List<SubjectInfo> datas;
    @Override
    protected LoadResult load(){
        SubjectProtocol subjectProtocol=new SubjectProtocol();
        datas=subjectProtocol.load(0);
        return super.checkData(datas);//调用父类的方法
    }
    @Override
    protected View createSuccessView(){
        /*TextView view=new TextView(getActivity());
        view.setText("访问成功...");
        view.setTextSize(16f);*/
        BaseListView listView=new BaseListView(UiUtils.getContext());
        listView.setAdapter(new SubjectAdapter(datas,listView));
        return listView;
    }

    private class SubjectAdapter extends DefaultAdapter<SubjectInfo> {


        public SubjectAdapter(List<SubjectInfo> subjectInfos,ListView listView) {
            super(subjectInfos,listView);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            System.out.println("调用了getView方法........");
            ViewHolder holder;
            if(convertView==null){
                holder=new ViewHolder();
            }else{

                holder= (ViewHolder)convertView.getTag();//复用
            }

            SubjectInfo info=datas.get(position);//父类的方法
            holder.textView.setText(info.getDes());
            BitmapUtils bitmapUtils= BitmapHelper.getBitmapUtils();
            bitmapUtils.configDefaultLoadingImage(R.drawable.ic_default);//图片加载中显示的图片
            bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_default);//图片加载失败显示的图片
            //bitmapUtils.display(holder.item_icon, HttpHelper.URL+"image?name="+iconUrl);没服务器访问不了，
            return holder.getView();
        }
    }

    static class  ViewHolder{
        private ImageView image;
        private TextView textView;
        private View view;
        public ViewHolder(){
            view=View.inflate(UiUtils.getContext(), R.layout.item_subject,null);
            this.image= (ImageView) view.findViewById(R.id.item_icon);
            this.textView= (TextView) view.findViewById(R.id.item_txt);
            view.setTag(this);
        }
        public View getView() {
            return view;
        }


    }
}
