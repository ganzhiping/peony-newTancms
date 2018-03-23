package com.peony.newPeony.opinionMon.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/1.
 */
public class OpinionSearch implements Serializable{
    private int total;
    private List<OpinionCondition> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<OpinionCondition> getList() {
        return list;
    }

    public void setList(List<OpinionCondition> list) {
        this.list = list;
    }
}
