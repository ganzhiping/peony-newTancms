package com.peony.peonyfront.popular.service;

import com.peony.core.base.pojo.Pagination;
import com.peony.peonyfront.popular.model.Popular;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/8.
 */
public interface PopularService {
    Pagination<Popular> selectByPage(Popular popular);

    void createDoc(String title,String fileName, String template, Map<String, Object> dataMap, HttpServletRequest request, HttpServletResponse response, String type);

    Popular selectByPopularId(Popular popular);

    void delete(Popular popular);
}
