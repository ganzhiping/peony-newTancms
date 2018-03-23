package com.peony.newPeony.util;

import com.alibaba.druid.filter.config.ConfigTools;
import com.peony.newPeony.opinionMon.model.OpinionCondition;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/17.
 */
public class NewPeonyUtil {
    // 如果第i个元素跟第i-1个元素相同，就把重复的去掉
    public static List<OpinionCondition> removeDuplicateByTitle(List<OpinionCondition> list) {
        if (null == list || list.size() <= 1) {
            return list;
        }
        List<OpinionCondition> newList = new ArrayList<OpinionCondition>();
        newList.add(list.get(0));
        for (int i = 1; i < list.size(); i++) {
            String curTitle = list.get(i).getTitle();
            String lastTitle = list.get(i - 1).getTitle();

            if (!curTitle.equals(lastTitle)) {
                newList.add(list.get(i));
            }
        }
        return newList;
    }

    /**
     * 外部接口
     * @param url
     * @return
     * @throws Exception
     */
    public static String doGet(String url) throws Exception {
        String content = null;
        BufferedReader br = null;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(url));
            HttpResponse response = client.execute(request);
            //返回的流
            br = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while((line=br.readLine())!=null){
                sb.append("<p>"+line+"</p>");
            }
            content = sb.toString();
        } finally {
            br.close();
        }
        return content;
    }

    public static String doGetByAutor(String url) throws Exception {
        String content = null;
        BufferedReader br = null;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(url));
            HttpResponse response = client.execute(request);
            //返回的流
            br = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"));
            content = br.readLine();
        } finally {
            br.close();
        }
        return content;
    }

/*
    public static void main(String[] args){
        try {
            System.out.println(ConfigTools.encrypt("peony"));
            System.out.println(ConfigTools.encrypt("peony2014"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
