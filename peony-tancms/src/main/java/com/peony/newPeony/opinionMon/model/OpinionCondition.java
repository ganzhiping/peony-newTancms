package com.peony.newPeony.opinionMon.model;

import com.peony.core.base.pojo.BasePojo;

import java.util.Date;

/**
 * 查询条件
 * Created by gzp on 2017/8/16.
 */
public class OpinionCondition extends BasePojo {
    //查询条件
    private String sqlWh;
    private String source;//文章来源
    private Integer polarity;//情感属性
    private String webLevel;//网站等级
    private String startTime;//开始时间
    private String endTime;//结束时间
    private String orderStr;//排序
    private Integer isRead;//是否已读
    private String follow;//关注度
    private String timeStr;//时间判断1 今天,2 24小时,3 2天,4 一周,5 一个月
    //排序
    private String timeOrder;//时间排序
    private String followOrder;//关注度排序
    private String webLevelOrder;//网站等级排序
    //排序
    private String topTime;
    private String content;
    private String isConcern;
    private String keywords;
    private String author;
    private int report;
    private int siteLevel;//网站等级
    private String queryid;
    private String es_relevance;
    private String highligiht;
    private String hitKeywords;
    private int is_concern;

    /**
     * 情感极性变化 start
     * @return
     */
    private int p_id;
    private int p_feedp;
    private int p_isused;
    private int p_originp;
    private Date p_feedT;
    private String p_subjectid;
    private String p_pageid;

    public String getHitKeywords() {
        return hitKeywords;
    }

    public void setHitKeywords(String hitKeywords) {
        this.hitKeywords = hitKeywords;
    }

    public int getIs_concern() {
        return is_concern;
    }

