package com.peony.newPeony.special.dao;

import com.peony.newPeony.special.model.Special;

import java.util.List;

/**
 * Created by Administrator on 2017/8/28.
 */
public interface SpecialMapper {
    List<Special> selectSubjectListBySubjectId(Integer subjectId);

    void insert(Special sp);

    void update(Special sp);

    List<Special> selectAreaKeywords(Special special);

    List<Special> selectPolarity(Special special);

    String selectPolarityLevel(Special special);
}
