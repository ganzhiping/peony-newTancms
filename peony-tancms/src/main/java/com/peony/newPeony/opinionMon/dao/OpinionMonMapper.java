package com.peony.newPeony.opinionMon.dao;

import com.peony.core.base.pojo.Pagination;
import com.peony.newPeony.opinionMon.model.Concern;
import com.peony.newPeony.opinionMon.model.OpinionCondition;

import java.util.List;

/**
 * Created by gzp on 2017/8/16.
 */
public interface OpinionMonMapper {
    List<OpinionCondition> selectByPage(OpinionCondition opinionCondition);

    List<OpinionCondition> selectByOverViewTop(OpinionCondition op);

    OpinionCondition selectByPageId(String id);

    int updateState(OpinionCondition subjectPage);

    OpinionCondition selectPageCount(OpinionCondition opinionCondition);

    List<OpinionCondition> selectByExportExecle(OpinionCondition opinionCondition);

    OpinionCondition selectByPageIdOnPo(OpinionCondition op);

    List<OpinionCondition> selectRouteReport(OpinionCondition op);

    String selectSearchTotal(OpinionCondition queryid);

    String selectProparity(OpinionCondition op);

    void insertProparity(OpinionCondition op);

    void updateProparity(OpinionCondition op);

    void updateSubject_pagePolarity(OpinionCondition op);
}
