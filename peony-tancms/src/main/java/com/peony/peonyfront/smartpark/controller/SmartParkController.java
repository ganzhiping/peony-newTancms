package com.peony.peonyfront.smartpark.controller;


import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.fastjson.JSONObject;
import com.peony.newPeony.opinionMon.model.OpinionCondition;
import com.peony.newPeony.util.NewPeonyUtil;
import com.peony.peonyfront.smartpark.model.SmartPark;
import com.peony.peonyfront.smartpark.service.SmartParkService;
import com.peony.peonyfront.util.encrypt.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/11/28.
 */
@Controller
@RequestMapping("/smartPark")
public class SmartParkController {
    private int userId = 3157;

    @Autowired
    private SmartParkService smartParkService;

    @RequestMapping("/login")
    public ModelAndView smartParkLogin(@RequestParam(value = "token", required = true) String token, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("/smartPark/login.....token:" + token);

        //String userid = (String)request.getSession().getAttribute("smartPark_userID");
        ModelAndView modelAndView = new ModelAndView("/smartpark/smartpark");
        String[] timeStamp = getTimeStamp();
        try{
            List<SmartPark> list = new ArrayList<>();
            //if(userid!=null &&!"".equals(userid)){
              //  list = smartParkService.selectFirstList(userId,timeStamp);
            //}else{
                JSONObject jsonObject = JSONObject.parseObject(token);
                JSONObject singleEntity = (JSONObject) jsonObject.get("singleEntity");
                String user_type = singleEntity.get("user_type").toString();
                System.out.println("/smartPark/login.....user_type:" + user_type);

                request.getSession().setAttribute("smartPark_userID", userId);
                if (user_type != null && !"".equals(user_type)) {
                    int userType = Integer.valueOf(user_type);
                    if (userType == 0) {
                        //访客模式
                        JSONObject userInfo = (JSONObject) singleEntity.get("userInfo");
                        String weixinId = userInfo.get("weixin_id").toString();
                        if (weixinId != null && !"".equals(weixinId)) {

                        }

                    } else if (userType == 1) {
                        //员工模式

                    } else if (userType == 2) {
                        //管理员模式

                    } else {

                    }
                    list = smartParkService.selectFirstList(userId, timeStamp);
                }
                    modelAndView.addObject("smartParkList", list);
                //}
        }catch (Exception e){e.printStackTrace();}
        modelAndView.addObject("startTime",timeStamp[1]);
        modelAndView.addObject("endTime",timeStamp[0]);
        return modelAndView;
    }

    @RequestMapping("/searchList")
    public  @ResponseBody List<SmartPark> searchList( HttpServletRequest request){
        String title = request.getParameter("title");
        SmartPark sp = new SmartPark();
        int userid = (Integer)request.getSession().getAttribute("smartPark_userID");
        sp.setUserid(userid);
        List<String> ids = smartParkService.selectSubjectIds(sp);
        List<SmartPark> list = new ArrayList<>();
        String subjectids = "";
        for (String id : ids) {
            if(id!=null &&!"".equals(id)){
                subjectids += id+",";
            }
        }
        sp.setSubjectIds(subjectids.substring(0,subjectids.length()-1));
        String queryid = searchFirst(title,sp);
        if(queryid!=null&&!"".equals(queryid)){
            request.getSession().setAttribute("queryid",queryid);
            sp.setQueryid(queryid);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list = smartParkService.selectByQueryCache(sp);
        }
        return list;
    }

