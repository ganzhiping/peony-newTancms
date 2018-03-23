package com.peony.newPeony.overView.controller;

import com.peony.core.base.controller.BaseController;
import com.peony.core.base.pojo.PageParameter;
import com.peony.core.base.pojo.Pagination;
import com.peony.newPeony.opinionMon.model.OpinionCondition;
import com.peony.newPeony.opinionMon.service.OpinionMonService;
import com.peony.newPeony.overView.model.ThinkInfo;
import com.peony.newPeony.overView.service.OverViewService;
import com.peony.newPeony.page.service.PageService;
import com.peony.peonyfront.login.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by gzp on 2017/8/15.
 *
 * 舆情系统二次开发总览页控制器
 *
 */
@Controller
@RequestMapping("/overView")
public class OverViewController extends BaseController {
    @Autowired
    private OverViewService overViewService;
    @Autowired
    private OpinionMonService opinionMonService;
    @Autowired
    private PageService pageService;
    @RequestMapping("/overViewList")
    public ModelAndView overViewList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/newPeony/overView/overView");
        return mv;
    }

    @RequestMapping("/overViewListTop")
    public ModelAndView overViewListTop(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/newPeony/overView/overViewTop");
        User user = (User) request.getSession().getAttribute("userfront");
        int userId = user.getUserId();
        List<OpinionCondition> list = opinionMonService.findOverViewTop(userId);
        mv.addObject("overViewTopList",list);
        return mv;
    }

    @RequestMapping("/overViewTopInfo")
    public ModelAndView overViewTopInfo(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/newPeony/zhiKuNewInfo");
        User user = (User) request.getSession().getAttribute("userfront");
        int userId = user.getUserId();
        String id = request.getParameter("id");
        ThinkInfo list = overViewService.findOverViewInfo(Integer.valueOf(id));
        mv.addObject("overViewTopList",list);
        return mv;
    }

    @RequestMapping("/overViewListBottom")
    public ModelAndView overViewListBottom(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/newPeony/overView/overViewBottom");
        User user = (User) request.getSession().getAttribute("userfront");
        int userId = user.getUserId();
        ThinkInfo info = new ThinkInfo();
        PageParameter pageParameter = new PageParameter();
        if (null == request.getParameter("pageNo")) {
            pageParameter.setCurrentPage(1);
        } else {
            pageParameter.setCurrentPage(Integer.parseInt(request.getParameter("pageNo")));
        }
        if (null != request.getParameter("pagesize")) {
            pageParameter.setPageSize(Integer.parseInt(request.getParameter("pagesize")));
        } else {
            pageParameter.setPageSize(10);
        }
        String pageStr = pageService.thinkInfoPage(pageParameter,"pe_t_pubinfo",null);
        info.setPageParameter(pageParameter);
        info.setPageNo(((pageParameter.getCurrentPage()-1)*pageParameter.getPageSize()));
        info.setPageSize(pageParameter.getPageSize());
        Pagination<ThinkInfo> list = overViewService.findAllThinkInfo(info);

        mv.addObject("thinkInfo", info);
        mv.addObject("pageStr", pageStr);
        mv.addObject("pagination", list);
        return mv;
    }
}
