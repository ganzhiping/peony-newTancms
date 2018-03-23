package com.peony.newPeony.dataScreen.service;

import com.peony.newPeony.report.hotword.dao.HotWordsMapper;
import com.peony.newPeony.report.hotword.model.HotWords;
import com.peony.newPeony.report.media.dao.MediaMapper;
import com.peony.newPeony.report.media.model.MediaUtil;
import com.peony.newPeony.report.region.dao.RegionReportMapper;
import com.peony.newPeony.report.region.model.RegionUtil;
import com.peony.newPeony.report.sentiment.dao.SentimentMapper;
import com.peony.newPeony.report.sentiment.model.Sentiment;
import com.peony.newPeony.report.subject.dao.SubjectReportMapper;
import com.peony.newPeony.report.subject.model.SubjectUtil;
import com.peony.newPeony.report.website.dao.WebsiteMapper;
import com.peony.newPeony.report.website.model.WebSiteUtil;
import com.peony.newPeony.special.dao.SubjectInfoMapper;
import com.peony.newPeony.special.model.SubjectInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/9/1.
 */
@Service
public class DataScreenServiceImpl implements DataScreenService {
    @Resource
    private SentimentMapper sentimentMapper;
    @Resource
    private RegionReportMapper regionReportMapper;
    @Resource
    private MediaMapper mediaMapper;
    @Resource
    private WebsiteMapper websiteMapper;
    @Resource
    private SubjectInfoMapper subjectInfoMapper;
    @Resource
    private SubjectReportMapper subjectReportMapper;
    @Resource
    private HotWordsMapper hotWordsMapper;
    /**
     * 计算总数，和今日数量
     * @param userId
     */
    @Override
    public  Map<String,String> selectAllWebNum(int userId) {
        Map<String,String> map = new HashMap<String,String>();
        Sentiment sentiment = new Sentiment();
        sentiment.setUserid(userId);
        String[] str = timeJudgement(4);
        sentiment.setStarTime(str[1]);
        sentiment.setEndTime(str[0]);
        String allData = sentimentMapper.selectAllWebData(sentiment);
        //格式化全网舆情数据信息总量(###,###,###)
        String webData = "";
        if(allData!=null&&!"".equals(allData)){
            BigDecimal decimal = new BigDecimal(String.valueOf(allData));
            DecimalFormat format = new DecimalFormat("###,###");
            format.setRoundingMode(RoundingMode.HALF_UP);
            webData = format.format(decimal);
        }
        sentiment = new Sentiment();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sentiment.setEndTime(sdf.format(calendar.getTime()));
        //设置时间为当天的00:00:00
        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        System.out.println(sdf.format(calendar.getTime()));
        sentiment.setStarTime(sdf.format(calendar.getTime()));
        sentiment.setUserid(userId);
        String todayData = sentimentMapper.selectAllWebData(sentiment);
        map.put("webData",webData);
        map.put("todayData",String.valueOf(todayData));
        return map;
    }

    @Override
    public String selectAllWeb(int userId, String timeType) {
        Sentiment sentiment = new Sentiment();
        sentiment.setUserid(userId);
        String[] str = timeJudgement(Integer.valueOf(timeType == null ? "4" : timeType));
        sentiment.setStarTime(str[1]);
        sentiment.setEndTime(str[0]);
        String allData = sentimentMapper.selectAllWebData(sentiment);
        return allData;
    }
    /**
     * 全部专题---计算各省媒体总数
     * @param userId
     * @param subjectid
     * @param timeType
     * @return
     */
    @Override
    public List<RegionUtil> selectRegionReport(int userId, String subjectid, int timeType) {
        RegionUtil regionUtil = new RegionUtil();
        String[] str = timeJudgement(timeType);
        regionUtil.setEndTime(str[0]);
        regionUtil.setStarTime(str[1]);
        if(subjectid!=null&&!"".equals(subjectid)){
            regionUtil.setSubjectid(Integer.valueOf(subjectid));
        }
        regionUtil.setUserid(userId);
        List<RegionUtil> regionUtils = regionReportMapper.selectRegionReportCount(regionUtil);
        return regionUtils;
    }

