package com.peony.newPeony.overView.service;

import com.peony.core.base.pojo.Pagination;
import com.peony.newPeony.overView.dao.ThinkInfoMapper;
import com.peony.newPeony.overView.model.ThinkInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/17.
 */
@Service
public class OverViewServiceImpl implements OverViewService {
    private final Log logger = LogFactory.getLog(OverViewServiceImpl.class);
    @Autowired
    private ThinkInfoMapper thinkInfoMapper;
    @Override
    public  Pagination<ThinkInfo> findAllThinkInfo(ThinkInfo info) {
        List<ThinkInfo> list =  changeBackTime(thinkInfoMapper.selectAllThinkInfo(info));
        Pagination<ThinkInfo> pagination = new Pagination<ThinkInfo>();

        if (null != list) {
            pagination = new Pagination<ThinkInfo>(list, info.getPageParameter());
        } else {
            this.logger.warn("Can't find any subject pages!");
        }
        return pagination;
    }

    @Override
    public ThinkInfo findOverViewInfo(Integer id) {
        return thinkInfoMapper.findOverViewInfo(id);
    }

    public  List<ThinkInfo> changeBackTime(List<ThinkInfo> list){
        List<ThinkInfo> listNew = new ArrayList<ThinkInfo>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Long nowTime = System.currentTimeMillis();
        for (ThinkInfo op : list) {
            long writeTime = op.getTime().getTime();
            int topTime = (int)((nowTime - writeTime)/(1000*60*60));
            if(topTime>0&&topTime<24){
                op.setTimeStr(topTime+"小时前");
            }else if(topTime == 0){
                op.setTimeStr("1小时内");
            }else{
                op.setTimeStr(sdf.format(op.getTime()));
            }
            listNew.add(op);
        }
        return listNew;
    }

}
