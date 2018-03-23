package com.peony.newPeony.page.service;

import com.peony.core.base.pojo.PageParameter;
import com.peony.newPeony.page.dao.PageMapper;
import com.peony.newPeony.page.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.StringValueExp;

/**
 * Created by Administrator on 2017/8/18.
 */
@Service
public class PageServiceImpl implements PageService {

    @Autowired
    private PageMapper pageMapper;
    @Override
    public String thinkInfoPage(PageParameter pageParameter, String str, String s) {
        Page page = new Page();
        page.setTableName(str);
        page.setSqlwhe(s);
        int totalPage = pageMapper.selectThinkInfo(page);
        int pageSize = pageParameter.getPageSize();
        int pageNo = pageParameter.getCurrentPage();
        StringBuffer sb = new StringBuffer();
        int pageAllNo=0;
        if(totalPage>0){
            int i = totalPage % pageSize;
            if(i>0){
                pageAllNo = totalPage/pageSize+1;
            }else if(i==0){
                pageAllNo = totalPage/pageSize;
            }
        }
        sb.append(" <div class=\"page\"><div class=\"page-normal\">");
        if(pageAllNo>1){
            boolean flag = true;
            int i=1;
            if(pageNo>5&&pageAllNo>10){
                if((pageNo+5)<=pageAllNo){
                    i = pageAllNo-9;
                }else{
                    i = pageNo-4;
                }
            }
            if(pageNo==1){
                sb.append("<a href=\"javascript:;\" class=\"page-prev\" title=\"当前已是首页\">&lt;</a>");
            }else if(pageNo>1){
                sb.append("<a href=\"javascript:changePageNo('"+(pageNo-1)+"');\" class=\"page-prev\" title=\"上一页\">&lt;</a>");
            }
            for(;i<=pageAllNo;i++){
                String ret = "<a href=\"javascript:changePageNo('"+i+"');\"";
                if(i==pageNo){
                    ret+=" class=\"page-current\">";
                }else{
                    ret+=" class=\"\">";
                }
                ret += i + "</a>";
                sb.append(ret);
            }
            if(pageNo==pageAllNo){
              sb.append("<a href=\"javascript:;\" class=\"page-prev\" title=\"当前已是尾页\">&gt;</a>");
            }else if(pageAllNo>1){
                sb.append("<a href=\"javascript:changePageNo('"+(pageNo+1)+"');\" class=\"page-prev\" title=\"下一页\">&gt;</a>");
            }
        }else{
            sb.append("<a href=\"javascript:;\" class=\"page-current\">1</a>");
            sb.append("<a href=\"javascript:;\" class=\"page-prev\" title=\"当前已是尾页\">&gt;</a>");
        }
        sb.append("   </div>" +
                "    </div>");
        return sb.toString();
    }

