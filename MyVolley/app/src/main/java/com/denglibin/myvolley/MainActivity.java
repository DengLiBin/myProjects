package com.denglibin.myvolley;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.LruCache;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.lang.ref.ReferenceQueue;

/**
 * Volley是Android平台网络通信库：更快，更简单，更健壮
 * Volley提供的功能：
 * 1、JSON、图片（异步）
 * 2、网络请求的排序
 * 3、网络请求的优先级处理
 * 4、缓存
 * 5、多级别的取消请求
 * 6、与Activity生命周期联动
 *
 * 获取Volley
 */
public class MainActivity extends AppCompatActivity {
    TextView tv;
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        tv= (TextView) findViewById(R.id.text);
        iv= (ImageView) findViewById(R.id.iv);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getJsonVolley();
        loadImageVolley();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //获取JSON字符串
    public void  getJsonVolley(){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String jsonUrl="http://file.bmob.cn/M03/09/EE/oYYBAFb9HPeAf2HXAAAEk4nVkg015.json";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, jsonUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("response:" + response.toString());
                        String result=response.toString();
                        tv.setText(result);
                    }
                },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                   System.out.println("对不起，有问题");
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public void loadImageVolley(){
        String imageUrl="https://www.baidu.com/img/bd_logo1.png";
        RequestQueue requestQueue=Volley.newRequestQueue(this);

        final LruCache<String,Bitmap> lruCache=new LruCache<String,Bitmap>(20);
        ImageLoader.ImageCache imageCache=new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String url) {
                return lruCache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                lruCache.put(url,bitmap);
            }
        };

        ImageLoader imageLoader=new ImageLoader(requestQueue,imageCache);

        ImageLoader.ImageListener listener=imageLoader.getImageListener(iv,
                            R.mipmap.ic_launcher,R.mipmap.ic_launcher);

        imageLoader.get(imageUrl,listener);
    }
}
