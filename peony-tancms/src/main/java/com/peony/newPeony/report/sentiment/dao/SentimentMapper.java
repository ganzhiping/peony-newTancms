package com.peony.newPeony.report.sentiment.dao;

import com.peony.newPeony.report.sentiment.model.Sentiment;

import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 */
public interface SentimentMapper {
    String selectReportByNegative(Sentiment sentiment);

    String selectReportByCnt(Sentiment sentiment);

    String selectAllWebData(Sentiment userId);

    /*String selectTodayData(Sentiment sentiment);*/

    String selectSubjectWebNum(Sentiment sentiment);

    List<Sentiment> selectDevelopRoute(Sentiment sentiment);
}
