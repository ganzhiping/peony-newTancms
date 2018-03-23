package com.peony.newPeony.special.service;

import com.peony.newPeony.special.dao.SpecialMapper;
import com.peony.newPeony.special.model.Special;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/28.
 */
@Service
public class SpecialServiceImpl implements SpecialService {
    @Autowired
    private SpecialMapper specialMapper;

    @Override
    public List<Special> selectBySpecialId(Integer subjectId) {
        return specialMapper.selectSubjectListBySubjectId(subjectId);
    }

    @Override
    public void insert(Special sp) {
        specialMapper.insert(sp);
    }

    @Override
    public void update(Special sp) {
        specialMapper.update(sp);
    }

    @Override
    public List<Special> selectAreaKeywords(Special special) {
        special.setLevel(1);
        special.setRegionId(0);
        return specialMapper.selectAreaKeywords(special);
    }

    @Override
    public Map<String, List<Special>> selectAreaRegionLevel(Special special) {
        special.setLevel(2);
        Map<String,List<Special>> map = new HashMap<>();
        List<Special> list = specialMapper.selectAreaKeywords(special);
        if(list.size()>0){
            String [] str = new String[2];
            for(Special s : list){

                Special s1 = new Special();
                s1.setRegionId(s.getRegionId());
                s1.setLevel(3);
                List<Special> list_ = specialMapper.selectAreaKeywords(s1);

                map.put(s.getRegionAbbr(),list_);
            }
        }
        return map;
    }

    @Override
    public List<Special> selectPolarityKeywords(Special special) {
        special.setLevel(1);
        List<Special>  list = specialMapper.selectPolarity(special);
        return list;
    }

    @Override
    public List<Special> selectPolarityLevel(Special special) {
        special.setLevel(1);
       String value_jdbc = specialMapper.selectPolarityLevel(special);
       String[] strs = value_jdbc.split(" ");
        List<Special> list = new ArrayList<>();
        for(int i = 0;i<strs.length;i++){
            Special sp = new Special();
            sp.setId(i+1);
            sp.setContent(strs[i]);
            list.add(sp);
        }
        return list;
    }
}
