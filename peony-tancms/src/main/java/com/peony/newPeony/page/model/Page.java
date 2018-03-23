package com.peony.newPeony.page.model;

/**
 * Created by Administrator on 2017/8/18.
 */
public class Page {
    private String pageNum;
    private String pageSize;
    private String totalPage;
    private String tableName;
    private String sqlwhe;
    private String pageCount;
    private String ret;
    private String infoCount;

    public String getInfoCount() {
        return infoCount;
    }

    public void setInfoCount(String infoCount) {
        this.infoCount = infoCount;
    }

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getSqlwhe() {
        return sqlwhe;
    }

    public void setSqlwhe(String sqlwhe) {
        this.sqlwhe = sqlwhe;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }
}