    private String searchFirst(String keyword, SmartPark op) {
        String  ids = op.getSubjectIds();
        String[] subject_ids = ids.split(",");
        String query_id = MD5.toMD5("{days:5,user_id:"+op.getUserid()+",subject_ids:"+subject_ids+",phrases:"+keyword);
        com.alibaba.dubbo.common.json.JSONObject json1 = new com.alibaba.dubbo.common.json.JSONObject();
        String[] str1 = {};/*
        if(keyword.indexOf(" ")!=-1){*/
        str1 = keyword.split(" ");
       /* }else{
            str1 = keyword;
        }*/
        //int days = countDate(op.getEndTime(),op.getStartTime());
        json1.put("days", 5);
        json1.put("query_id",query_id);
        json1.put("user_id",op.getUserid());
        json1.put("subject_ids", subject_ids);
        json1.put("phrases", str1);
        String jsonString = null;
        try {
            jsonString = JSON.json(json1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String msg = "";
        try {
            msg = sendPost(jsonString, "http://192.168.3.19:9000/search/search_by_subject/");
            com.alibaba.dubbo.common.json.JSONObject ob = (com.alibaba.dubbo.common.json.JSONObject) JSON.parse(msg);
            String str = (String)ob.get("query_id");
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    public String sendPost(String jsonStr, String path)
            throws IOException {
        URL url = new URL(path);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
// //设置连接属性
        httpConn.setDoOutput(true);// 使用 URL 连接进行输出
        httpConn.setDoInput(true);// 使用 URL 连接进行输入
        httpConn.setUseCaches(false);// 忽略缓存
        httpConn.setRequestMethod("POST");// 设置URL请求方法
// 设置请求属性
// 获得数据字节数据，请求数据流的编码，必须和下面服务器端处理请求流的编码一致
        httpConn.setRequestProperty("Content-length", "" + (jsonStr.getBytes()).length);
        httpConn.setRequestProperty("Content-Type", "application/json");
        httpConn.setRequestProperty("Accept-Charset", "utf-8");
        httpConn.setRequestProperty("Charset", "UTF-8");// 返回写入到此连接的输出流
        OutputStream dataOutputStream = httpConn.getOutputStream();
        dataOutputStream.write(jsonStr.getBytes("UTF-8"));
        dataOutputStream.flush();
        //关闭流
        // 如果请求响应码是200，则表示成功
        String lines;
        StringBuilder sb = new StringBuilder("");
        if (httpConn.getResponseCode() == 200) {
            // HTTP服务端返回的编码是UTF-8,故必须设置为UTF-8,保持编码统一,否则会出现中文乱码
            BufferedReader in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
            while ((lines = in.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            in.close();
        }
        dataOutputStream.close();
        httpConn.disconnect();// 断开连接
        return sb.toString();
    }


    @RequestMapping("/ajaxAddList")
    public @ResponseBody
    List<SmartPark> ajaxAddList(HttpServletRequest request, HttpServletResponse response) {
        List<SmartPark> list = new ArrayList<>();
        SmartPark park = new SmartPark();
        String pageStart = request.getParameter("pageStart");
        String pageEnd = request.getParameter("pageEnd");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            if (pageStart != null && !"".equals(pageStart) && pageEnd != null && !"".equals(pageEnd)) {
                park.setPageSize(Integer.valueOf(pageStart));
                park.setCurentPage(Integer.valueOf(pageEnd));
            }
            if (startTime != null && !"".equals(startTime) && endTime != null && !"".equals(endTime)) {
                park.setStartTime(sdf.format(sdf.parse(startTime)));
                park.setEndTime(sdf.format(sdf.parse(endTime)));
            }
            String user_Id = request.getSession().getAttribute("smartPark_userID").toString();
            if(user_Id!=null && !"".equals(user_Id)){
                userId = Integer.valueOf(user_Id);
            }
            park.setUserid(userId);
        } catch (Exception e) {
            System.out.println("/ajaxAddList ERROR");
            e.printStackTrace();
        }
        String title = request.getParameter("title");
        if(title!=null && !"".equals(title)){
            park.setTitle(title);
            park.setQueryid((String)request.getSession().getAttribute("queryid"));
            list = smartParkService.selectAjaxSearch(park);
        }else{
            list = smartParkService.selectAjaxPageList(park);
        }
        return list;
    }

    @RequestMapping("/newsInfo")
    public ModelAndView newsInfo(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        SmartPark op = smartParkService.selectInfoById(id);
        String path = "http://119.254.110.32:8085/dfs/get?id=" + op.getPageId() + "&text=true&depress=true";
        String path_ = "http://192.168.3.19:9000/documents/?ids=" + op.getPageId() ;

        try {
            String content = NewPeonyUtil.doGet(path);
            String content_ = NewPeonyUtil.doGetByAutor(path_);
            String str = smartParkService.keywordsAndAuthor(content_);
            op.setContent(content);
            /*op.setKeywords(str[0]);*/
            op.setAuthor(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ModelAndView mv = new ModelAndView("/smartpark/smartparkInfo");
        mv.addObject("newInfoManager", op);
        return mv;
    }

    private String[] getTimeStamp(){
        String[] arrStr = new String[2];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String endTime = sdf.format(date);
        calendar.add(Calendar.DATE ,-2);
        String startTime = sdf.format(calendar.getTime());
        arrStr[0] = endTime;
        arrStr[1] = startTime;
        return arrStr;
    }
}
