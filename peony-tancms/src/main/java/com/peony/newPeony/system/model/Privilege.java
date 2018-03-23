package com.peony.newPeony.system.model;

/**
 * Created by gzp on 2017/8/23.
 */
public class Privilege{
    /**
     * 权限相关
     */
    private int menuid;
    private int roleid;
    private int userid;

    public int getMenuid() {
        return menuid;
    }

    public void setMenuid(int menuid) {
        this.menuid = menuid;
    }

    public int getRoleid() {
        return roleid;
    }

    public void setRoleid(int roleid) {
        this.roleid = roleid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
