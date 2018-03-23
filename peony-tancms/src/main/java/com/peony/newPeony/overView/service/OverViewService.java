package com.peony.newPeony.overView.service;

import com.peony.core.base.pojo.Pagination;
import com.peony.newPeony.overView.model.ThinkInfo;

/**
 * Created by Administrator on 2017/8/17.
 */
public interface OverViewService {
    Pagination<ThinkInfo> findAllThinkInfo(ThinkInfo info);

    ThinkInfo findOverViewInfo(Integer id);
}
