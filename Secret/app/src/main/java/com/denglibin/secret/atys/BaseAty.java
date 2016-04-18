package com.denglibin.secret.atys;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


/**
 * Created by DengLibin on 2016/4/7 0007.
 */
public class BaseAty extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    protected  void init(int R_layout,int toolbar_id){
        setContentView(R_layout);
        Toolbar toolbar= (Toolbar) findViewById(toolbar_id);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
