package com.peony.newPeony.system.service;

import com.peony.newPeony.system.dao.PrivilegeMapper;
import com.peony.newPeony.system.model.Privilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/8/23.
 */
@Service
public class PrivilegeServiceImpl implements PrivilegeService {
    @Autowired
    private PrivilegeMapper privilegeMapper;


    @Override
    public boolean findSendManagerRole(int userId) {
        boolean flag = false;
        List<Privilege> privilege = privilegeMapper.selectMenuIdById(userId);
        for(Privilege pr : privilege){
           // System.out.println(pr.getMenuid());
            if("2001".equals(String.valueOf(pr.getMenuid()))){
                flag = true;
            }
        }
        return flag;
    }

}
