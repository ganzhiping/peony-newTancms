package com.peony.newPeony.special.controller;

import com.peony.newPeony.special.model.Special;
import com.peony.newPeony.special.model.SubjectInfo;
import com.peony.newPeony.special.service.SpecialService;
import com.peony.newPeony.special.service.SubjectInfoService;
import com.peony.peonyfront.key.service.IdService;
import com.peony.peonyfront.login.model.User;
import com.peony.peonyfront.subject.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by gzp on 2017/8/28.
 */
@Controller
@RequestMapping("/special")
public class SpecialController {
    @Autowired
    private SpecialService specialService;
    @Autowired
    private SubjectInfoService subjectInfoService;
    @Autowired
    private IdService idService;

    /**
     * 跳转至专题添加以及关键词添加或修改关键词页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/specialSubject")
    public ModelAndView specialSubject(HttpServletRequest request, HttpServletResponse response) {
        String pageType = request.getParameter("pageType");
        String pageType_ = "";
        Special special = new Special();
        ModelAndView mv = new ModelAndView("newPeony/special/specialSubject");
        String subjectId = request.getParameter("subjectid");
        //true add;false edit
        boolean flag = Boolean.valueOf(request.getParameter("isParent"));
        SubjectInfo subjectInfo = new SubjectInfo();
        if (!flag) {
            pageType_ = "修改专题";
            special.setId(Integer.valueOf(subjectId));
            subjectInfo = subjectInfoService.selectBySubjectId(Integer.valueOf(subjectId));
            List<Special> specials = specialService.selectBySpecialId(Integer.valueOf(subjectId));
            special = formatSpecial(specials, special);
            special.setSubjectid(Integer.valueOf(subjectId));
        } else if (flag) {
            pageType_ = "新增专题";
        }
        mv.addObject("special", special);
        mv.addObject("subjectInfo", subjectInfo);
        mv.addObject("pageType", flag);
        mv.addObject("pageType_", pageType_);
        return mv;
    }
    @RequestMapping("/saveUserView")
    public @ResponseBody String specialSaveUserView(HttpServletRequest request, HttpServletResponse response) {
       String name = request.getParameter("name");
        String parentid = request.getParameter("parentId");
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        User user = (User) request.getSession().getAttribute("userfront");
        int userId = user.getUserId();
        SubjectInfo subjectInfo = new SubjectInfo();
        subjectInfo.setUserid(userId);
        subjectInfo.setName(name);
        subjectInfo.setPid(Integer.valueOf(parentid==null?"1":"".equals(parentid)?"1":parentid));
        String childNode = subjectInfoService.selectIsChildeNode(subjectInfo.getPid());
        if(childNode =="1"||"1".equals(childNode)){
            return "error";
        }else {
            subjectInfo.setState(1);
            subjectInfo.setCreatetime(new Date());
            subjectInfo.setIschildnodes("0");
            subjectInfo.setId(idService.NextKey("pe_t_subject"));
            subjectInfo.setUpdateTime(new Date());
            subjectInfoService.saveSubjectParent(subjectInfo);
            return "success";
        }

    }

    @RequestMapping("/specialAreaKeywords")
    public ModelAndView specialAreaKeywords(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        String viewType = request.getParameter("viewType");
        Special special = new Special();
        if(viewType!=null&&"area".equals(viewType)){
            List<Special> list = specialService.selectAreaKeywords(special);
            mv.addObject("areaKeywords",list);
            mv.setViewName("/newPeony/special/specialAreaKeyWords");
        }else{
            List<Special> list = specialService.selectPolarityKeywords(special);
            mv.addObject("polarityName",list);
            mv.setViewName("/newPeony/special/specialPolarityKeywords");
        }
        return mv;
    }


    @RequestMapping("/specialArea")
    public ModelAndView specialArea(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        String regionId = request.getParameter("regionId");
        Special special = new Special();
        special.setRegionId(Integer.valueOf(regionId==null?"0":regionId));
        Map<String,List<Special>> map = specialService.selectAreaRegionLevel(special);
        mv.setViewName("/newPeony/special/specialArea");
        mv.addObject("areaKeywordsRegion",map);
         return mv;
    }
    @RequestMapping("/specialPolarity")
    public ModelAndView specialPolarity(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        String regionId = request.getParameter("polarityId");
        Special special = new Special();
        special.setId(Integer.valueOf(regionId==null?"0":regionId));
        List<Special> map = specialService.selectPolarityLevel(special);
        mv.setViewName("/newPeony/special/specialPolarity");
        mv.addObject("polarityContent",map);
         return mv;
    }


    /**
     * 专题关键词新增和修改
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/editDoSubmit")
    public @ResponseBody String editDoSubmit(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        boolean pageType = Boolean.valueOf(request.getParameter("pageType"));
        int subjectId = 0;
        String pid = request.getParameter("pid");
        if (pageType) {
            subjectId = idService.NextKey("pe_t_subject");
        } else {
            subjectId = Integer.valueOf(pid);
        }
        String name = request.getParameter("name");
        User user = (User) request.getSession().getAttribute("userfront");
        int userId = user.getUserId();
        List<Special> list = new ArrayList<Special>();
        if (null != request.getParameter("qxxStr") && !"".equals(request.getParameter("qxxStr"))) {
            Special special = new Special();
            special.setName("qxx");
            special.setKeywords(request.getParameter("qxxStr"));
            list.add(setSpecialInfo(special, subjectId, pageType, 1, request));
        }
        if (null != request.getParameter("glcStr") && !"".equals(request.getParameter("glcStr"))) {
            Special special = new Special();
            special.setName("glc");
            special.setKeywords(request.getParameter("glcStr"));
            list.add(setSpecialInfo(special, subjectId, pageType, 2, request));
        }
        if (null != request.getParameter("areaStr") && !"".equals(request.getParameter("areaStr"))) {
            Special special = new Special();
            special.setName("area");
            special.setKeywords(request.getParameter("areaStr"));
            list.add(setSpecialInfo(special, subjectId, pageType, 3, request));
        }
        if (null != request.getParameter("mainStr") && !"".equals(request.getParameter("mainStr"))) {
            Special special = new Special();
            special.setName("main");
            special.setKeywords(request.getParameter("mainStr"));
            list.add(setSpecialInfo(special, subjectId, pageType, 4, request));
        }

        if (!pageType) {
            subjectInfoService.updateChildsNode(subjectId);
            return "1";
        } else {
            add(list, userId, name, pid, subjectId);
            return "2";
        }
    }

    @RequestMapping("/renameSubject")
    public @ResponseBody String changeSpecialName(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        String subjectId = request.getParameter("subjectid");
        User user = (User) request.getSession().getAttribute("userfront");
        int userId = user.getUserId();
        String spName = request.getParameter("name");
        SubjectInfo subject = new SubjectInfo();
        subject.setName(spName);
        subject.setId(Integer.valueOf(subjectId==null?"0":subjectId));
        subject.setUpdateTime(setChangeTime());
        subject.setUserid(userId);
        subjectInfoService.changeSpecialName(subject);
        return "success";
    }

    @RequestMapping("/removeSubject")
    public void reMoveSubject(HttpServletRequest request,HttpServletResponse response) throws ParseException {
        String isParent = request.getParameter("isParent");
        String subjectId = request.getParameter("subjectid");
        User user = (User) request.getSession().getAttribute("userfront");
        int userId = user.getUserId();
        SubjectInfo subject = new SubjectInfo();
        subject.setId(Integer.valueOf(subjectId));
        subject.setUserid(userId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<SubjectInfo> list = new ArrayList<SubjectInfo>();
        if(Boolean.valueOf(isParent)){
            subject.setPid(Integer.valueOf(subjectId));
            list = subjectInfoService.selectSubjectsByParentSubject(subject);
            for(SubjectInfo subjectInfo : list){
                SubjectInfo tmp = new SubjectInfo();
                tmp.setDroptime(sdf.parse(sdf.format(new Date())));
                tmp.setId(subjectInfo.getId());
                tmp.setState(0);
                subjectInfoService.deleteSubject(tmp);
            }
        }else{
            subject.setDroptime(sdf.parse(sdf.format(new Date())));
            subject.setState(0);
            subjectInfoService.deleteSubject(subject);
        }

    }

    @RequestMapping("/addSubject")
    public ModelAndView addUserSubject(HttpServletRequest request,HttpServletResponse response){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/newPeony/opinionMon/opinionAdd");
        return mv;
    }

    @RequestMapping("/modelShowTc")
    public ModelAndView tacnchuang(HttpServletRequest request,HttpServletResponse response){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/newPeony/opinionMon/opinionAdd");
        return mv;
    }

    /**
     * @param list
     * @param userId
     * @param name
     * @param pid
     * @param subjectId
     */
    private void add(List<Special> list, int userId, String name, String pid, int subjectId) {
        SubjectInfo subjectInfo = new SubjectInfo();
        subjectInfo.setPid(Integer.valueOf(pid));
        subjectInfo.setId(subjectId);
        subjectInfo.setName(name);
        subjectInfo.setState(1);
        subjectInfo.setCreatetime(setChangeTime());
        subjectInfo.setLastupdatedtime(setChangeTime());
        subjectInfo.setDroptime(setChangeTime());
        subjectInfo.setIschildnodes("1");
        subjectInfo.setUpdateTime(setChangeTime());
        subjectInfoService.insertSubjectSpecial(subjectInfo);
        for (Special sp : list) {
            specialService.insert(sp);
        }
    }

