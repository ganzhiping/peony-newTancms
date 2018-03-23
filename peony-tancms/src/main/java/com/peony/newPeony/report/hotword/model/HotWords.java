package com.peony.newPeony.report.hotword.model;

import java.util.Date;

/**
 * Created by Administrator on 2017/9/12.
 */
public class HotWords {
    private String hotwords;
    private String starTime;
    private String endTime;
    private int cnt;
    private int subjectid;
    private int userid;
    private String colorView;
    private Date reportTime;

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public String getColorView() {
        return colorView;
    }

    public void setColorView(String colorView) {
        this.colorView = colorView;
    }

    public String getHotwords() {
        return hotwords;
    }

    public void setHotwords(String hotwords) {
        this.hotwords = hotwords;
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

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public int getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(int subjectid) {
        this.subjectid = subjectid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
