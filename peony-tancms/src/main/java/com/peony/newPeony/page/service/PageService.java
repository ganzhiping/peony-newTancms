package com.peony.newPeony.page.service;

import com.peony.core.base.pojo.PageParameter;
import com.peony.newPeony.page.model.Page;

/**
 * Created by Administrator on 2017/8/18.
 */
public interface PageService {
    String thinkInfoPage(PageParameter pageParameter, String str, String s);
    Page SubjectPage(PageParameter pageParameter, int str, int s);
    Page ESSearchPage(String queryid,int pageNo);
}
