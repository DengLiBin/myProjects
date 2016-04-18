package com.denglibin.weather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.denglibin.weather.adapter.CityListAdapter;
import com.denglibin.weather.utils.CitysSichuan;

/**
 * Created by DengLibin on 2016/4/5 0005.
 */
public class CityActivity extends Activity {
    private ListView lv_city;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        initView();
    }

    private void initView(){
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lv_city= (ListView) findViewById(R.id.lv_city);

        String[] citys= new CitysSichuan(CityActivity.this).getCitys();
        lv_city.setAdapter(new CityListAdapter(citys,LayoutInflater.from(CityActivity.this)));

        lv_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv= (TextView) view.findViewById(R.id.tv_city);
                String city= (String) tv.getText();
                Intent intent = new Intent();
                intent.putExtra("city",city);
                setResult(1, intent);
                finish();
            }
        });
    }
}