    public void setIs_concern(int is_concern) {
        this.is_concern = is_concern;
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public int getP_feedp() {
        return p_feedp;
    }

    public void setP_feedp(int p_feedp) {
        this.p_feedp = p_feedp;
    }

    public int getP_isused() {
        return p_isused;
    }

    public void setP_isused(int p_isused) {
        this.p_isused = p_isused;
    }

    public int getP_originp() {
        return p_originp;
    }

    public void setP_originp(int p_originp) {
        this.p_originp = p_originp;
    }

    public Date getP_feedT() {
        return p_feedT;
    }

    public void setP_feedT(Date p_feedT) {
        this.p_feedT = p_feedT;
    }

    public String getP_subjectid() {
        return p_subjectid;
    }

    public void setP_subjectid(String p_subjectid) {
        this.p_subjectid = p_subjectid;
    }

    public String getP_pageid() {
        return p_pageid;
    }

    public void setP_pageid(String p_pageid) {
        this.p_pageid = p_pageid;
    }
    //end

    public String getEs_relevance() {
        return es_relevance;
    }

    public void setEs_relevance(String es_relevance) {
        this.es_relevance = es_relevance;
    }

    public String getHighligiht() {
        return highligiht;
    }

    public void setHighligiht(String highligiht) {
        this.highligiht = highligiht;
    }

    public String getQueryid() {
        return queryid;
    }

    public void setQueryid(String queryid) {
        this.queryid = queryid;
    }

    public int getSiteLevel() {
        return siteLevel;
    }

    public void setSiteLevel(int siteLevel) {
        this.siteLevel = siteLevel;
    }

    public int getReport() {
        return report;
    }

    public void setReport(int report) {
        this.report = report;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsConcern() {
        return isConcern;
    }

    public void setIsConcern(String isConcern) {
        this.isConcern = isConcern;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public String getTopTime() {
        return topTime;
    }

    public void setTopTime(String topTime) {
        this.topTime = topTime;
    }

    public String getSqlWh() {
        return sqlWh;
    }

    public void setSqlWh(String sqlWh) {
        this.sqlWh = sqlWh;
    }

    public String getTimeOrder() {
        return timeOrder;
    }

    public void setTimeOrder(String timeOrder) {
        this.timeOrder = timeOrder;
    }

    public String getFollowOrder() {
        return followOrder;
    }

    public void setFollowOrder(String followOrder) {
        this.followOrder = followOrder;
    }

    public String getWebLevelOrder() {
        return webLevelOrder;
    }

    public void setWebLevelOrder(String webLevelOrder) {
        this.webLevelOrder = webLevelOrder;
    }

    //基本信息
    private String  id;
    private Integer subjectid;
    private String  pageid;
    private Date    updatetime;
    private String  idArray;
    private String  url;
    private String  website;
    private Date    downloaddate;
    private String  title;
    private String  summary;
    private Integer clickcount;
    private Integer replycount;
    private Integer forwardcount;
    private Integer type;
    private Date    publishdate;
    private Integer newslevel;
    private Integer groupcount;
    // 不一样 线上是String
    private String  groupseedid;
    private String  isMaininfo;
    private String  beginTime;
    private String  time;                 // 用于回显时间点
    private Integer userid;
    private String  overTime;             // 导出结束时间
    private Integer newslev;              // 区分定制和境外舆情
    private String  newslevelConditions;
    private Integer count;
    private String  subjectidArray;
    private Integer state;
    private int  pageNum;
    private int pageNoByTitle;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageNoByTitle() {
        return pageNoByTitle;
    }

    public void setPageNoByTitle(int pageNoByTitle) {
        this.pageNoByTitle = pageNoByTitle;
    }

    //基本信息
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(Integer subjectid) {
        this.subjectid = subjectid;
    }

    public String getPageid() {
        return pageid;
    }

    public void setPageid(String pageid) {
        this.pageid = pageid;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getIdArray() {
        return idArray;
    }

    public void setIdArray(String idArray) {
        this.idArray = idArray;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Date getDownloaddate() {
        return downloaddate;
    }

    public void setDownloaddate(Date downloaddate) {
        this.downloaddate = downloaddate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getClickcount() {
        return clickcount;
    }

    public void setClickcount(Integer clickcount) {
        this.clickcount = clickcount;
    }

    public Integer getReplycount() {
        return replycount;
    }

    public void setReplycount(Integer replycount) {
        this.replycount = replycount;
    }

    public Integer getForwardcount() {
        return forwardcount;
    }

    public void setForwardcount(Integer forwardcount) {
        this.forwardcount = forwardcount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getPublishdate() {
        return publishdate;
    }

    public void setPublishdate(Date publishdate) {
        this.publishdate = publishdate;
    }

    public Integer getNewslevel() {
        return newslevel;
    }

    public void setNewslevel(Integer newslevel) {
        this.newslevel = newslevel;
    }

    public Integer getGroupcount() {
        return groupcount;
    }

    public void setGroupcount(Integer groupcount) {
        this.groupcount = groupcount;
    }

    public String getGroupseedid() {
        return groupseedid;
    }

    public void setGroupseedid(String groupseedid) {
        this.groupseedid = groupseedid;
    }

    public String getIsMaininfo() {
        return isMaininfo;
    }

    public void setIsMaininfo(String isMaininfo) {
        this.isMaininfo = isMaininfo;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getOverTime() {
        return overTime;
    }

    public void setOverTime(String overTime) {
        this.overTime = overTime;
    }

    public Integer getNewslev() {
        return newslev;
    }

    public void setNewslev(Integer newslev) {
        this.newslev = newslev;
    }

    public String getNewslevelConditions() {
        return newslevelConditions;
    }

    public void setNewslevelConditions(String newslevelConditions) {
        this.newslevelConditions = newslevelConditions;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getSubjectidArray() {
        return subjectidArray;
    }

    public void setSubjectidArray(String subjectidArray) {
        this.subjectidArray = subjectidArray;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    //查询条件
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getPolarity() {
        return polarity;
    }

    public void setPolarity(Integer polarity) {
        this.polarity = polarity;
    }

    public String getWebLevel() {
        return webLevel;
    }

    public void setWebLevel(String webLevel) {
        this.webLevel = webLevel;
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

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public String getFollow() {
        return follow;
    }

    public void setFollow(String follow) {
        this.follow = follow;
    }
}
