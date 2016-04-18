package com.denglibin.weather;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.Preference;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.denglibin.weather.domain.AirQuality;
import com.denglibin.weather.domain.FutureThreeDay;
import com.denglibin.weather.domain.WeatherBean;
import com.denglibin.weather.service.UpdateDataService;
import com.denglibin.weather.utils.ParseJson;
import com.denglibin.weather.utils.PinyinUtils;
import com.denglibin.weather.utils.ToUtf8;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;


import org.json.JSONObject;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private RelativeLayout rlCity;
    private PullToRefreshScrollView scrollView;
    private String CITY_NAME="成都";
    private String WEATHER_KEY="784b5fd75d7eb8ab68078ec8f5f5c5cd";
    private String AIR_KEY="0a4fb80e1835c11c96874073f56409a6";
    BroadcastReceiver receiver;
    SharedPreferences preferences;
    ParseJson parse;
    boolean flag=true;
    private int userIconId;
    private View headerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences=getSharedPreferences("city",MODE_PRIVATE);
        init();
        getSharedPreference();
        getCityWeather();
        getCityAir();
        startUpdateService();
        slidOperation();
    }
    //将选择的城市保存的本地
    private void saveSharedPreference(){

        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("city", CITY_NAME);
        editor.commit();

    }
    private void getSharedPreference(){
        CITY_NAME=preferences.getString("city",CITY_NAME);
    }
    private void startUpdateService(){
        //启动服务
       startService(new Intent(MainActivity.this, UpdateDataService.class));

        //注册广播接收器
        receiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle=intent.getExtras();
                String update=bundle.getString("update");
                if(update.equals("update")){
                    getCityAir();
                    getCityWeather();
                }
            }
        };
        IntentFilter filter=new IntentFilter();
        filter.addAction("com.denglibin.update");
        MainActivity.this.registerReceiver(receiver, filter);
    }
    private void init(){
        scrollView= (PullToRefreshScrollView) findViewById(R.id.pull_refresh_scrollview);
        scrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> pullToRefreshBase) {
                getCityWeather();
                scrollView.onRefreshComplete();//刷新完成
            }
        });

        rlCity= (RelativeLayout) findViewById(R.id.rl_city);
        rlCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, CityActivity.class), 1);
            }
        });
    }

    //使用Volley从服务器获取json字符串
    private void getCityWeather(){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String cityNameUtf8= ToUtf8.getUtf8(CITY_NAME);
        System.out.println("成都utf-8:" + cityNameUtf8);
        String jsonUrl="http://v.juhe.cn/weather/index?format=2&cityname="+cityNameUtf8+"&key="+WEATHER_KEY;

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, jsonUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String result=response.toString();
                        System.out.println(result);
                        //解析和填充数据
                        parse=new ParseJson();
                        WeatherBean bean=parse.parse(result);
                        //System.out.println("bean:" + bean);
                        if(bean!=null){
                            fillData(bean);
                        }
                        List<FutureThreeDay> list=parse.parseNextThreedDaysJson(result);
                        if(list!=null){
                            fillThreeDaysData(list);
                        }

                    }
                },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("对不起，有问题");
            }
        });


        requestQueue.add(jsonObjectRequest);
    }
    //从服务器获取空气质量数据
    private void getCityAir(){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String cityPinyin= PinyinUtils.getPinyin(CITY_NAME);
        if("成都".equals(CITY_NAME)){
            cityPinyin="chengdu";
        }
        String jsonAirUrl="http://web.juhe.cn:8080/environment/air/cityair?city="+cityPinyin+"&key="+AIR_KEY;
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, jsonAirUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String result2=response.toString();
                        System.out.println("空气质量:"+result2);
                        //解析和填充数据
                        if(parse==null){
                            parse=new ParseJson();
                        }
                        AirQuality air=parse.parseAir(result2);
                        if(air!=null){
                            fillDataPM(air);
                        }
                    }
                },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("对不起，有问题");
            }
        });
        requestQueue.add(jsonObjectRequest);

    }
        //填充数据
    private void fillData(WeatherBean weatherBean){
        TextView tv_city,tv_release,tv_now_weather,tv_temperature,temp,tv_tigan,tv_shidu,tv_fengxiang,
                tv_ziwaixian,tv_chuanyi;
        tv_city= (TextView) findViewById(R.id.tv_city);
        tv_release= (TextView) findViewById(R.id.tv_release);
        tv_now_weather= (TextView) findViewById(R.id.tv_now_weather);
        tv_temperature= (TextView) findViewById(R.id.tv_temperature);
        temp= (TextView) findViewById(R.id.tv_temp);
        tv_shidu= (TextView) findViewById(R.id.tv_shidu);
        tv_tigan= (TextView) findViewById(R.id.tv_tigan);
        tv_fengxiang= (TextView) findViewById(R.id.tv_fengxiang);
        tv_ziwaixian= (TextView) findViewById(R.id.tv_ziwaixian);
        tv_chuanyi= (TextView) findViewById(R.id.tv_chuanyi);

        tv_city.setText(CITY_NAME);
        tv_release.setText(weatherBean.getTime()+"发布");
        tv_now_weather.setText(weatherBean.getWeather());
        tv_temperature.setText(weatherBean.getTemperature());
        temp.setText(weatherBean.getTemp()+" °");
        tv_tigan.setText(weatherBean.getTemp()+" °");
        tv_shidu.setText(weatherBean.getHumidity());
        tv_fengxiang.setText(weatherBean.getWind_direction()+weatherBean.getWind_strength());
        tv_ziwaixian.setText(weatherBean.getUx_index());
        tv_chuanyi.setText(weatherBean.getDressing_index());
    }

    //填充未来三天的天气数据
    private void fillThreeDaysData(List<FutureThreeDay> futures){
        TextView tv_todayTop, tv_today_buttom,tv_tommorow_top,tv_tommorow_buttom,
                tv_thirdTop,tv_thirdButtom;
        tv_todayTop= (TextView) findViewById(R.id.tv_today_top);
        tv_today_buttom= (TextView) findViewById(R.id.tv_today_bottom);
        tv_tommorow_top= (TextView) findViewById(R.id.tv_tommorow_top);
        tv_tommorow_buttom= (TextView) findViewById(R.id.tv_tommorow_bottom);
        tv_thirdTop= (TextView) findViewById(R.id.tv_thirdDay_top);
        tv_thirdButtom= (TextView) findViewById(R.id.tv_thirdDay_bottom);

        for(int i=0;i<futures.size();i++){
            FutureThreeDay day=futures.get(i);
            switch (i){
                case 0:
                    tv_todayTop.setText(day.getTopTemp());
                    tv_today_buttom.setText(day.getBottomTemp());
                    break;
                case 1:
                    tv_tommorow_top.setText(day.getTopTemp());
                    tv_tommorow_buttom.setText(day.getBottomTemp());
                    break;
                case 2:
                    tv_thirdTop.setText(day.getTopTemp());
                    tv_thirdButtom.setText(day.getBottomTemp());
                    break;
                default:
                    break;
            }
        }
    }

    //填充PM2.5数据
    private void fillDataPM (AirQuality air){
        TextView tv_quality_index,tv_quality;
        tv_quality= (TextView) findViewById(R.id.tv_quality);
        tv_quality_index= (TextView) findViewById(R.id.tv_quality_index);

        tv_quality_index.setText(air.getAQI());
        tv_quality.setText(air.getQuality());
    }

    //侧边栏相关操作
    private void slidOperation(){
        final SharedPreferences sharedPreferences2=getSharedPreferences("user",MODE_PRIVATE);
        final String userName=sharedPreferences2.getString("userName", "立刻登录");
        android.support.design.widget.NavigationView view= (NavigationView) findViewById(R.id.navigation);
        headerView=view.getHeaderView(0);
        final TextView tv_user= (TextView) headerView.findViewById(R.id.tv_signup);
        tv_user.setText(userName);

            tv_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("立刻登录".equals(tv_user.getText().toString().trim())) {
                        startActivity(new Intent(MainActivity.this, SignUpActivity.class));
                    } else {
                        sharedPreferences2.edit().remove("userName").commit();
                        tv_user.setText("立刻登录");
                        Toast.makeText(MainActivity.this, "已经退出登录", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        userIconId=sharedPreferences2.getInt("iconId",0);
        ImageView user_icon= (ImageView) headerView.findViewById(R.id.iv_user_icon);
        String myIcon=sharedPreferences2.getString("myIcon",null);
        if(TextUtils.isEmpty(myIcon)) {
            if(userIconId!=0){
                user_icon.setImageResource(userIconId);
            }

        }else{
            Bitmap bm= BitmapFactory.decodeFile(myIcon);
            user_icon.setImageBitmap(bm);
        }

        user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddIconActivity.class));
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 1) {
            String city = data.getStringExtra("city");
            CITY_NAME=city;
            saveSharedPreference();
            getCityWeather();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        final SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        userIconId=sharedPreferences.getInt("iconId",0);
        String myIcon=sharedPreferences.getString("myIcon",null);

        ImageView user_icon= (ImageView) headerView.findViewById(R.id.iv_user_icon);
        if(TextUtils.isEmpty(myIcon)) {
            if(userIconId!=0){
                user_icon.setImageResource(userIconId);
            }

        }else{
            Bitmap bm= BitmapFactory.decodeFile(myIcon);
            user_icon.setImageBitmap(bm);
        }

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        stopService(new Intent(MainActivity.this,UpdateDataService.class));
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){

        switch(keyCode){
            case KeyEvent.KEYCODE_BACK:
                if(flag){
                    Toast.makeText(MainActivity.this,"再按一次退出",Toast.LENGTH_SHORT).show();
                    flag=false;
                }else{
                    MainActivity.this.finish();
                }
                break;

        }
        return false;
    }
}