    /**
     * 舆情列表分页
     * @param pageParameter
     * @param str
     * @param s
     * @return
     */
    @Override
    public Page SubjectPage(PageParameter pageParameter, int str, int s) {
        Page page = new Page();
        int totalPage_subject = str;
        int  totalPage= str;
        int pageSize = pageParameter.getPageSize();
        int pageNo = pageParameter.getCurrentPage();
        StringBuffer sb = new StringBuffer();
        int pageAllNo=0;
        if(totalPage>0){
            int i = totalPage % pageSize;
            if(i>0){
                pageAllNo = totalPage/pageSize+1;
            }else if(i==0){
                pageAllNo = totalPage/pageSize;
            }
        }
        sb.append(" <div class=\"page\"><div class=\"page-normal\">");
        if(pageAllNo>=1){
            boolean flag = true;
            int i=1;
            if(pageNo>5&&pageAllNo>10){
                if((pageNo+5)<=pageAllNo){
                    i = pageNo-4;
                }else{
                    i = pageAllNo-9;
                }
            }
            sb.append("<a href=\"javascript:changePageNo('"+1+"');\" class=\"page-prev\" title=\"首页\">首页</a>");
            if(pageNo==1){
                sb.append("<a href=\"javascript:;\" class=\"page-prev\" title=\"当前已是首页\">&lt;</a>");
            }else if(pageNo>1){
                sb.append("<a href=\"javascript:changePageNo('"+(pageNo-1)+"');\" class=\"page-prev\" title=\"上一页\">&lt;</a>");
            }
            int conunt = 0;
            for(;i<=pageAllNo;i++){
                conunt++;
                if(conunt> 10){
                    break;
                }
                String ret = "<a href=\"javascript:changePageNo('"+i+"');\"";
                if(i==pageNo){
                    ret+=" class=\"page-current\">";
                }else{
                    ret+=" class=\"\">";
                }
                ret += i + "</a>";
                sb.append(ret);
            }
            if(pageNo==pageAllNo){
                sb.append("<a href=\"javascript:;\" class=\"page-prev\" title=\"当前已是尾页\">&gt;</a>");
            }else if(pageAllNo>1){
                sb.append("<a href=\"javascript:changePageNo('"+(pageNo+1)+"');\" class=\"page-prev\" title=\"下一页\">&gt;</a>");
            }
        }else{
            sb.append("<a href=\"javascript:;\" class=\"page-current\">1</a>");
            sb.append("<a href=\"javascript:;\" class=\"page-prev\" title=\"当前已是尾页\">&gt;</a>");
        }
        sb.append("<a href=\"javascript:changePageNo('"+pageAllNo+"');\" class=\"page-prev\" title=\"尾页\">尾页</a>");
        sb.append("   </div>" +
                "    </div>");
        page.setRet(sb.toString());
        page.setInfoCount(String.valueOf(totalPage_subject));
        page.setTotalPage(String.valueOf(pageAllNo));
        page.setPageNum(String.valueOf(pageNo));
        page.setPageCount(String.valueOf(pageSize));
        return page;
    }
    /**
     * 舆情ES搜索分页
     * @return
     */
    @Override
    public Page ESSearchPage(String total,int pageNo) {
        Page page = new Page();
        int s = (total==null?0:Integer.valueOf(total));
        int totalPage_subject = s;
        int  totalPage= s;
        int pageSize =10;
        /*int pageNo = pageParameter.getCurrentPage();*/
        StringBuffer sb = new StringBuffer();
        int pageAllNo=0;
        if(totalPage>0){
            int i = totalPage % pageSize;
            if(i>0){
                pageAllNo = totalPage/pageSize+1;
            }else if(i==0){
                pageAllNo = totalPage/pageSize;
            }
        }
        sb.append(" <div class=\"page\"><div class=\"page-normal\">");
        if(pageAllNo>=1){
            boolean flag = true;
            int i=1;
            if(pageNo>5&&pageAllNo>10){
                if((pageNo+5)<=pageAllNo){
                    i = pageNo-4;
                }else{
                    i = pageAllNo-9;
                }
            }
            sb.append("<a href=\"javascript:changePageESNo('"+1+"');\" class=\"page-prev\" title=\"首页\">首页</a>");
            if(pageNo==1){
                sb.append("<a href=\"javascript:;\" class=\"page-prev\" title=\"当前已是首页\">&lt;</a>");
            }else if(pageNo>1){
                sb.append("<a href=\"javascript:changePageESNo('"+(pageNo-1)+"');\" class=\"page-prev\" title=\"上一页\">&lt;</a>");
            }
            int conunt = 0;
            for(;i<=pageAllNo;i++){
                conunt++;
                if(conunt> 10){
                    break;
                }
                String ret = "<a href=\"javascript:changePageESNo('"+i+"');\"";
                if(i==pageNo){
                    ret+=" class=\"page-current\">";
                }else{
                    ret+=" class=\"\">";
                }
                ret += i + "</a>";
                sb.append(ret);
            }
            if(pageNo==pageAllNo){
                sb.append("<a href=\"javascript:;\" class=\"page-prev\" title=\"当前已是尾页\">&gt;</a>");
            }else if(pageAllNo>1){
                sb.append("<a href=\"javascript:changePageESNo('"+(pageNo+1)+"');\" class=\"page-prev\" title=\"下一页\">&gt;</a>");
            }
        }else{
            sb.append("<a href=\"javascript:;\" class=\"page-current\">1</a>");
            sb.append("<a href=\"javascript:;\" class=\"page-prev\" title=\"当前已是尾页\">&gt;</a>");
        }
        sb.append("<a href=\"javascript:changePageESNo('"+pageAllNo+"');\" class=\"page-prev\" title=\"尾页\">尾页</a>");
        sb.append("   </div>" +
                "    </div>");
        String  ret ="";
        if(s==0){
            ret = null;
        }else{
            ret = sb.toString();
        }
        page.setRet(ret);
        page.setInfoCount(String.valueOf(totalPage_subject));
        page.setTotalPage(String.valueOf(pageAllNo));
        page.setPageNum(String.valueOf(pageNo));
        page.setPageCount(String.valueOf(pageSize));
        return page;
    }
}
