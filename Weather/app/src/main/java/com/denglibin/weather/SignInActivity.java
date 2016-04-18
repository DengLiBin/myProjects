package com.denglibin.weather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.denglibin.weather.R;
import com.denglibin.weather.entity.User;
import com.denglibin.weather.utils.GetMD5;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by DengLibin on 2016/4/5 0005.
 */
public class SignInActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText et_userName,et_password,et_confirmPassword;
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        preferences=getSharedPreferences("user",MODE_PRIVATE);
        init();
    }
    private void init(){
        findViewById(R.id.iv_back3).setOnClickListener(this);
        findViewById(R.id.btn_signin).setOnClickListener(this);
        et_userName= (EditText) findViewById(R.id.et_username_signin);
        et_password= (EditText) findViewById(R.id.et_password_signin);
        et_confirmPassword= (EditText) findViewById(R.id.et_confirmpassword_signin);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back3:
                finish();
                break;
            case R.id.btn_signin:
                final String userName=et_userName.getText().toString().trim();
                String password=et_password.getText().toString().trim();
                String confirmPassword=et_confirmPassword.getText().toString().trim();
                if(!TextUtils.isEmpty(userName)&&!TextUtils.isEmpty(password)&&!TextUtils.isEmpty(confirmPassword)){
                    if(password.equals(confirmPassword)){
                        User user=new User();
                        user.setUsername(userName);
                        final String md5Password=GetMD5.MD5(password);
                        user.setPassword(md5Password);
                        user.setInfo("普通用户");
                        user.signUp(this, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                SharedPreferences.Editor editor=preferences.edit();
                                editor.putString("userName", userName);
                                editor.putString("password",md5Password);
                                editor.commit();
                                Toast.makeText(SignInActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignInActivity.this,MainActivity.class));
                                finish();
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                Toast.makeText(SignInActivity.this, "注册失败：" + s,
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                    }else{
                        Toast.makeText(this,"两次密码输入不一致",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this,"亲，用户名和密码不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
