package com.peony.newPeony.special.service;

import com.peony.newPeony.special.model.Special;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/28.
 */
public interface SpecialService {
    List<Special> selectBySpecialId(Integer special);

    void insert(Special sp);

    void update(Special sp);

    List<Special> selectAreaKeywords(Special special);

    Map<String,List<Special>> selectAreaRegionLevel(Special special);

    List<Special> selectPolarityKeywords(Special special);

    List<Special> selectPolarityLevel(Special special);
}
