package com.peony.peonyfront.popular.dao;

import com.peony.peonyfront.popular.model.Popular;

import java.util.List;

/**
 * Created by Administrator on 2018/1/8.
 */
public interface PopularMapper {
    List<Popular> selectByPage(Popular popular);

    Popular selectByPopularId(Popular popular);

    void update(Popular popular);
}
