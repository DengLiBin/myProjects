package com.denglibin.googleplay.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.denglibin.googleplay.R;
import com.denglibin.googleplay.adapter.DefaultAdapter;
import com.denglibin.googleplay.domain.AppInfo;
import com.denglibin.googleplay.protocol.AppProtocol;
import com.denglibin.googleplay.utils.BitmapHelper;
import com.denglibin.googleplay.utils.UiUtils;
import com.denglibin.googleplay.view.BaseListView;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by DengLibin on 2016/3/5.
 */
public class AppFragment extends BaseFragment {
    private List<AppInfo> datas;
    @Override
    protected LoadResult load(){
        AppProtocol appProtocol=new AppProtocol();
        datas=appProtocol.load(0);
        return super.checkData(datas);//父类的方法检查数据
    }
    @Override
    protected View createSuccessView(){
        ListView listView=new BaseListView(UiUtils.getContext());

        listView.setAdapter(new MyAdapter(datas,listView));
        return listView;
    }


    public class MyAdapter extends DefaultAdapter<AppInfo> {
        public MyAdapter(List<AppInfo> appInfos,ListView listView) {
        super(appInfos,listView);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder holder;
            if(convertView==null){
                view=View.inflate(UiUtils.getContext(), R.layout.item_app,null);
                holder=new ViewHolder();
                holder.item_icon= (ImageView) view.findViewById(R.id.item_icon);
                holder.item_titel= (TextView) view.findViewById(R.id.item_title);
                holder.item_size= (TextView) view.findViewById(R.id.item_size);
                holder.item_bottom= (TextView) view.findViewById(R.id.item_bottom);
                holder.item_rating= (RatingBar) view.findViewById(R.id.item_rating);
                view.setTag(holder);
            }else{
                view=convertView;
                holder= (ViewHolder) view.getTag();
            }
            AppInfo appInfo=datas.get(position);//父类的方法,datas是创建该对象时传入进来的
            holder.item_titel.setText(appInfo.getName());//应用程序名称
            String size = android.text.format.Formatter.formatFileSize(UiUtils.getContext(), appInfo.getSize());
            holder.item_size.setText(size);
            holder.item_bottom.setText(appInfo.getDes());
            float stars=appInfo.getStars();
            holder.item_rating.setRating(stars);
            String iconUrl=appInfo.getIconUrl();
            BitmapUtils bitmapUtils= BitmapHelper.getBitmapUtils();
            bitmapUtils.configDefaultLoadingImage(R.drawable.ic_default);//图片加载中显示的图片
            bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_default);//图片加载失败显示的图片
            //bitmapUtils.display(holder.item_icon, HttpHelper.URL+"image?name="+iconUrl);没服务器访问不了，
            System.out.println("调用了getView方法"+position);
            return view;
        }
}
static class  ViewHolder{
    ImageView item_icon;
    TextView item_titel,item_size,item_bottom;
    RatingBar item_rating;
}

}
