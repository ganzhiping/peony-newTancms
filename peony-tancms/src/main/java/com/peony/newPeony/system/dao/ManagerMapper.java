package com.peony.newPeony.system.dao;

import com.peony.newPeony.system.model.Manager;

/**
 * Created by Administrator on 2017/8/23.
 */
public interface ManagerMapper {
    Manager selectByUserId(int userId) ;
}
