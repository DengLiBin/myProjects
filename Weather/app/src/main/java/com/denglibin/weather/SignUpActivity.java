package com.denglibin.weather;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.denglibin.weather.entity.User;
import com.denglibin.weather.utils.GetMD5;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by DengLibin on 2016/4/5 0005.
 */
public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_userName;
    private EditText et_password;
    private SharedPreferences preferences;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        preferences=getSharedPreferences("user",MODE_PRIVATE);
        init();
    }

    private void init(){
        findViewById(R.id.iv_back2).setOnClickListener(this);
        findViewById(R.id.btn_signup).setOnClickListener(this);
        findViewById(R.id.tv_signin).setOnClickListener(this);
        et_userName= (EditText) findViewById(R.id.et_username);
        et_password= (EditText) findViewById(R.id.et_password);
    }

    @Override
    public void onClick(View v) {
        final User user=new User();
        switch (v.getId()){
            case R.id.iv_back2:
                finish();
                break;
            case R.id.tv_signin:
                startActivity(new Intent(this,SignInActivity.class));
                break;
            case R.id.btn_signup:

                user.setUsername(et_userName.getText().toString().trim());
                user.setPassword(GetMD5.MD5(et_password.getText().toString().trim()));

                user.login(this, new SaveListener() {

                    @Override
                    public void onSuccess() {
                        Toast.makeText(SignUpActivity.this, "登陆成功", Toast.LENGTH_LONG)
                                .show();
                        SharedPreferences.Editor editor=preferences.edit();
                        editor.putString("userName", user.getUsername());
                        editor.commit();

                        startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onFailure(int arg0, String arg1) {
                        Toast.makeText(SignUpActivity.this, "登陆失败：" + arg1,
                                Toast.LENGTH_LONG).show();
                    }
                });
                break;
            default:
                break;
        }
    }
}
