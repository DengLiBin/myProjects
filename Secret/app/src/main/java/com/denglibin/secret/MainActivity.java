package com.denglibin.secret;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.denglibin.secret.atys.AtyLogin;
import com.denglibin.secret.atys.AtyTimeline;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String token=Config.getCacheToken(this);//获取标记
        String phoneNumber=Config.getCacheNumber(this);
        if(token!=null&&phoneNumber!=null){
            Intent intent=new Intent(this,AtyTimeline.class);//将token和PhoneNumber传过去
            intent.putExtra(Config.KEY_TOKEN,token);
            intent.putExtra(Config.PHONE_KEY,phoneNumber);
            startActivity(intent);
        }else{
            startActivity(new Intent(this,AtyLogin.class));//直接跳到登录页面
        }
        finish();

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
}
