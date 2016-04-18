package com.example.administrator.myxmpp.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.myxmpp.R;
import com.example.administrator.myxmpp.config.Constant;
import com.example.administrator.myxmpp.utils.ThreadUtils;
import com.example.administrator.myxmpp.utils.ToastUtils;
import com.example.administrator.myxmpp.utils.UiUtils;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Registration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DengLibin on 2016/4/17 0017.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText m_et_username;
    private EditText m_et_password;
    private Button m_btn_login, m_btn_signup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initListener();
    }


    private void initView() {
        m_et_username = (EditText) findViewById(R.id.et_username);
        m_et_password = (EditText) findViewById(R.id.et_password);
        m_btn_login = (Button) findViewById(R.id.btn_login);
        m_btn_signup = (Button) findViewById(R.id.btn_signup);
    }

    private void initListener() {
        m_btn_login.setOnClickListener(this);
        m_btn_signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final String username = m_et_username.getText().toString().trim();
        final String password = m_et_password.getText().toString().trim();

        //判断用户名和密码是否为空
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(UiUtils.getContext(), UiUtils.getResource().
                    getString(R.string.username_password_is_empty), Toast.LENGTH_SHORT).show();
            return;
        }

        switch (v.getId()) {
            case R.id.btn_login://登录
                ThreadUtils.runInThread(new Runnable() {
                    @Override
                    public void run() {
                        XMPPConnection conn = getXMPPConnection();
                        try {
                            conn.login(username, password);
                            ToastUtils.showToastSafe(LoginActivity.this, "登录成功");
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent); //跳转到主页面
                            finish();
                        } catch (XMPPException e) {
                            e.printStackTrace();

                        }
                    };
                });
                break;
            case R.id.btn_signup://注册
                ThreadUtils.runInThread(new Runnable() {
                    @Override
                    public void run() {
                        XMPPConnection conn = getXMPPConnection();
                        Registration reg = new Registration();
                        reg.setType(IQ.Type.SET);
                        reg.setTo(conn.getServiceName());
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("username", username);
                        map.put("password", password);
                        reg.setAttributes(map);
                        PacketFilter filter = new AndFilter(new PacketIDFilter(reg.getPacketID()),
                                new PacketTypeFilter(IQ.class));
                        //PacketCollector collector = conn.createPacketCollector(filter);
                        conn.sendPacket(reg);
                        ToastUtils.showToastSafe(LoginActivity.this, "注册成功,请登录");
                    }
                });
                break;
            default:
                break;
        }
    }

    //连接服务器，并拿到连接对象（在子线程中进行）
    private XMPPConnection getXMPPConnection() {
        ConnectionConfiguration config = new ConnectionConfiguration(Constant.SERVER_URL, Constant.SERVER_PORT, Constant.SERVER_NAME);
        // 额外的配置(方便我们开发,上线的时候,可以改回来)
        //config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);// 明文传输
        config.setDebuggerEnabled(true);// 开启调试模式,方便我们查看具体发送的内容
        config.setReconnectionAllowed(true);
        config.setSendPresence(true); // 状态设为离线，目的为了取离线消息
        config.setReconnectionAllowed(true);
        config.setSecurityMode(ConnectionConfiguration.SecurityMode.enabled);
        config.setCompressionEnabled(false);//是否对流进行压缩
        config.setSASLAuthenticationEnabled(true);
        XMPPConnection conn = new XMPPConnection(config);
        try {
            conn.connect();
            System.out.println("执行了connnect方法");
            if (conn.isConnected()) {
                Constant.conn=conn;//保存一下conn
                ToastUtils.showToastSafe(LoginActivity.this, "连接服务器成功");
                System.out.println("连接服务器成功:" + conn.getServiceName());
            }
        } catch (XMPPException e) {
            e.printStackTrace();
            ToastUtils.showToastSafe(LoginActivity.this, "连接服务器失败");
        }


        return conn;
    }
}

