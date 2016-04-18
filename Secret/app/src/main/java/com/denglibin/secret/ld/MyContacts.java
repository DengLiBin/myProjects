package com.denglibin.secret.ld;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.denglibin.secret.Config;
import com.denglibin.secret.utils.MD5Tool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by DengLibin on 2016/4/9 0009.
 */
public class MyContacts {
    public static String getContactsJSONString(Context context){
        Cursor cursor=context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,null,null,null);//没有限制条件，查询全部联系人
        String phoneNum;
        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject;
        while(cursor.moveToNext()){
            phoneNum=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            if(phoneNum.charAt(0)=='+'&&phoneNum.charAt(1)=='8'&&phoneNum.charAt(2)=='6'){
                phoneNum=phoneNum.substring(3);//前面三个字符不要
            }
            //封装json字符串
            jsonObject=new JSONObject();
            try {
                jsonObject.put(Config.PHONE_MD5, MD5Tool.md5(phoneNum));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();

    }
}
