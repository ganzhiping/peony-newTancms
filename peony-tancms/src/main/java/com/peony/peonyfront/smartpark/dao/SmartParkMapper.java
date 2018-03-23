package com.peony.peonyfront.smartpark.dao;

import com.peony.peonyfront.smartpark.model.SmartPark;

import java.util.List;

/**
 * Created by Administrator on 2017/11/28.
 */
public interface SmartParkMapper {
    List<SmartPark> selectSmartPark(SmartPark park);

    SmartPark selectInfoById(String id);

    List<String> selectSubjectIds(SmartPark userid);

    List<SmartPark> selectByQueryCache(SmartPark sp);
}
