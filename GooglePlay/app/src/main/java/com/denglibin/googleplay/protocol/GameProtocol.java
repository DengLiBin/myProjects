package com.denglibin.googleplay.protocol;

import com.denglibin.googleplay.domain.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DengLibin on 2016/3/19 0019.
 */
public class GameProtocol extends BaseProtocol<List<AppInfo>> {
    @Override
    protected List<AppInfo> parseJson(String json) {
        List<AppInfo> appInfos=new ArrayList<AppInfo>();
     /*
        try{
            JSONArray jsonArray=new JSONArray(json);
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
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

*/
        for(int i=0;i<100;i++){
            AppInfo info=new AppInfo(123123+i,"打飞机"+i,"com.youyuan.yyhl","app/com.youyuan.yyhl/icon.jpg",4,3876203+i,"app/com.youyuan.yyhl/com.youyuan.yyhl.apk","产品介绍：打飞机是时下最受大众单身男女青睐的手机游戏");
            appInfos.add(info);
        }



        return appInfos;
    }

    @Override
    protected String getKey() {
        return "game";
    }
}
