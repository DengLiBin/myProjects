package com.denglibin.googleplay.fragment;


import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;

import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.denglibin.googleplay.DetailActivity;
import com.denglibin.googleplay.R;
import com.denglibin.googleplay.adapter.DefaultAdapter;
import com.denglibin.googleplay.domain.AppInfo;
import com.denglibin.googleplay.protocol.HomeProtocol;
import com.denglibin.googleplay.utils.BitmapHelper;
import com.denglibin.googleplay.utils.UiUtils;
import com.denglibin.googleplay.view.BaseListView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.PauseOnScrollListener;

import java.util.Formatter;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by DengLibin on 2016/3/5.
 */
public class HomeFragment extends BaseFragment {
    private List<AppInfo> datas;
    private BitmapUtils bitmapUtils;
    public static final int[] pic=new int[]{R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e};
    View headView;
    ViewPager viewPager;
    private Handler handler=new Handler(){
        public void handleMessage(android.os.Message msg){
            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            handler.sendEmptyMessageDelayed(0, 3000);
        }
    };
    //返回访问服务器的状态
    @Override
    protected LoadResult load(){
        HomeProtocol homeProtocol=new HomeProtocol();
        datas=homeProtocol.load(0);
        return super.checkData(datas);//调用父类的方法

    }
    //访问服务器成功显示的界面
    @Override
    protected View createSuccessView(){
        headView=View.inflate(UiUtils.getContext(),R.layout.top_viewpager,null);
        viewPager= (ViewPager) headView.findViewById(R.id.view_pager);
        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setCurrentItem(8000);//设置当前的item位置（设置中间值，保证可以向前滑）
        handler.sendEmptyMessageDelayed(0, 3000);//发送空消息，让handler执行handleMessage()方法
        BaseListView listView= new BaseListView(getActivity());
        listView.addHeaderView(headView);
        bitmapUtils= BitmapHelper.getBitmapUtils();
        listView.setAdapter(new HomeAdapter(datas,listView));

        //第二个参数 慢慢滑动的时候是否加载图片，false --加载  ，true--不加载
        //第三个参数 快速滑动是否加载图片，true--不加载
        listView.setOnScrollListener(new PauseOnScrollListener(bitmapUtils,false,true));


        return listView;

       /*
        System.out.println(datas);
        TextView view=new TextView(getActivity());
        view.setText("访问成功...");
        view.setTextSize(16f);
        return view;*/
    }

    public class HomeAdapter extends DefaultAdapter<AppInfo> {


        public HomeAdapter(List<AppInfo> appInfos,ListView listView) {
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

            bitmapUtils.configDefaultLoadingImage(R.drawable.ic_default);//图片加载中显示的图片
            bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_default);//图片加载失败显示的图片
            //bitmapUtils.display(holder.item_icon, HttpHelper.URL+"image?name="+iconUrl);没服务器访问不了，
            return view;
        }

        @Override
        public void onInnerItemClick(int position) {
            super.onInnerItemClick(position);
            //Toast.makeText(UiUtils.getContext(), "position:" + position, Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(UiUtils.getContext(), DetailActivity.class);
            startActivity(intent);//跳转到DetailActivity
        }
    }
    static class  ViewHolder{
        ImageView item_icon;
        TextView item_titel,item_size,item_bottom;
        RatingBar item_rating;
    }

    public class MyPagerAdapter extends PagerAdapter{
         LinkedList<ImageView>link=new LinkedList<ImageView>();//增删比较快
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
            ImageView view=(ImageView)object;
            link.add(view);//把移除的对象，添加到缓存集合中
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //return super.instantiateItem(container, position);
            ImageView imageView;
            if(link.size()>0){
                imageView=link.remove();//直接从集合中取(简单的优化)
            }else{
                imageView=new ImageView(UiUtils.getContext());
            }
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(pic[position % 5]);
            container.addView(imageView);
            return imageView;
        }

    }

}
