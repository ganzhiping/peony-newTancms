package com.peony.newPeony.report.hotword.dao;

import com.peony.newPeony.report.hotword.model.HotWords;

import java.util.List;

/**
 * Created by Administrator on 2017/9/12.
 */
public interface HotWordsMapper {
    List<HotWords> selectSubjectHotWords(HotWords hotWords);
}
