package com.example.administrator.myxmpp.activitys;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.myxmpp.R;
import com.example.administrator.myxmpp.adapter.ViewPagerAdapter;
import com.example.administrator.myxmpp.config.Constant;
import com.example.administrator.myxmpp.fragment.ContactsFragment;
import com.example.administrator.myxmpp.fragment.SessionFragment;
import com.example.administrator.myxmpp.utils.ToastUtils;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.XMPPException;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity{
    @InjectView(R.id.main_bottom)
    LinearLayout m_ll_Btn;
    @InjectView(R.id.main_viewpager)
    ViewPager m_view_pager;
    @InjectView(R.id.rg_group)
    RadioGroup m_rg_group;
    @InjectView(R.id.rb_session)
    RadioButton m_rb_session;
    @InjectView(R.id.rb_contacts)
    RadioButton m_rb_contacts;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);//使用butterknife包通过注解找到控件
        setSupportActionBar(toolbar);
        toolbar.setTitle("消息");
        initData();

    }

    private void initData() {
        fragments=new ArrayList<Fragment>();
        //讲Fragment放入到集合中
        fragments.add(new SessionFragment());
        fragments.add(new ContactsFragment());
        m_view_pager.setAdapter(new ViewPagerAdapter(this.getSupportFragmentManager(),fragments));
        m_rb_session.setChecked(true);
        m_view_pager.setCurrentItem(0);
        toolbar.setTitle("消息");
        m_rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_session:
                        m_view_pager.setCurrentItem(0);
                        toolbar.setTitle("消息");
                        break;
                    case R.id.rb_contacts:
                        m_view_pager.setCurrentItem(1);
                        toolbar.setTitle("联系人");
                        break;
                    default:
                        break;
                }
            }
        });

        m_view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        m_rb_session.setChecked(true);
                        toolbar.setTitle("消息");
                        break;
                    case 1:
                        m_rb_contacts.setChecked(true);
                        toolbar.setTitle("联系人");
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.menu_main,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_settings){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            final AlertDialog dialog=builder.create();
            View view=View.inflate(this,R.layout.add_contact_dialog,null);
            dialog.setView(view,0,0,0,0);//边距为0
            final EditText et_contactId=(EditText)view.findViewById(R.id.et_contactId);
                view.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {

                        final String contactId=et_contactId.getText().toString().trim();
                        if(TextUtils.isEmpty(contactId)){
                            ToastUtils.showToastSafe(MainActivity.this,"id不能为空");
                        }else{
                            if(Constant.conn!=null){
                                Roster roster=Constant.conn.getRoster();
                                //添加联系人
                                try {
                                    roster.createEntry(contactId,null,new String[]{"friends"});
                                    ToastUtils.showToastSafe(MainActivity.this,"添加联系人成功");
                                    dialog.dismiss();
                                } catch (XMPPException e) {
                                    ToastUtils.showToastSafe(MainActivity.this,"添加联系人失败");
                                    e.printStackTrace();
                                }
                            }else{
                                ToastUtils.showToastSafe(MainActivity.this,"连接失效，请重新登录");
                            }
                        }
                    }
                });
            view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();//显示

        }
        return super.onOptionsItemSelected(item);
    }
}
