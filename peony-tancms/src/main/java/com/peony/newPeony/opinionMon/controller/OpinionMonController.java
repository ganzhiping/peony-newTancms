package com.peony.newPeony.opinionMon.controller;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.JSONArray;
import com.alibaba.dubbo.common.json.JSONObject;
import com.peony.core.base.controller.BaseController;
import com.peony.core.base.pojo.PageParameter;
import com.peony.newPeony.opinionMon.model.Concern;
import com.peony.newPeony.opinionMon.model.OpinionCondition;
import com.peony.newPeony.opinionMon.model.OpinionSearch;
import com.peony.newPeony.opinionMon.model.QueryRequire;
import com.peony.newPeony.opinionMon.service.OpinionMonService;
import com.peony.newPeony.page.model.Page;
import com.peony.newPeony.page.service.PageService;
import com.peony.newPeony.system.service.PrivilegeService;
import com.peony.newPeony.system.service.SendMangerService;
import com.peony.newPeony.util.NewPeonyUtil;
import com.peony.peonyfront.key.service.IdService;
import com.peony.peonyfront.login.model.User;
import com.peony.peonyfront.subject.model.Subject;
import com.peony.peonyfront.subject.service.SubjectService;
import com.peony.peonyfront.util.encrypt.MD5;
import org.apache.poi.hssf.usermodel.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by gzp on 2017/8/15.
 * <p>
 * 舆情系统二次开发舆情监测页控制器
 */
@Controller
@RequestMapping("/opinionMon")
public class OpinionMonController extends BaseController {
    @Autowired
    private OpinionMonService opinionMonService;
    @Autowired
    private IdService idService;
    @Autowired
    private PageService pageService;
    @Autowired
    private SendMangerService sendManger;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private PrivilegeService privilegeService;

