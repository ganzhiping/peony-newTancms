package com.peony.newPeony.opinionMon.model;

/**
 * Created by Administrator on 2017/8/25.
 */
public class QueryRequire {
    private int userid;
    private int source;
    private int polarity;
    private int weblevel;
    private int isRead;
    private String startTime;
    private String endTime;
    private int dataType;

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getPolarity() {
        return polarity;
    }

    public void setPolarity(int polarity) {
        this.polarity = polarity;
    }

    public int getWeblevel() {
        return weblevel;
    }

    public void setWeblevel(int weblevel) {
        this.weblevel = weblevel;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
