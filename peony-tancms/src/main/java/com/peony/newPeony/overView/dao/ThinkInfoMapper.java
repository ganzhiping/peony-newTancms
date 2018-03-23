package com.peony.newPeony.overView.dao;

import com.peony.newPeony.overView.model.ThinkInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/8/17.
 */
public interface ThinkInfoMapper {
    List<ThinkInfo> selectAllThinkInfo(ThinkInfo info);

    ThinkInfo findOverViewInfo(Integer id);
}
