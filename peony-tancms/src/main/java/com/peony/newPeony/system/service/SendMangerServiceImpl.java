package com.peony.newPeony.system.service;

import com.peony.newPeony.system.dao.ManagerMapper;
import com.peony.newPeony.system.model.Manager;
import com.peony.newPeony.util.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/8/23.
 */
@Service
public class SendMangerServiceImpl implements SendMangerService {
    //短信发送sn序列号
    private static final String sn="SDK-BBX-010-26381";
    //密码
    private static final String pwd="2548=d=-5b6";
    @Autowired
    private ManagerMapper managerMapper;
    /**
     * 发送短信
     * @param content
     * @param userId
     * @return
     */
    @Override
    public boolean sendManager(String content, int userId) {
        Manager manager = managerMapper.selectByUserId(userId);
        Client client= null;
        boolean flag = false;
        try {
            client = new Client(sn, pwd);
            String mobiles = "";
            if(manager.getMobiles()!=null &&!"".equals(manager.getMobiles())&&!"null".equals(manager.getMobiles())){
                mobiles =  manager.getMobile()+","+manager.getMobiles();
            }else{
                mobiles =  manager.getMobile();
                //mobiles = "18210423831";
            }
            String retBack = client.mdsmssend(mobiles, content, "", "", "20170823", "");
            if("20170823".equals(retBack)){
                flag = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return flag;
    }
}
