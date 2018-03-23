package com.peony.newPeony.dataScreen.controller;

import com.peony.newPeony.dataScreen.service.DataScreenService;
import com.peony.newPeony.report.hotword.model.HotWords;
import com.peony.newPeony.report.media.model.MediaUtil;
import com.peony.newPeony.report.region.model.RegionUtil;
import com.peony.newPeony.report.sentiment.model.Sentiment;
import com.peony.newPeony.report.subject.model.SubjectUtil;
import com.peony.newPeony.report.website.model.WebSiteUtil;
import com.peony.newPeony.special.model.SubjectInfo;
import com.peony.peonyfront.login.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/1.
 */
@Controller
@RequestMapping("/dataScreen")
public class DataScreenController {
    @Autowired
    private DataScreenService dataScreenService;
    @RequestMapping("/dataScreenMain")
    public ModelAndView dataScreenMain(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv = new ModelAndView("/newPeony/dataScreen/dataScreenMain");
        int userId = getUserId(request);
        //计算总数，以mdyq_sentiment_report(情感值总量)为准
        List<SubjectInfo> listSubjectIds = dataScreenService.selectSubjectIdByUserid(userId);
        Map<String,String> map = dataScreenService.selectAllWebNum(userId);
        mv.addObject("allWebData",map);
        mv.addObject("subjectValues",listSubjectIds);
        return mv;
    }

 @RequestMapping("/dataScreenAllNum")
    public @ResponseBody String dataScreenAllNum(HttpServletRequest request, HttpServletResponse response){
        int userId = getUserId(request);
        String timeType = request.getParameter("timeType");
       String map = dataScreenService.selectAllWeb(userId,timeType);
        return map;
    }

    @RequestMapping("/dataScreenMainView")
    public ModelAndView dataScreenMainView(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv = new ModelAndView("/newPeony/dataScreen/dataScreenUser");
        int userId = getUserId(request);
        int timeType = Integer.valueOf(request.getParameter("timeType")==""?"0":request.getParameter("timeType")==null?"0":request.getParameter("timeType"));
        String subjectid= request.getParameter("subjectid");
        String subjectWebNum = dataScreenService.selectSubjectAllWebNum(timeType,subjectid,userId);
        mv.addObject("subjectWebNum",subjectWebNum);
        return mv;
    }
    @RequestMapping("/dataScreenFirstView")
    public ModelAndView dataScreenFirstView(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv = new ModelAndView();
        String flag = request.getParameter("flagVal");
        int userId = getUserId(request);
        if("true".equals(flag)){
            mv.setViewName("/newPeony/dataScreen/dataScreenFirstUser");
            Map<String,String> map = dataScreenService.selectAllWebNum(userId);
            mv.addObject("allWebData",map);
        }else{
            mv.setViewName("/newPeony/dataScreen/dataScreenFirstSubject");
        }
        return mv;
    }

    @RequestMapping("/dataScreenSubjectView")
    public ModelAndView dataScreenSubjectView(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv = new ModelAndView("/newPeony/dataScreen/dataScreenSubject");
        int userId = getUserId(request);
        String subjectid = request.getParameter("subjectid");
        int timeType = Integer.valueOf(request.getParameter("timeType")==""?"3":request.getParameter("timeType")==null?"3":request.getParameter("timeType").equals("")?"3":request.getParameter("timeType"));
        List<HotWords> list = dataScreenService.selectHotWords(userId,subjectid,timeType);
        String subjectWebNum = dataScreenService.selectSubjectAllWebNum(timeType,subjectid,userId);
        mv.addObject("subjectWebNum",subjectWebNum);
        mv.addObject("dataScreenHotWords",list);
        return mv;
    }

