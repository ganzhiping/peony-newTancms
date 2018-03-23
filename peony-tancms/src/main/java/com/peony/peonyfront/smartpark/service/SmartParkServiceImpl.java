package com.peony.peonyfront.smartpark.service;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.JSONArray;
import com.alibaba.dubbo.common.json.JSONObject;
import com.peony.peonyfront.smartpark.dao.SmartParkMapper;
import com.peony.peonyfront.smartpark.model.SmartPark;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/28.
 */
@Service
public class SmartParkServiceImpl implements SmartParkService {
    private static final String orderStr = " publishDate desc ";

    @Resource
    private SmartParkMapper smartParkMapper;
    @Override
    public List<SmartPark> selectFirstList(int userId, String[] timeStamp) {
        SmartPark park = new SmartPark();
        park.setCurentPage(10);
        park.setPageSize(0);
        park.setUserid(userId);
        park.setEndTime(timeStamp[0]);
        park.setStartTime(timeStamp[1]);
        park.setOrderStr(orderStr);
        List<SmartPark> list = smartParkMapper.selectSmartPark(park);
        return list;
    }

    @Override
    public List<SmartPark> selectAjaxPageList(SmartPark park) {
        park.setOrderStr(orderStr);
        List<SmartPark> list = smartParkMapper.selectSmartPark(park);
        return list;
    }

    @Override
    public String keywordsAndAuthor(String str) {
        String ret = "";
        String res = "";
        try {
            JSONObject ob = (JSONObject) JSON.parse(str);
            JSONArray val1 = (JSONArray) ob.get("docs");
            JSONObject url = (JSONObject) val1.get(0);
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> m = mapper.readValue(JSON.json(url), Map.class);
            Object author = m.get("author");
            ret = (author==null?"":author.toString()==""?"":"".equals(author.toString())?"":author.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public SmartPark selectInfoById(String id) {
        return smartParkMapper.selectInfoById(id);
    }

    @Override
    public List<String> selectSubjectIds(SmartPark userid) {
        return smartParkMapper.selectSubjectIds(userid);
    }

    @Override
    public List<SmartPark> selectByQueryCache(SmartPark sp) {
        sp.setCurentPage(10);
        sp.setPageSize(0);
        return smartParkMapper.selectByQueryCache(sp);
    }

    @Override
    public List<SmartPark> selectAjaxSearch(SmartPark park) {
        return smartParkMapper.selectByQueryCache(park);
    }

}