    /**
     * @return
     */
    private Date setChangeTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(new Date());
        try {
            return sdf.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param special
     * @param id
     * @param flag
     * @param type
     * @param request
     * @return
     */
    private Special setSpecialInfo(Special special, int id, boolean flag, int type, HttpServletRequest request) {
        special.setSubjectid(Integer.valueOf(id));
        special.setRejectflag("0");
        if (flag) {
            special.setId(idService.NextKey("pe_t_subject_keywords"));
        } else {
            switch (type) {
                case 1:
                    if(Integer.valueOf(request.getParameter("qxxId"))==0){
                        special.setId(idService.NextKey("pe_t_subject_keywords"));
                        specialService.insert(special);
                    }else{
                        special.setId(Integer.valueOf(request.getParameter("qxxId")));
                        specialService.update(special);
                    }
                    break;
                case 2:
                    if(Integer.valueOf(request.getParameter("glcId"))==0){
                        special.setId(idService.NextKey("pe_t_subject_keywords"));
                        specialService.insert(special);
                    }else{
                        special.setId(Integer.valueOf(request.getParameter("glcId")));
                        specialService.update(special);
                    }
                    break;
                case 3:
                    if(Integer.valueOf(request.getParameter("areaId"))==0){
                        special.setId(idService.NextKey("pe_t_subject_keywords"));
                        specialService.insert(special);
                    }else{
                        special.setId(Integer.valueOf(request.getParameter("areaId")));
                        specialService.update(special);
                    }
                    break;
                case 4:
                    if(Integer.valueOf(request.getParameter("mainId"))==0){
                        special.setId(idService.NextKey("pe_t_subject_keywords"));
                        specialService.insert(special);
                    }else{
                        special.setId(Integer.valueOf(request.getParameter("mainId")));
                        specialService.update(special);
                    }
                    break;
            }
        }
        return special;
    }

    /**
     * @param specials
     * @param special
     * @return
     */
    private Special formatSpecial(List<Special> specials, Special special) {
        for (Special sp : specials) {
            String name = sp.getName();
            switch (name) {
                case "qxx":
                    special.setQxx(name);
                    special.setQxxId(sp.getId());
                    special.setQxxStr(sp.getKeywords());
                    continue;
                case "area":
                    special.setArea(name);
                    special.setAreaId(sp.getId());
                    special.setAreaStr(sp.getKeywords());
                    continue;
                case "main":
                    special.setMain(name);
                    special.setMainId(sp.getId());
                    special.setMainStr(sp.getKeywords());
                    continue;
                case "glc":
                    special.setGlc(name);
                    special.setGlcId(sp.getId());
                    special.setGlcStr(sp.getKeywords());
                    continue;
            }
        }
        return special;
    }
}
