package com.denglibin.googleplay.protocol;


import com.denglibin.googleplay.domain.AppInfo;
import com.denglibin.googleplay.domain.SubjectInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by DengLibin on 2016/3/16 0016.
 */
public class SubjectProtocol extends BaseProtocol <List<SubjectInfo>> {
    @Override
    protected List<SubjectInfo> parseJson(String json){
        List<SubjectInfo> subjectInfos=new ArrayList<SubjectInfo>();

      /*   json解析格式如下（没有json数据,咱就直接封装了），
        try{
                JSONArray jsonArray=new JSONArray(json);
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    String des=jsonObject.getString("des");
                    String url=jsonObject.getString("url");
                    SubjectInfo info=new SubjectInfo(des,url);
                    subjectInfos.add(info);
                }
            }
            return subjectInfos;//返回对象的集合
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
        */
        for(int i=0;i<100;i++){
            SubjectInfo info=new SubjectInfo("描述信息"+i,"图片的url"+1);
            subjectInfos.add(info);
        }
        return subjectInfos;
    }

    /**
     * 每个页面的标记
     * @return
     */
    @Override
    public String getKey(){
        return "subject";
    }
}

/*json格式数据：
[
    {
        "des":"一周新锐游戏精选",
        "url":"image/recommend_01.jpg"
    },{
        "des":"一周新锐游戏精选",
        "url":"image/recommend_01.jpg"
    },{
        "des":"一周新锐游戏精选",
        "url":"image/recommend_01.jpg"
    },{
        "des":"一周新锐游戏精选",
        "url":"image/recommend_01.jpg"
    }
]
*/