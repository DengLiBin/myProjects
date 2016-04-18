package com.denglibin.secret.atys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.denglibin.secret.Config;
import com.denglibin.secret.R;
import com.denglibin.secret.net.GetCode;
import com.denglibin.secret.net.Login;
import com.denglibin.secret.utils.MD5Tool;

import org.w3c.dom.Text;

/**
 * 登录页面
 * Created by DengLibin on 2016/4/7 0007.
 */
public class AtyLogin extends BaseAty {
    private EditText et_phone;
    private EditText et_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.init(R.layout.activity_login, R.id.toolbar_login);
        et_phone= (EditText) findViewById(R.id.et_number);
        et_code= (EditText) findViewById(R.id.et_code);
        //获取验证码
        findViewById(R.id.btn_getCode).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String number=et_phone.getText().toString().trim();
                if(TextUtils.isEmpty(number)){
                    Toast.makeText(AtyLogin.this,R.string.phone_number_can_not_be_empty,Toast.LENGTH_SHORT).show();
                    return;
                }
                //执行获取验证码的逻辑
                final ProgressDialog pd=ProgressDialog.show(AtyLogin.this,getResources().getString(R.string.connecting),
                        getResources().getString(R.string.connecting_to_server));//显示一个进度条窗口
                pd.show();
                new GetCode(number, new GetCode.SuccessCallback() {
                    @Override
                    public void onSuccess(String result) {
                        pd.dismiss();
                        Toast.makeText(AtyLogin.this,R.string.success_to_send_code,Toast.LENGTH_SHORT).show();
                    }
                }, new GetCode.FailCallback() {
                    @Override
                    public void onFail() {
                        pd.dismiss();
                        Toast.makeText(AtyLogin.this,R.string.fail_to_get_code,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //登录
        findViewById(R.id.btn_submitCode).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                final String phonenumber=et_phone.getText().toString().trim();
                if(TextUtils.isEmpty(phonenumber)){
                    Toast.makeText(AtyLogin.this,R.string.phone_number_can_not_be_empty,Toast.LENGTH_SHORT).show();
                }
                String code=et_code.getText().toString().trim();
                if(TextUtils.isEmpty(code)){
                    Toast.makeText(AtyLogin.this,R.string.code_can_not_be_empty,Toast.LENGTH_SHORT).show();
                }

                new Login(MD5Tool.md5(phonenumber),code,new Login.SuccessCallBack(){
                    @Override
                    public void onSuccess(String token) {
                        Config.setCacheToken(AtyLogin.this, token);//把token保存到本地
                        Config.setCacheNumber(AtyLogin.this, phonenumber);
                        Intent in=new Intent(AtyLogin.this,AtyTimeline.class);
                        in.putExtra(Config.KEY_TOKEN,token);//将token和号码传过去
                        in.putExtra(Config.PHONE_KEY,phonenumber);
                        startActivity(in);
                        finish();
                    }
                },new Login.FailCallBack(){
                    @Override
                    public void onFail() {
                        Toast.makeText(AtyLogin.this,R.string.fail_to_login,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}
