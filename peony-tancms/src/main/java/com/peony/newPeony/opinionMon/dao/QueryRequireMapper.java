package com.peony.newPeony.opinionMon.dao;

import com.peony.newPeony.opinionMon.model.OpinionCondition;
import com.peony.newPeony.opinionMon.model.QueryRequire;

/**
 * Created by Administrator on 2017/8/25.
 */
public interface QueryRequireMapper {
    QueryRequire selectByUserId(int userId);

    int insertOrUpdate(QueryRequire require);

    OpinionCondition selectByPageId(String id, int userId);

    OpinionCondition selectByPageId(OpinionCondition op);
}
