package com.denglibin.googleplay.protocol;

import com.denglibin.googleplay.domain.AppInfo;
import com.denglibin.googleplay.utils.FileUtils;
import com.lidroid.xutils.util.IOUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.List;

/**
 * Created by DengLibin on 2016/3/17 0017.
 */
public abstract  class BaseProtocol<T>  {

    /**
     * 加载数据的逻辑,返回的应该是个对象
     * @param index
     */
    public T load(int index){//加载数据（从发服务器或是本地拿到json数据并解析，返回对象的集合）
        //请求服务器
        String json=loadLocal(index);//先从本地获取
        if(json==null){//本地没有就从服务器获取
            json=loadServer(index);//从服务器获取数据
            if(json!=null){
                saveLocal(json,index);//将数据保存到本地
            }
        }else{
            System.out.println("复用了本地缓存");
        }
        if(json!=null){//拿到数据后（从本地或是服务器）解析数据
            return  parseJson(json);
        }else{
            return null;
        }

    }

    /**
     * 从服务获取数据
     * @param index
     * @return
     */
    public  String loadServer(int index){
        //HttpHelper.HttpResult httpResult=HttpHelper.get(HttpHelper.URL+"?index="+index);//不能从电脑端获取到数据
        //System.out.println("httpresult:" + httpResult);
        //InputStream in=httpResult.getInputStream();
        //String json= TextUtis.getTextFromStream(in);
        //System.out.println(json);
        String json="{\"list\": [\"id\": 152555222,\"name\": \"有缘网\",\"packageName\": \"com.youyuan.yyhl\",\"iconUrl\": \"app/com.youyuan.yyhl/icon.jpg\",\"stars\": 4,\"size\": 3876203,\"downloadUrl\": \"app/com.youyuan.yyhl/com.youyuan.yyhl.apk\",\"des\": \"产品介绍：有缘是时下最受大众单身男女青睐的婚恋交友软件\"}";
        return json;
    }

    /**
     * 数据保存到本地
     * @param json
     * @param index
     */

    public void saveLocal(String json,int index) {

        BufferedWriter bw = null;
        try {
            File dir = FileUtils.getCacheDir();
            System.out.println("dir:" + dir.toString());
            //在第一行写一个过期时间
            File file = new File(dir, getKey() + index);

            FileWriter fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            bw.write(System.currentTimeMillis() + 1000 * 100 + "");//当前时间100秒后（过期时间是100秒）
            bw.newLine();//换行
            bw.write(json);//把整个json文件保存起来
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("io异常啦");
        } finally {
            IOUtils.closeQuietly(bw);
        }
    }

    /**
     * 从本地获取数据
     * @param index
     * @return
     */
    public String loadLocal(int index){
        File dir=FileUtils.getCacheDir();
        File file=new File(dir,getKey()+index);
        try{
            FileReader fr=new FileReader(file);
            BufferedReader br=new BufferedReader(fr);
            // System.out.println("第一行outOfDate:"+br.readLine());
            long outOfDate=Long.parseLong(br.readLine());//将第一行的时间读出来，转成long类型

            if(System.currentTimeMillis()>outOfDate){//如果当前时间大于设置的过期时间（存的时间+100s），缓存过期
                return null;
            }else{
                String str=null;
                StringWriter sw=new StringWriter();
                while((str=br.readLine())!=null){//第一行已经读了，现在读第二行（共两行）

                    sw.write(str);
                }
                System.out.println(sw.toString());
                return sw.toString();//返回的就是存的json字符串
            }
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
    }
    protected abstract T parseJson(String json);
    protected abstract String getKey();
}
