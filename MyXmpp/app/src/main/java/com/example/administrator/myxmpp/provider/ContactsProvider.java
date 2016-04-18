package com.example.administrator.myxmpp.provider;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.administrator.myxmpp.config.Constant;
import com.example.administrator.myxmpp.dbhelper.ContactOpenHelper;

/**
 * Created by Administrator on 2016/4/18.
 */
public class ContactsProvider extends ContentProvider {
    public  static final int CONTACT=1;
    public  static UriMatcher m_urimatcher;
    public ContactOpenHelper m_helper;
    static{
        m_urimatcher=new UriMatcher(UriMatcher.NO_MATCH);
        //添加一个匹配规则
        m_urimatcher.addURI("com.example.administrator.myxmpp","/contact",CONTACT);
    }

    @Override
    public boolean onCreate() {
        m_helper=new ContactOpenHelper(getContext());
        if(m_helper!=null){
            return true;//创建成功
        }
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int code=m_urimatcher.match(uri);
        Cursor cursor=null;
        switch (code) {
            case CONTACT:
                SQLiteDatabase db = m_helper.getReadableDatabase();
                cursor = db.query(Constant.TABLE_CONTACT, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    //此方法供其他应用调用，用于往数据库插入数据
    //values:由其他应用传入，用于封装要插入的数据
    //URI:内容提供者的主机名，也就是地址(在清单文件中配置)
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int code=m_urimatcher.match(uri);
        switch (code){
            case CONTACT:
                SQLiteDatabase db=m_helper.getWritableDatabase();
                long id=db.insert(Constant.TABLE_CONTACT,null,values);
                if(id!=-1){
                    System.out.println("插入成功");
                    //拼接最新的uri
                    uri= ContentUris.withAppendedId(uri,id);
                }
                break;
            default:
                break;

        }
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int code=m_urimatcher.match(uri);
        switch (code) {
            case CONTACT:
                SQLiteDatabase db = m_helper.getWritableDatabase();
               int deleteCount=db.delete(Constant.TABLE_CONTACT,selection,selectionArgs);
                if (deleteCount>0) {
                    System.out.println("删除成功");
                    return deleteCount;
                }
                break;
            default:
                break;
        }
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int code=m_urimatcher.match(uri);
        switch (code) {
            case CONTACT:
                SQLiteDatabase db = m_helper.getWritableDatabase();
                int updateCount= db.update(Constant.TABLE_CONTACT,values,selection,selectionArgs);
                if (updateCount>0) {
                    System.out.println("更新成功");
                    return updateCount;
                }
                break;
            default:
                break;
        }
        return 0;
    }
}
