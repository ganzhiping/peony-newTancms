package com.peony.newPeony.report.website.dao;

import com.peony.newPeony.report.website.model.WebSiteUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/9/6.
 */
public interface WebsiteMapper {
    List<WebSiteUtil> selectWebsiteReport(WebSiteUtil webSiteUtil);
}
