package com.example.administrator.myxmpp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.myxmpp.R;
import com.example.administrator.myxmpp.activitys.LoginActivity;
import com.example.administrator.myxmpp.config.Constant;
import com.example.administrator.myxmpp.utils.ToastUtils;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPException;

import java.util.Collection;


public class ContactsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initData();
        initListener();

    }

    private void init() {
    }

    private void initData() {
    //获取联系人,需要连接对象conn
        if(Constant.conn!=null){
            Roster roster=Constant.conn.getRoster();
            //添加联系人
            /*
          try {
                roster.createEntry("yui",null,new String[]{"friends"});
            } catch (XMPPException e) {
                ToastUtils.showToastSafe(getContext(),"添加联系人失败");
                e.printStackTrace();
            }
            */
            //得到所有联系人
            Collection<RosterEntry> entries=roster.getEntries();
            for(RosterEntry entry:entries){
                System.out.println("联系人getName()别名:"+entry.getName());//nickName别名
                System.out.println("联系人getUser()账户名:"+entry.getUser());//账户
            }
        }else{
            ToastUtils.showToastSafe(getContext(),"没有连接到服务器，请重新登录");
            //startActivity(new Intent(getContext(), LoginActivity.class));
        }
    }

    private void initListener() {

    }

    private void initView(View view) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        initView(view);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
