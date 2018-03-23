package com.peony.peonyfront.smartpark.service;

import com.peony.peonyfront.smartpark.model.SmartPark;

import java.util.List;

/**
 * Created by Administrator on 2017/11/28.
 */
public interface SmartParkService {
    List<SmartPark> selectFirstList(int userId, String[] timeStamp);

    List<SmartPark> selectAjaxPageList(SmartPark park);

    String keywordsAndAuthor(String content_);

    SmartPark selectInfoById(String id);

    List<String> selectSubjectIds(SmartPark userid);

    List<SmartPark> selectByQueryCache(SmartPark sp);

    List<SmartPark> selectAjaxSearch(SmartPark park);
}
