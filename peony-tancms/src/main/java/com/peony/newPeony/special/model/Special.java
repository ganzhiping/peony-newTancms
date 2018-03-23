package com.peony.newPeony.special.model;

import com.peony.peonyfront.subject.model.Subject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/28.
 */
public class Special implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer subjectid;
    private String name;
    private String keywords;
    private String rejectflag;
    private Integer regionid;
    private Integer mainbodyid;
    private Integer eventid;
    private String main;
    private String mainStr;
    private String qxx;
    private String qxxStr;
    private String qxx2textarea;
    private String glc;
    private String glcStr;
    private String glc2textarea;
    private String area;
    private String areaStr;
    private int areaId;
    private int glcId;
    private int qxxId;
    private int mainId;
    private String regionName;
    private int regionId;
    private String regionAbbr;
    private int level;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getRegionAbbr() {
        return regionAbbr;
    }

    public void setRegionAbbr(String regionAbbr) {
        this.regionAbbr = regionAbbr;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public int getGlcId() {
        return glcId;
    }

    public void setGlcId(int glcId) {
        this.glcId = glcId;
    }

    public int getQxxId() {
        return qxxId;
    }

    public void setQxxId(int qxxId) {
        this.qxxId = qxxId;
    }

    public int getMainId() {
        return mainId;
    }

    public void setMainId(int mainId) {
        this.mainId = mainId;
    }

    public String getMainStr() {
        return mainStr;
    }

    public void setMainStr(String mainStr) {
        this.mainStr = mainStr;
    }

    public String getQxxStr() {
        return qxxStr;
    }

    public void setQxxStr(String qxxStr) {
        this.qxxStr = qxxStr;
    }

    public String getGlcStr() {
        return glcStr;
    }

    public void setGlcStr(String glcStr) {
        this.glcStr = glcStr;
    }

    public String getAreaStr() {
        return areaStr;
    }

    public void setAreaStr(String areaStr) {
        this.areaStr = areaStr;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(Integer subjectid) {
        this.subjectid = subjectid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getRejectflag() {
        return rejectflag;
    }

    public void setRejectflag(String rejectflag) {
        this.rejectflag = rejectflag;
    }

    public Integer getRegionid() {
        return regionid;
    }

    public void setRegionid(Integer regionid) {
        this.regionid = regionid;
    }

    public Integer getMainbodyid() {
        return mainbodyid;
    }

    public void setMainbodyid(Integer mainbodyid) {
        this.mainbodyid = mainbodyid;
    }

    public Integer getEventid() {
        return eventid;
    }

    public void setEventid(Integer eventid) {
        this.eventid = eventid;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getQxx() {
        return qxx;
    }

    public void setQxx(String qxx) {
        this.qxx = qxx;
    }


    public String getQxx2textarea() {
        return qxx2textarea;
    }

    public void setQxx2textarea(String qxx2textarea) {
        this.qxx2textarea = qxx2textarea;
    }

    public String getGlc() {
        return glc;
    }

    public void setGlc(String glc) {
        this.glc = glc;
    }

    public String getGlc2textarea() {
        return glc2textarea;
    }

    public void setGlc2textarea(String glc2textarea) {
        this.glc2textarea = glc2textarea;
    }
}
