package com.peony.newPeony.report.sentiment.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/8/30.
 */
public class Sentiment implements Serializable{
    private int id;
    private Date reportTime;
    private String starTime;
    private String endTime;
    private int userid;
    private int subjectid;
    private int sentiment;
    private int cnt;//总数
    private int negativeNo;//负面数量
    private int noneNegativeNo;//非负面数量
    private String negativePercent;//负面百分比
    private String noneNegativePercent;//非负面百分比
    private String getTime;

    public String getGetTime() {
        return getTime;
    }

    public void setGetTime(String getTime) {
        this.getTime = getTime;
    }

    public String getStarTime() {
        return starTime;
    }

    public void setStarTime(String starTime) {
        this.starTime = starTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getNegativeNo() {
        return negativeNo;
    }

    public void setNegativeNo(int negativeNo) {
        this.negativeNo = negativeNo;
    }

    public int getNoneNegativeNo() {
        return noneNegativeNo;
    }

    public void setNoneNegativeNo(int noneNegativeNo) {
        this.noneNegativeNo = noneNegativeNo;
    }

    public String getNegativePercent() {
        return negativePercent;
    }

    public void setNegativePercent(String negativePercent) {
        this.negativePercent = negativePercent;
    }

    public String getNoneNegativePercent() {
        return noneNegativePercent;
    }

    public void setNoneNegativePercent(String noneNegativePercent) {
        this.noneNegativePercent = noneNegativePercent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(int subjectid) {
        this.subjectid = subjectid;
    }

    public int getSentiment() {
        return sentiment;
    }

    public void setSentiment(int sentiment) {
        this.sentiment = sentiment;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }
}
