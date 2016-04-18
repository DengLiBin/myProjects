package com.denglibin.secret.atys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.denglibin.secret.Config;
import com.denglibin.secret.R;
import com.denglibin.secret.adapter.MsglistAdpter;
import com.denglibin.secret.domain.Msg;
import com.denglibin.secret.ld.MyContacts;
import com.denglibin.secret.net.Login;
import com.denglibin.secret.net.TimeLine;
import com.denglibin.secret.net.UploadContacts;
import com.denglibin.secret.utils.MD5Tool;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by DengLibin on 2016/4/7 0007.
 */
public class AtyTimeline extends AppCompatActivity {
    public String phone,token,phone_md5;
    private RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar_timeline);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        phone=getIntent().getStringExtra(Config.PHONE_KEY);
        token=getIntent().getStringExtra(Config.KEY_TOKEN);
        phone_md5= MD5Tool.md5(phone);
        rv= (RecyclerView) findViewById(R.id.rv_timeline);
        //rv.setLayoutManager(new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false));//网格布局4列,竖向不反转
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));//垂直，不反转
        new UploadContacts(phone_md5, token, MyContacts.getContactsJSONString(this),
                new UploadContacts.SuccessCallback() {
                    @Override
                    public void onSuccess() {
                        loadMessage();
                    }
                },
                new UploadContacts.FailCallback() {
                    @Override
                    public void onFail(int errorCode) {
                        if(errorCode==Config.TOKEN_INVALID){//token过期,跳转到登录界面
                            startActivity(new Intent(AtyTimeline.this,AtyLogin.class));
                            finish();
                        }else{
                            loadMessage();
                        }
                    }
                });
    }

    private void loadMessage(){
        System.out.println("成功执行了loadMessage");
        final ProgressDialog pd=ProgressDialog.show(this,getResources().getString(R.string.connecting),
                getResources().getString(R.string.loadmessage));

        new TimeLine(phone_md5, token, 1, 20, new TimeLine.SuccessCallback() {
            @Override
            public void OnSuccess(int page, int perpage, List<Msg> list) {
                for(int i=0;i<50;i++){
                    list.add(new Msg("msg"+i,"md5",i+""));
                }
                rv.setAdapter(new MsglistAdpter(AtyTimeline.this,list));
                pd.dismiss();
            }
        }, new TimeLine.FailCallback() {
            @Override
            public void OnFail(int errorcode) {
                pd.dismiss();
                if(errorcode==Config.TOKEN_INVALID){//token过期，重新登录
                    startActivity(new Intent(AtyTimeline.this, Login.class));
                    finish();
                }else{
                    Toast.makeText(AtyTimeline.this,R.string.fail_to_loadmessage,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
