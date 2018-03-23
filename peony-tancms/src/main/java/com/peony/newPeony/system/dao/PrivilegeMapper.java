package com.peony.newPeony.system.dao;

import com.peony.newPeony.system.model.Privilege;

import java.util.List;

/**
 * Created by Administrator on 2017/8/23.
 */
public interface PrivilegeMapper {
    List<Privilege> selectMenuIdById(int userId);
}
