package com.peony.newPeony.opinionMon.service;

import com.peony.newPeony.opinionMon.model.Concern;
import com.peony.newPeony.opinionMon.model.OpinionCondition;
import com.peony.newPeony.opinionMon.model.QueryRequire;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by gzp on 2017/8/16.
 */
public interface OpinionMonService {
    List<OpinionCondition> findByPage(OpinionCondition opinionCondition);

    List<OpinionCondition> findOverViewTop(int userId);

    OpinionCondition selectPageById(String id);

    int batchUpdate(OpinionCondition subjectPage);

    OpinionCondition selectPageNo(OpinionCondition opinionCondition);

    OpinionCondition queryCriteria(HttpServletRequest request, int userId, OpinionCondition opinionCondition, QueryRequire queryRequire);

    List<OpinionCondition> selectByExportExecle(OpinionCondition opinionCondition);

    QueryRequire selectQueryRequireByUesrId(int userId);

    int updateOrInsertQuery(QueryRequire require);

    OpinionCondition selectByPageId(String id, int userId,int subjectid);

    String changeConcernValue(Concern concern);

    String keywordsAndAuthor(String content_);

    Map<String,List<OpinionCondition>> selectRouteReport(int userId, int subjectid);

    List<OpinionCondition> selectBySearchValue(OpinionCondition op);

    String selectSearchTotal(OpinionCondition queryid);

    String insertIntoProlarity(OpinionCondition op);

}
