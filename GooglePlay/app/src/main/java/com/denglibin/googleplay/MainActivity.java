package com.denglibin.googleplay;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.SearchView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.denglibin.googleplay.fragment.AppFragment;
import com.denglibin.googleplay.fragment.BaseFragment;
import com.denglibin.googleplay.fragment.FragmentFactory;
import com.denglibin.googleplay.fragment.HomeFragment;
import com.denglibin.googleplay.holder.MenuHolder;
import com.denglibin.googleplay.utils.UiUtils;

public class MainActivity extends BaseActivity implements android.support.v7.widget.SearchView.OnQueryTextListener {
    private ActionBar actionBar;
    private ViewPager viewPager;
    private FrameLayout frameLayout;
    private PagerTabStrip pagerTabStrip;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;//实现了DrawerListener这个监听器
    private String[] tab_names;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        viewPager= (ViewPager) findViewById(R.id.vp_pager);
        frameLayout= (FrameLayout) findViewById(R.id.fl);
        frameLayout.addView(new MenuHolder().initView());//添加布局
        viewPager.setAdapter(new MainAdapter(getSupportFragmentManager()));//添加适配器

        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                //BaseFragment currentFragment= (BaseFragment) FragmentFactory.createFragment(position);
                //currentFragment.showPager();
            }
        });
        pagerTabStrip= (PagerTabStrip) findViewById(R.id.pager_tab_strip);//拿到viewpager的标签
        pagerTabStrip.setTabIndicatorColor(R.color.indicatorcolor);//设置标签下划线颜色

        drawerLayout= (DrawerLayout) findViewById(R.id.drawer);
        //就是一个DrawerLayou的监听器
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.open_drawer,R.string.close_drawer){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Toast.makeText(getApplicationContext(), "抽屉关闭了", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Toast.makeText(getApplicationContext(), "抽屉打开了", Toast.LENGTH_SHORT).show();
            }
        };
        //让开关和actionBar建立关系
        drawerLayout.setDrawerListener(drawerToggle);//设置监听器
        drawerToggle.syncState();
    }

    @Override
    protected void initActionBar() {
        actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);//显示左边按钮
        //actionBar.setHomeButtonEnabled(true);

    }

    @Override
    protected void initData() {
        //拿到标签名数组
       tab_names= UiUtils.getStringArray(R.array.tab_names);//在Strings.xml中配置
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //如果运行的环境（部署到什么版本的手机）大于3.0
        if(Build.VERSION.SDK_INT>11){
            //拿到id为action_search的item的view，在menu.xml文件有有设置
            SearchView searchView= (SearchView) menu.findItem(R.id.action_search).getActionView();
            searchView.setOnQueryTextListener(this);//设置文本变化的监听器
        }
        return true;
    }
    //处理ActionBar菜单条目的点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            Toast.makeText(getApplicationContext(),"搜索",Toast.LENGTH_SHORT).show();
        }
        //让drawerToggle去处理(控制侧边栏的打开和关闭)选中的item
        return drawerToggle.onOptionsItemSelected(item)|super.onOptionsItemSelected(item);
    }

    //搜索框文本发生变化时调用
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }
    //提交时调用
    @Override
    public boolean onQueryTextChange(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * viewpager的适配器
     */
    private class  MainAdapter extends FragmentStatePagerAdapter{

        public MainAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentFactory.createFragment(position);
        }
        //共有多少个标签
        @Override
        public int getCount() {
            return tab_names.length;
        }

        //返回每个标签的标题
        @Override
        public CharSequence getPageTitle(int position) {
           return tab_names[position];
        }
    }
}
