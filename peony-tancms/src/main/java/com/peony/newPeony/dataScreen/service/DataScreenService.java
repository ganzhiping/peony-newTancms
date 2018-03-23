package com.peony.newPeony.dataScreen.service;

import com.peony.newPeony.report.hotword.model.HotWords;
import com.peony.newPeony.report.media.model.MediaUtil;
import com.peony.newPeony.report.region.model.RegionUtil;
import com.peony.newPeony.report.sentiment.model.Sentiment;
import com.peony.newPeony.report.subject.model.SubjectUtil;
import com.peony.newPeony.report.website.model.WebSiteUtil;
import com.peony.newPeony.special.model.SubjectInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/1.
 */
public interface DataScreenService {
    Sentiment selectBySentimentAll(int userId, String subjectid, int timeType);

    Map<String,String> selectAllWebNum(int userId);

    List<RegionUtil> selectRegionReport(int userId, String subjectid, int timeType);

    List<MediaUtil> selectMediaReport(int userId, String subjectid, int timeType);

    List<WebSiteUtil> selectWebsiteReport(int userId, String subjectid, int timeType);

    List<SubjectInfo> selectSubjectIdByUserid(int userId);

    Map<String,List<SubjectUtil>> selectSubjectReport(int userId, int timeType, List<SubjectInfo> listSubjectIds);

    String selectSubjectAllWebNum(int timeType, String subjectid, int userId);

    List<HotWords> selectHotWords(int userId, String subjectid, int timeType);

    List<Sentiment> selectDevelopRoute(int userId, String subjectid, int timeType);

    String selectAllWeb(int userId, String timeType);
}
