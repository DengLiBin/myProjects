package com.denglibin.googleplay.protocol;


import com.denglibin.googleplay.domain.AppInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by DengLibin on 2016/3/16 0016.
 */
public class HomeProtocol extends BaseProtocol <List<AppInfo>> {
    @Override
    protected List<AppInfo> parseJson(String json){
        List<AppInfo> appInfos=new ArrayList<AppInfo>();

      /*   json解析格式如下（没有json数据,咱就直接封装了），
        try{
            JSONObject jsonObject=new JSONObject(json);
            JSONArray jsonArray=jsonObject.getJSONArray("list");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObj=jsonArray.getJSONObject(i);
                long id=jsonObj.getLong("id");
                String name=jsonObj.getString("name");
                String packageName=jsonObj.getString("packageName");
                String iconUrl=jsonObj.getString("iconUrl");
                float stars=Float.parseFloat(jsonObj.getString("stars"));
                long size=jsonObj.getLong("size");
                String downloadUrl=jsonObj.getString("downloadUrl");
                String des=jsonObj.getString("des");


                AppInfo info=new AppInfo(id,name,packageName,iconUrl,stars,size,downloadUrl,des);
                appInfos.add(info);
            }
            return appInfos;//返回对象的集合
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
        */
        for(int i=0;i<10;i++){
            AppInfo info=new AppInfo(123123+i,"有缘网"+i,"com.youyuan.yyhl","app/com.youyuan.yyhl/icon.jpg",4,3876203+i,"app/com.youyuan.yyhl/com.youyuan.yyhl.apk","产品介绍：有缘是时下最受大众单身男女青睐的婚恋交友软件");
            appInfos.add(info);
        }



        return appInfos;
    }

    /**
     * 每个页面的标记
     * @return
     */
    @Override
    public String getKey(){
        return "home";
    }
}

/*json格式数据：
{
   "list":[
        {
            "id":152555222,
            "name":"有缘网",
            "packageName":"com.youyuan.yyhl",
            "iconUrl":"app/com.youyuan.yyhl/icon.jpg",
            "stars":4,
            "size":3876203,
            "downloadUrl":"app/com.youyuan.yyhl/com.youyuan.yyhl.apk",
            "des":"产品介绍：有缘是时下最受大众单身男女青睐的婚恋交友软件",
        }，
        {
            "id":152555222,
            "name":"有缘网",
            "packageName":"com.youyuan.yyhl",
            "iconUrl":"app/com.youyuan.yyhl/icon.jpg",
            "stars":4,
            "size":3876203,
            "downloadUrl":"app/com.youyuan.yyhl/com.youyuan.yyhl.apk",
            "des":"产品介绍：有缘是时下最受大众单身男女青睐的婚恋交友软件",
        }，
        {
            "id":152555222,
            "name":"有缘网",
            "packageName":"com.youyuan.yyhl",
            "iconUrl":"app/com.youyuan.yyhl/icon.jpg",
            "stars":4,
            "size":3876203,
            "downloadUrl":"app/com.youyuan.yyhl/com.youyuan.yyhl.apk",
            "des":"产品介绍：有缘是时下最受大众单身男女青睐的婚恋交友软件",
        }，
        {
            "id":152555222,
            "name":"有缘网",
            "packageName":"com.youyuan.yyhl",
            "iconUrl":"app/com.youyuan.yyhl/icon.jpg",
            "stars":4,
            "size":3876203,
            "downloadUrl":"app/com.youyuan.yyhl/com.youyuan.yyhl.apk",
            "des":"产品介绍：有缘是时下最受大众单身男女青睐的婚恋交友软件",
        }
   ]
 }
*/