    /**
     * 媒体总数，求负面的百分比
     * @param userId
     * @param subjectid
     * @param timeType
     * @return
     */
    @Override
    public List<MediaUtil> selectMediaReport(int userId, String subjectid, int timeType) {
        MediaUtil mediaUtil = new MediaUtil();
        String[] str = timeJudgement(timeType);
        mediaUtil.setEndTime(str[0]);
        mediaUtil.setStarTime(str[1]);
        if(subjectid!=null&&!"".equals(subjectid)){
            mediaUtil.setSubjectid(Integer.valueOf(subjectid));
        }
        mediaUtil.setUserid(userId);
        List<MediaUtil> mediaUtils = mediaMapper.selectMediaReportCount(mediaUtil);
        return mediaUtils;
    }

    /**
     * 各媒体的负面数量，例如新浪等，取负面数量前十 的数据
     * @param userId
     * @param subjectid
     * @param timeType
     * @return
     */
    @Override
    public List<WebSiteUtil> selectWebsiteReport(int userId, String subjectid, int timeType) {
        WebSiteUtil webSiteUtil = new WebSiteUtil();
        webSiteUtil.setUserid(userId);
        if(subjectid!=null&&!"".equals(subjectid)){
            webSiteUtil.setSubjectid(Integer.valueOf(subjectid));
        }
        String[] str = timeJudgement(timeType);
        webSiteUtil.setEndTime(str[0]);
        webSiteUtil.setStarTime(str[1]);
        List<WebSiteUtil> webSiteUtils = websiteMapper.selectWebsiteReport(webSiteUtil);
        return webSiteUtils;
    }

    /**
     * 查询所有专题Id
     * @param userId
     * @return
     */
    @Override
    public List<SubjectInfo> selectSubjectIdByUserid(int userId) {
        return subjectInfoMapper.selectSubjectByUserId(userId);
    }

    /**
     * 方案，chart4,小时与日期
     * @param userId
     * @param timeType
     * @param listSubjectIds
     * @return
     */
    @Override
    public Map<String, List<SubjectUtil>> selectSubjectReport(int userId, int timeType, List<SubjectInfo> listSubjectIds) {
        Map<String, List<SubjectUtil>> map = new HashMap<String, List<SubjectUtil>>();
        String[] str = timeJudgement(timeType);
        for(SubjectInfo info : listSubjectIds){
            SubjectUtil  util = new SubjectUtil();
            String subjectName = info.getName();
            int subjectid = info.getId();
            util.setUserid(userId);
            util.setEndTime(str[0]);
            util.setStarTime(str[1]);
            util.setSubjectid(subjectid);
            List<SubjectUtil> subjectUtil = null;
            if(timeType==0||timeType==3||timeType==4){
                subjectUtil = subjectReportMapper.selectSubjectDateType(util);
                if(subjectUtil.size()!=0){
                    subjectUtil = validateTime(subjectUtil,str,timeType,subjectid);
                }

            }else{
                subjectUtil = subjectReportMapper.selectSubjectHourType(util);
                if(subjectUtil.size()!=0){
                    subjectUtil = valihourTime(subjectUtil,str,timeType,subjectid);
                }

            }
            if(subjectUtil != null && !"".equals(subjectUtil)&&subjectUtil.size()!=0){
                map.put(subjectName+"&$&"+subjectid,subjectUtil);
            }
        }
        return map;
    }

    @Override
    public String selectSubjectAllWebNum(int timeType, String subjectid, int userId) {
        Sentiment sentiment = new Sentiment();
        sentiment.setSubjectid(Integer.valueOf(subjectid==null?"0":subjectid));
        String[] str = timeJudgement(3);
        sentiment.setStarTime(str[1]);
        sentiment.setEndTime(str[0]);
        sentiment.setUserid(userId);
        String num = sentimentMapper.selectSubjectWebNum(sentiment);
        return num;
    }

