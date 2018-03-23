package com.peony.newPeony.opinionMon.dao;

import com.peony.newPeony.opinionMon.model.Concern;

/**
 * Created by Administrator on 2017/9/6.
 */
public interface ConcernMapper {
    Concern selectByConcern(Concern concern);

    void insertIntoConcern(Concern concern);

    void deleteConcern(Concern concern);
}