    /**
     * 舆情监测信息查询
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/opinionMonList")
    public ModelAndView opinionMonAll(HttpServletRequest request, HttpServletResponse response) {
        OpinionCondition opinionCondition = new OpinionCondition();
        PageParameter pageParameter = new PageParameter();
        if (null == request.getParameter("pageNo")) {
            pageParameter.setCurrentPage(1);
        } else {
            pageParameter.setCurrentPage(Integer.parseInt(request.getParameter("pageNo")));
        }
        if (null != request.getParameter("pagesize")) {
            pageParameter.setPageSize(Integer.parseInt(request.getParameter("pagesize")));
        } else {
            pageParameter.setPageSize(10);
        }
        pageParameter.setTotalPage((pageParameter.getCurrentPage() - 1) * pageParameter.getPageSize());
        opinionCondition.setPageParameter(pageParameter);
        //获取userId
        User user = (User) request.getSession().getAttribute("userfront");
        int userId = user.getUserId();
        opinionCondition.setUserid(userId);
        //查询条件
        QueryRequire queryRequire = getQueryRequire(request);
        /*if (queryRequire == null || "".equals(queryRequire) || 0 == queryRequire.getUserid()) {
            queryRequire = opinionMonService.selectQueryRequireByUesrId(userId);
        }*/
        opinionCondition = opinionMonService.queryCriteria(request, userId, opinionCondition, queryRequire);
        //排序方式
        String orderStr = sortOrder(request);
        opinionCondition.setOrderStr(orderStr);
        //查询信息
        OpinionCondition op = opinionMonService.selectPageNo(opinionCondition);
        Page page = pageService.SubjectPage(pageParameter, op.getPageNum(), op.getPageNoByTitle());
        List<OpinionCondition> list = opinionMonService.findByPage(opinionCondition);
        boolean flag = privilegeService.findSendManagerRole(userId);
        ModelAndView mv = new ModelAndView("/newPeony/opinionMon/opinionRight");
        mv.addObject("sendManagerRole", flag);
        mv.addObject("opinionCondition", list);
        mv.addObject("opinionPage", page);
        mv.addObject("subjectid", request.getParameter("subjectid"));
        return mv;
    }


    @RequestMapping("/opinionMon")
    public ModelAndView opinionMonInfo(HttpServletRequest request, HttpServletResponse response) {
        //获取userId
        User user = (User) request.getSession().getAttribute("userfront");
        int userId = user.getUserId();
        QueryRequire queryRequire = opinionMonService.selectQueryRequireByUesrId(userId);
        Subject subject = new Subject();
        subject.setUserid(userId);
        subject.setPid(Integer.parseInt("1"));
        List<Subject> list = this.subjectService.selectSubjectByUserIdAndPid(subject);
        boolean flag = false;
        if (list != null && !"".equals(list)) {
            flag = true;
        }
        ModelAndView mv = new ModelAndView("/newPeony/opinionMon/opinionMon");
        mv.addObject("queryRequire", queryRequire);
        mv.addObject("flag", flag);
        return mv;
    }   

    @RequestMapping("/opinionList")
    public ModelAndView opinionList(HttpServletRequest request, HttpServletResponse response) {
        //获取userId
        User user = (User) request.getSession().getAttribute("userfront");
        int userId = user.getUserId();
        QueryRequire queryRequire = opinionMonService.selectQueryRequireByUesrId(userId);
        ModelAndView mv = new ModelAndView("/newPeony/opinionMon/opinionList");
        mv.addObject("queryRequire", queryRequire);
        return mv;
    }

    @RequestMapping("/opinionProlarity")
    public @ResponseBody String opinionProlarity(HttpServletRequest request, HttpServletResponse response) {
        //获取userId
        User user = (User) request.getSession().getAttribute("userfront");
        int userId = user.getUserId();
        String pageid=request.getParameter("pageid");
        String subjectid = request.getParameter("subjectid");
        String pol =request.getParameter("pol");
        OpinionCondition op = new OpinionCondition();
        op.setP_subjectid(subjectid);
        op.setSubjectid(Integer.valueOf(subjectid));
        op.setP_feedT(new Date());
        int polV = Integer.valueOf(pol);
        if(polV==0 || polV==1){
            op.setP_originp(polV);
            op.setP_feedp(-1);
        }else{
            op.setP_originp(polV);
            op.setP_feedp(0);
        }
        op.setP_pageid(pageid);
        op.setP_id(idService.NextKey("sentiment_feedback"));
        op.setP_isused(0);
        String ret = opinionMonService.insertIntoProlarity(op);
        return String.valueOf(op.getP_feedp());
    }


    @RequestMapping("/QuerySearch")
    public @ResponseBody
    String QuerySearch(@RequestParam QueryRequire queryRequire, HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("userfront");
        int userId = user.getUserId();
        String time = queryRequire.getStartTime();
        System.out.println(time);
        return "";
    }

    @RequestMapping(value = "/setAsDefault", method = RequestMethod.POST)
    public @ResponseBody
    String SetAsDefault(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("userfront");
        int userId = user.getUserId();
        QueryRequire queryRequire = getQueryRequire(request);
        if(queryRequire == null){
            return "error";
        }
        queryRequire.setUserid(userId);
        opinionMonService.updateOrInsertQuery(queryRequire);
        String time = queryRequire.getStartTime();
        System.out.println(time);
        return "success";
    }

    private QueryRequire getQueryRequire(HttpServletRequest request) {
        QueryRequire queryRequire = new QueryRequire();
        int i =0;
        //文章来源
        if (request.getParameter("source") != null && !"".equals(request.getParameter("source"))) {
            queryRequire.setSource(Integer.valueOf(request.getParameter("source")));
            i++;
        }
        //情感极性type
        if (request.getParameter("polarity") != null && !"".equals(request.getParameter("polarity"))) {
            queryRequire.setPolarity(Integer.valueOf(request.getParameter("polarity")));
            i++;
        }
        //网站等级
        if (request.getParameter("weblevel") != null && !"".equals(request.getParameter("weblevel"))) {
            queryRequire.setWeblevel(Integer.valueOf(request.getParameter("weblevel")));
            i++;
        }
        if (request.getParameter("dataType") != null && !"".equals(request.getParameter("dataType"))) {
            queryRequire.setDataType(Integer.valueOf(request.getParameter("dataType")));
            i++;
        }
        if ((request.getParameter("dataType") == null || "".equals(request.getParameter("dataType")))&&(!"".equals(request.getParameter("endTime"))&&request.getParameter("endTime") != null && request.getParameter("startTime") != null)) {
            queryRequire.setDataType(6);
            queryRequire.setEndTime(request.getParameter("endTime"));
            queryRequire.setStartTime(request.getParameter("startTime"));
            i++;
        }
        if(i==0){
           return null;
        }else{
            return queryRequire;
        }
    }

    /**
     * 跳转新闻详情页
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/newsInfo")
    public ModelAndView newsInfo(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        OpinionCondition op = opinionMonService.selectPageById(id);
        String path = "http://119.254.110.32:8085/dfs/get?id=" + op.getPageid() + "&text=true&depress=true";
        String path_ = "http://192.168.3.19:9000/documents/?ids=" + op.getPageid() ;

        try {
            String content = NewPeonyUtil.doGet(path);
            String content_ = NewPeonyUtil.doGetByAutor(path_);
            String str = opinionMonService.keywordsAndAuthor(content_);
            op.setContent(content);
            /*op.setKeywords(str[0]);*/
            op.setAuthor(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ModelAndView mv = new ModelAndView("/newPeony/articleNewInfo");
        mv.addObject("newInfoManager", op);
        return mv;
    }


    @RequestMapping("/sendManger")
    public String sendManger(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        OpinionCondition op = opinionMonService.selectPageById(id);
        String url = op.getUrl();
        String title = op.getTitle();
        int userId = op.getUserid();
        String content = title + url + "【牡丹大数据】";
        boolean ret = sendManger.sendManager(content, userId);
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            if (ret) {
                pw.write("发送成功！");
            } else {
                pw.write("发送失败！");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            pw.close();
        }
        return "";
    }


    /**
     * 根据主键批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/deleteOpinionCondition", method = RequestMethod.POST)
    public @ResponseBody
    int deleteSubjectPage(@RequestParam(value = "ids[]", required = true) String[] ids, HttpServletRequest request) {
        OpinionCondition subjectPage = new OpinionCondition();
        // 从缓存中加载用户id
        User user = (User) request.getSession().getAttribute("userfront");
        int userId = user.getUserId();
        subjectPage.setUserid(userId);
        String result = "";
        for (int i = 0; i < ids.length; i++) {
            if (i == 0) {
                result = "'" + ids[i] + "'";
            } else {
                result = result + "," + "'" + ids[i] + "'";
            }
        }
        subjectPage.setIdArray(result);
        int i = this.opinionMonService.batchUpdate(subjectPage);
        return i;
    }

    @RequestMapping(value = "/opinionConcern", method = RequestMethod.POST)
    public @ResponseBody String opinionConcern( HttpServletRequest request,HttpServletResponse response) {
        // 从缓存中加载用户id
        User user = (User) request.getSession().getAttribute("userfront");
        int userId = user.getUserId();
        String id = request.getParameter("id");
        String pageid = request.getParameter("pageid");
        String subjectid= request.getParameter("subjectid");
        Concern concern = new Concern();
        concern.setId(id);
        concern.setUserid(userId);
        concern.setPageid(pageid);
        concern.setSubjectid(Integer.valueOf(subjectid));
        concern.setIsConcern(1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        concern.setUpdateTime(sdf.format(new Date()));
        String ret = opinionMonService.changeConcernValue(concern);
        return ret;
    }

    /**
     * http://192.168.3.19:9000/search/search_by_es/
     * {"days":5,"media_type":1,"subject_ids":["b095e022d9ae46418c47e76fa7717eb7"],"keyword":"马英九","fields":"content","page":500,"page_size":20}
     * {"query_id":"4CA0628FFF6C55988B135E8933541BA2",
     "user_id":12345,
     "subject_ids":["1849"],
     "days":15,
     "phrases":["安宁市", "道路"]
     }
     */
    @RequestMapping("/searchForResult")
    public ModelAndView searchForResult(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        ModelAndView mv = new ModelAndView("newPeony/opinionMon/opinionRight");
        request.setCharacterEncoding("UTF-8");
        OpinionCondition op = new OpinionCondition();
        PageParameter pageParameter = new PageParameter();
        if (null == request.getParameter("pageNo")) {
            pageParameter.setCurrentPage(1);
        } else {
            pageParameter.setCurrentPage(Integer.parseInt(request.getParameter("pageNo")));
        }
        if (null != request.getParameter("pagesize")) {
            pageParameter.setPageSize(Integer.parseInt(request.getParameter("pagesize")));
        } else {
            pageParameter.setPageSize(10);
        }
        pageParameter.setTotalPage((pageParameter.getCurrentPage() - 1) * pageParameter.getPageSize());
        op.setPageParameter(pageParameter);
        User user = (User) request.getSession().getAttribute("userfront");
        int userId = user.getUserId();
        QueryRequire queryRequire = getQueryRequire(request);
        op = opinionMonService.queryCriteria(request, userId, op, queryRequire);
        String keyword = request.getParameter("searchContent");
        op.setUserid(userId);
        String queryid = null;
        String searchType = request.getParameter("searchType");
        if("1".equals(searchType)){
            queryid = searchFirst(keyword,op);
            request.getSession().setAttribute("query_id"+String.valueOf(userId),"");
            request.getSession().setAttribute("query_id"+String.valueOf(userId),queryid);
        }else if ("2".equals(searchType)){
            queryid = (String) request.getSession().getAttribute("query_id"+String.valueOf(userId));
        }
        int currentPage = op.getPageParameter().getCurrentPage();
        op.setQueryid(queryid);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        op.setOrderStr(" order by publishDate desc  ");
        List<OpinionCondition> ops = selectOpListBySearch(op);
        String total = opinionMonService.selectSearchTotal(op);
        Page page = pageService.ESSearchPage(total,currentPage);
        mv.addObject("opinionCondition", ops);
        mv.addObject("opinionPage", page);
        return mv;
    }

    private String searchFirst(String keyword, OpinionCondition op) {
        String  ids = op.getSubjectidArray();
        String[] subject_ids = ids.split(",");
        String query_id = MD5.toMD5("{days:5,user_id:"+op.getUserid()+",subject_ids:"+subject_ids+",phrases:"+keyword);
        JSONObject json1 = new JSONObject();
        String[] str1 = {};/*
        if(keyword.indexOf(" ")!=-1){*/
        str1 = keyword.split(" ");
       /* }else{
            str1 = keyword;
        }*/
        int days = countDate(op.getEndTime(),op.getStartTime());
        json1.put("days", days);
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
            JSONObject ob = (JSONObject) JSON.parse(msg);
            String str = (String)ob.get("query_id");
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    private List<OpinionCondition> selectOpListBySearch(OpinionCondition op) {
        List<OpinionCondition> list = opinionMonService.selectBySearchValue(op);
        return list;
    }


    private int countDate(String endTime, String startTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int days=0;
        try{
            long end = sdf.parse(endTime).getTime();
            long start = sdf.parse(startTime).getTime();
            days=(int)((  end-start)/(24*60*60*1000));
        }catch (Exception e){
            e.printStackTrace();
        }
        return  days;
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

    /*public OpinionSearch JSONChangeType(String str, int userId) {
        OpinionSearch opS = new OpinionSearch();
        try {
            List<OpinionCondition> list = new ArrayList<OpinionCondition>();
            JSONObject ob = (JSONObject) JSON.parse(str);
            JSONObject val1 = (JSONObject) ob.get("results");
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> m = mapper.readValue(JSON.json(val1), Map.class);
            Object o = m.get("docs");
            Object total = m.get("total");
            opS.setTotal(Integer.valueOf(total.toString()));
            List map = mapper.readValue(JSON.json(o), List.class);
            for (Object o1 : map) {
                OpinionCondition op = new OpinionCondition();
                Map<String, Object> m1 = mapper.readValue(JSON.json(o1), Map.class);
                op.setTitle(new String(m1.get("title").toString()));
                op.setWebsite(m1.get("website").toString());
                long publish_date = (Long) m1.get("publish_date");
                op.setPublishdate(new Date(publish_date));
                long download_date = Long.valueOf(m1.get("download_date").toString());
                op.setDownloaddate(new Date(download_date));
                op.setPageid(m1.get("id").toString());
                op.setUrl(m1.get("url").toString());
                int subjectId = Integer.valueOf(m1.get("subjectid").toString());
                OpinionCondition opByPo = opinionMonService.selectByPageId(m1.get("id").toString(), userId, subjectId);
                op.setGroupcount(opByPo.getGroupcount());
                op.setPolarity(opByPo.getPolarity());
                list.add(op);
            }
            opS.setList(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return opS;
    }*/

    @RequestMapping("/opinionRouteReport")
    public ModelAndView opinionRouteReport(HttpServletRequest request,HttpServletResponse response){
        ModelAndView mv = new ModelAndView("newPeony/opinionMon/routeReport");
        User user = (User) request.getSession().getAttribute("userfront");
        int userId = user.getUserId();
        String subjectidStr = request.getParameter("subjectid");
        /*String timeType = request.getParameter("subjectid");*/
        Map<String,List<OpinionCondition>> map = new HashMap<String,List<OpinionCondition>>();
        if(subjectidStr !=null && subjectidStr!=""&&!"".equals(subjectidStr)){
            int subjectid=Integer.valueOf(subjectidStr);
            map = opinionMonService.selectRouteReport(userId,subjectid);
        }
        mv.addObject("routeReports",map);
        return mv;
    }

    /**
     * 排序
     *
     * @param request
     * @return
     */
    private String sortOrder(HttpServletRequest request) {
        String order = " order by ";
        String orderStr = request.getParameter("orderStr");
        String downOrUp = request.getParameter("downOrUp");
        if ("1".equals(orderStr)) {
            if ("up".equals(downOrUp)) {
                order += " publishDate desc,";
            }
            if ("down".equals(downOrUp)) {
                order += " publishDate ,";
            }
        }else
        //关注度
        if ("2".equals(orderStr)) {
            if ("up".equals(downOrUp)) {
                order += " concern desc,";
            }
            if ("down".equals(downOrUp)) {
                order += " concern ,";
            }
        }else
        //汪站等级
        if ("3".equals(orderStr)) {
            if ("up".equals(downOrUp)) {
                order += " siteLevel desc,";
            }
            if ("down".equals(downOrUp)) {
                order += " siteLevel ,";
            }
        }else if("0".equals(orderStr) || null == orderStr ||"".equals(orderStr)){
            order += " publishDate desc,concern desc,siteLevel desc,";
        }
        return order.substring(0, order.length() - 1);
    }


    @RequestMapping(value = "/exportExecle")
    public void exportExecle(@RequestParam(value = "ids", required = true) String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //定义输出流，以便打开保存对话框______________________begin
        OutputStream os = response.getOutputStream();// 取得输出流
        response.reset();// 清空输出流
        User user = (User) request.getSession().getAttribute("userfront");
        int userId = user.getUserId();
        OpinionCondition opinionCondition = new OpinionCondition();
        QueryRequire queryRequire = new QueryRequire();
        String ids[] ={};
        if ("".equals(id) || null == id) {
            opinionCondition = opinionMonService.queryCriteria(request, userId, opinionCondition, queryRequire);
        } else {
            ids = id.split(",");
            String result = "";
            for (int i = 0; i < ids.length; i++) {
                if (i == 0) {
                    result = "'" + ids[i] + "'";
                } else {
                    result = result + "," + "'" + ids[i] + "'";
                }
            }
            opinionCondition.setIdArray(result);
        }
        opinionCondition.setUserid(userId);
        List<OpinionCondition> list = opinionMonService.selectByExportExecle(opinionCondition);
        try {
            toExPort(list, request, response);
        } catch (Exception e) {
            e.printStackTrace();
           /* return "error";*/
        }
/*        return "success";*/
    }

    private void toExPort(List<OpinionCondition> list, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String path = request.getRealPath("") + "/template/opinion.xls";
        InputStream in = new FileInputStream(new File(path));
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //为了转时间
        HSSFWorkbook work = new HSSFWorkbook(in);
        // 得到第1行的第一个单元格的样式
        int k = 2;
        HSSFSheet sheet = work.getSheetAt(0);
        for (OpinionCondition op : list) {
            HSSFRow row = sheet.createRow(k);
            HSSFCell cell = row.createCell(0);
            cell.setCellValue(k - 1);
            cell = row.createCell(1);
            cell.setCellValue(op.getTitle());
            cell = row.createCell(2);
            cell.setCellValue(op.getUrl());
            cell = row.createCell(3);
            cell.setCellValue(op.getPolarity() == -1 ? "负面" : (op.getPolarity() == 1 ? "争议" : "正面"));
            cell = row.createCell(4);
            cell.setCellValue(op.getWebsite());
            cell = row.createCell(5);
            cell.setCellValue("");
            cell = row.createCell(6);
            cell.setCellValue(simpleFormat.format(op.getPublishdate()));
            cell = row.createCell(7);
            cell.setCellValue(op.getGroupcount());
            cell = row.createCell(8);
            cell.setCellValue("");
            k++;
        }
        /****************************输出流*****************************************/
        // 以流的形式下载文件。
        InputStream fis = new BufferedInputStream(new FileInputStream(path));
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        // 清空response
        response.reset();
        OutputStream fos = response.getOutputStream();
        String filename = simpleFormat.format(new Date());
        response.setContentType("application/vnd.ms-excel;charset=gb2312");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename) + ".xls");
       /* response.addHeader("Content-Length", "" + file.length());*/
        work.write(fos);
    }


    public static void main(String[] args){
        String a= "sadasdas       asdasdsa         sdfdsfs         sdgfdfg         dfgfdgd         dgrfdfgdf   dfgdgfd dfgdfg     dfgdfgd";
        String b = a.replaceAll(" +", "<br>     ");
        System.out.println(b);
    }

}