    @Override
    public List<HotWords> selectHotWords(int userId, String subjectid, int timeType) {
        HotWords hotWords = new HotWords();
        hotWords.setUserid(userId);
        hotWords.setSubjectid(Integer.valueOf(subjectid));
        String[] str = timeJudgement(timeType);
        hotWords.setEndTime(str[0]);
        hotWords.setStarTime(str[1]);
        List<HotWords> list = hotWordsMapper.selectSubjectHotWords(hotWords);
        List<HotWords> listFinal = addChangeColor(list);
        return listFinal;
    }

    private List<HotWords> addChangeColor(List<HotWords> list) {
        List<HotWords> list_color = new ArrayList<>();
        String[] color = {"red","green","blue","yellow","#BC8F8F","#FAF0E6","#FFD700","#00FF00",
                "#20B2AA"};
        for(HotWords hot : list){
            int m = (int) ((Math.random() * color.length));
            hot.setColorView(color[m]);
            list_color.add(hot);
        }
        return list_color;
    }

    @Override
    public List<Sentiment> selectDevelopRoute(int userId, String subjectid, int timeType) {
        Sentiment sentiment = new Sentiment();
        sentiment.setUserid(userId);
        sentiment.setSubjectid(Integer.valueOf(subjectid));
        String[] str = timeJudgement(3);
        sentiment.setStarTime(str[1]);
        sentiment.setEndTime(str[0]);
        List<Sentiment> list = sentimentMapper.selectDevelopRoute(sentiment);
        return list;
    }