    @RequestMapping("/dataScreenSentiment")
    public @ResponseBody Sentiment dataScreenSentiment(HttpServletRequest request, HttpServletResponse response){
        int userId = getUserId(request);
        String subjectid = request.getParameter("subjectid");
        //1:今日，2:3天，3：一周，4：一个月
        int timeType = Integer.valueOf(request.getParameter("timeType")==""?"0":request.getParameter("timeType")==null?"0":request.getParameter("timeType"));
        //情感值负面以及非负面数值以及百分比
        Sentiment sentiment = dataScreenService.selectBySentimentAll(userId,subjectid,timeType);
        return sentiment;
    }

    @RequestMapping("/dataScreenRegion")
    public @ResponseBody List<RegionUtil> dataScreenRegion(HttpServletRequest request, HttpServletResponse response){
        int userId = getUserId(request);
        String subjectid = request.getParameter("subjectid");
        int timeType = Integer.valueOf(request.getParameter("timeType")==""?"0":request.getParameter("timeType")==null?"0":request.getParameter("timeType"));
        List<RegionUtil> list = dataScreenService.selectRegionReport(userId,subjectid,timeType);
     return list;
    }

    @RequestMapping("/dataScreenMedia")
    public @ResponseBody List<MediaUtil> dataScreenMedia(HttpServletRequest request, HttpServletResponse response){
        int userId = getUserId(request);
        String subjectid = request.getParameter("subjectid");
        int timeType = Integer.valueOf(request.getParameter("timeType")==""?"0":request.getParameter("timeType")==null?"0":request.getParameter("timeType"));
        List<MediaUtil> list = dataScreenService.selectMediaReport(userId,subjectid,timeType);
     return list;
    }

    @RequestMapping("/dataScreenWebsite")
    public @ResponseBody List<WebSiteUtil> dataScreenWebsite(HttpServletRequest request, HttpServletResponse response){
        int userId = getUserId(request);
        String subjectid = request.getParameter("subjectid");
        int timeType = Integer.valueOf(request.getParameter("timeType")==""?"0":request.getParameter("timeType")==null?"0":request.getParameter("timeType").equals("")?"0":request.getParameter("timeType"));
        List<WebSiteUtil> list = dataScreenService.selectWebsiteReport(userId,subjectid,timeType);
     return list;

    }

    @RequestMapping("/dataScreenSubject")
    public @ResponseBody Map<String,List<SubjectUtil>> dataScreenSubject(HttpServletRequest request, HttpServletResponse response){
        int userId = getUserId(request);
        int timeType = Integer.valueOf(request.getParameter("timeType")==""?"0":request.getParameter("timeType")==null?"0":request.getParameter("timeType").equals("")?"0":request.getParameter("timeType"));
        List<SubjectInfo> listSubjectIds = dataScreenService.selectSubjectIdByUserid(userId);
        Map<String,List<SubjectUtil>> map = dataScreenService.selectSubjectReport(userId,timeType,listSubjectIds);
        return map;
    }

  /*  @RequestMapping("/dataScreenHotWords")
    public @ResponseBody List<HotWords> dataScreenHotWords(HttpServletRequest request, HttpServletResponse response){
        int userId = getUserId(request);
        String subjectid = request.getParameter("subjectid");
        int timeType = Integer.valueOf(request.getParameter("timeType")==""?"0":request.getParameter("timeType")==null?"0":request.getParameter("timeType").equals("")?"0":request.getParameter("timeType"));
        List<HotWords> list = dataScreenService.selectHotWords(userId,subjectid,timeType);
        return list;
    }*/

    @RequestMapping("/dataScreenDevelopRoute")
    public @ResponseBody List<Sentiment> dataScreenDevelopRoute(HttpServletRequest request, HttpServletResponse response){
        int userId = getUserId(request);
        String subjectid = request.getParameter("subjectid");
        int timeType = Integer.valueOf(request.getParameter("timeType")==""?"0":request.getParameter("timeType")==null?"0":request.getParameter("timeType").equals("")?"0":request.getParameter("timeType"));
        List<Sentiment> list = dataScreenService.selectDevelopRoute(userId,subjectid,timeType);
        return list;
    }

    private int getUserId(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("userfront");
        int userId = user.getUserId();
        return  userId;
    }


}
