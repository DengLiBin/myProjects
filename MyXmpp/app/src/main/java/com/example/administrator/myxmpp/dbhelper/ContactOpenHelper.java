package com.example.administrator.myxmpp.dbhelper;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.example.administrator.myxmpp.config.Constant;

/**
 * Created by Administrator on 2016/4/18.
 */
public class ContactOpenHelper extends SQLiteOpenHelper {

    public ContactOpenHelper(Context context) {
        super(context, "contact.db", null, 1);
    }

    //创建联系人表
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE"+ Constant.TABLE_CONTACT+"(_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                Constant.ContactTable.ACCTOUN+"TEXT,"+
                Constant.ContactTable.NICKNAME+"TEXT,"+
                Constant.ContactTable.AVATAR+"TEXT,"+
                Constant.ContactTable.PINYIN+"TEXT)";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