    public  int daysBetween(Date smdate,Date bdate) throws Exception {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        smdate=sdf.parse(sdf.format(smdate));
        bdate=sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));
    }
    /**
     * 日期补全
     * @param subjectUtil
     * @param str
     * @param timeType
     * @param subjectid
     * @return
     */
    private List<SubjectUtil> validateTime(List<SubjectUtil> subjectUtil,String[] str,int timeType,int subjectid) {
        String starTime = str[1];
        //现在时间
        String endTime=str[0];
        List<SubjectUtil> newList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //一个月
        Calendar calendar = Calendar.getInstance();
        try{
            String date = sdf.format(sdf2.parse(endTime));
            calendar.setTime(sdf2.parse(starTime));
           /* int timeLength =daysBetween(sdf2.parse(starTime),sdf2.parse(endTime));*/
            int listNum =0;
            for(int i=0;i>=0;i++){
                String oldTime = sdf.format(calendar.getTime());
                if(Integer.valueOf(oldTime)>Integer.valueOf(date)){
                    break;
                }
                String newTime = sdf.format(subjectUtil.get(listNum).getReportTime());
                if(oldTime.equals(newTime)){
                    subjectUtil.get(listNum).setReTime(sdf1.format(subjectUtil.get(listNum).getReportTime()));
                    newList.add(subjectUtil.get(listNum));
                    if(listNum < subjectUtil.size()-1){
                        listNum ++;
                    }
                }else{
                    SubjectUtil s = new SubjectUtil();
                    s.setReTime(sdf1.format(calendar.getTime()));
                    s.setCnt(0);
                    s.setSubjectid(subjectid);
                    newList.add(s);
                }
                calendar.add(Calendar.DATE,1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return newList;
    }

    /**
     * 小时补全
     * @param subjectUtil
     * @param str
     * @param timeType
     * @param subjectid
     * @return
     */
    private List<SubjectUtil> valihourTime(List<SubjectUtil> subjectUtil,String[] str,int timeType,int subjectid) {
        //7天或30天前的时间
        String starTime = str[1];
        //现在时间
        String endTime=str[0];
        List<SubjectUtil> newList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
        SimpleDateFormat sdf1 = new SimpleDateFormat("MMdd HH");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        try{
            String date = sdf.format(sdf2.parse(endTime));
            calendar.setTime(sdf2.parse(starTime));
            int listNum =0;
            for(int i=0;i>=0;i++){
                String oldTime = sdf.format(calendar.getTime());
                if(Integer.valueOf(oldTime)>Integer.valueOf(date)){
                    break;
                }
                String newTime = sdf.format(subjectUtil.get(listNum).getReportTime());
                if(oldTime.equals(newTime)){
                    subjectUtil.get(listNum).setReTime(sdf1.format(subjectUtil.get(listNum).getReportTime()));
                    newList.add(subjectUtil.get(listNum));
                    if(listNum <subjectUtil.size()-1){
                        listNum ++;
                    }
                }else{
                    SubjectUtil s = new SubjectUtil();
                    s.setReportTime(calendar.getTime());
                    s.setReTime(sdf1.format(calendar.getTime()));
                    s.setCnt(0);
                    s.setSubjectid(subjectid);
                    newList.add(s);
                }
                calendar.add(Calendar.HOUR,1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return newList;
    }


    /**
     * char5
     * @param userId
     * @param subjectid
     * @param timeType
     * @return
     */
    @Override
    public Sentiment selectBySentimentAll(int userId, String subjectid, int timeType) {
        Sentiment sentiment = new Sentiment();
        sentiment.setUserid(userId);
        String[] str = timeJudgement(timeType);
        sentiment.setEndTime(str[0]);
        sentiment.setStarTime(str[1]);
        if (subjectid != "" && !"".equals(subjectid) && null != subjectid) {
            sentiment.setSubjectid(Integer.valueOf(subjectid));
        }
        String s = sentimentMapper.selectReportByNegative(sentiment);
        int negativeNo = Integer.valueOf(s==null?"0":s);
        String s1 = sentimentMapper.selectReportByCnt(sentiment);
        int cnt = Integer.valueOf(s1==null?"0":s1);
        if(cnt!=0){
            int noneNegativeNo = cnt - negativeNo;
            sentiment.setCnt(cnt);
            sentiment.setNegativeNo(negativeNo);
            sentiment.setNoneNegativeNo(noneNegativeNo);
            float percent = (negativeNo / cnt)*100;
            float nonePercent = (noneNegativeNo / cnt)*100;
            sentiment.setNegativePercent(degreePercent(nonePercent));
            sentiment.setNegativePercent(degreePercent(percent));
        }
        return sentiment;
    }

    /**
     * 判断时间类型
     * @param timeType
     * @return
     */
    private String[] timeJudgement(int timeType) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateNow = new Date();
        Calendar calendar = Calendar.getInstance();
        String[] str = new String[2];
        str[0] = (sdf.format(calendar.getTime()));
        switch (timeType) {
            case 0:
                calendar.add(Calendar.MONTH, -1);
                str[1]=sdf.format(calendar.getTime());
                break;
            case 1:
                calendar.set(Calendar.HOUR, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                str[1]=sdf.format(calendar.getTime());
                break;
            case 2:
                calendar.add(Calendar.DAY_OF_MONTH, -3);
                str[1]=sdf.format(calendar.getTime());
                break;
            case 3:
                calendar.add(Calendar.WEEK_OF_YEAR, -1);
                str[1]=sdf.format(calendar.getTime());
                break;
            case 4:
                calendar.add(Calendar.MONTH, -1);
                str[1]=sdf.format(calendar.getTime());
                break;
        }
        return str;
    }

    /**
     * 求百分比精度
     * @param percent
     * @return
     */
    public String degreePercent(float percent) {
        BigDecimal decimal = new BigDecimal(String.valueOf(percent));
        DecimalFormat format = new DecimalFormat("####");
        format.setRoundingMode(RoundingMode.HALF_UP);
        float f = Float.parseFloat(format.format(decimal.doubleValue()));
        String ret = String.valueOf(f).substring(0, String.valueOf(f).indexOf("."));
        return ret;
    }
}
