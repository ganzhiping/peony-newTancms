package com.peony.peonyfront.popular.controller;

import com.itextpdf.xmp.impl.Base64;
import com.lowagie.text.pdf.BaseFont;
import com.peony.core.base.controller.BaseController;
import com.peony.core.base.pojo.PageParameter;
import com.peony.core.base.pojo.Pagination;
import com.peony.peonyfront.login.model.User;
import com.peony.peonyfront.operationlog.model.OperationLog;
import com.peony.peonyfront.operationlog.service.OperationLogService;
import com.peony.peonyfront.popular.model.Popular;
import com.peony.peonyfront.popular.service.PopularService;
import com.peony.peonyfront.util.DocumentHandler;
import com.peony.peonyfront.util.log.LoginType;
import com.peony.peonyfront.util.log.OperateMode;
import com.peony.peonyfront.util.log.OperateType;
import com.peony.peonyfront.util.log.Type;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/1/8.
 */
@Controller
@RequestMapping("/popular")
public class PopularController extends BaseController {

    @Autowired
    private OperationLogService operationLogService;
    @Autowired
    private PopularService popularService;
    /**
     * 跳转到舆情专报列表页面
     *
     * @return
     */
    @RequestMapping(value = "/listPopular")
    public ModelAndView listPopular(@ModelAttribute("popular") Popular popular, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        // --uerId从session里面获取
        User user = (User) request.getSession().getAttribute("userfront");
        popular.setUserid(String.valueOf(user.getUserId()));
        PageParameter pageParameter = new PageParameter();
        if (null == request.getParameter("pageNo")) {
            pageParameter.setCurrentPage(1);
        } else {
            pageParameter.setCurrentPage(Integer.parseInt(request.getParameter("pageNo")));
        }
        popular.setPageParameter(pageParameter);
        Pagination<Popular> pagination = popularService.selectByPage(popular);

        OperationLog operationLog = new OperationLog(new Date(), user.getUserId(), user.getName(), String.valueOf(Type.OPERATE.getValue()), String.valueOf(LoginType.PC.getValue()), "舆情简报", OperateType.FIND.toString(), OperateMode.舆情简报.toString());
        this.operationLogService.insertSelective(operationLog);

        mv.addObject("pagination", pagination);
        mv.addObject("popular", popular);
        mv.setViewName("/popular/list_popular");
        return mv;
    }

    @RequestMapping(value = "/deletePopular")
    @ResponseBody
    public void delete(Popular popular, HttpServletRequest request, HttpServletResponse response) {
        popularService.delete(popular);
    }


    @RequestMapping(value = "/popularInfo")
    public ModelAndView popularInfo(@ModelAttribute("popular") Popular popular, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        Popular listPopular = this.popularService.selectByPopularId(popular);
        mv.addObject("popular", listPopular);
        mv.setViewName("/popular/info_popular");
        return mv;
    }
   public Popular exchangeImg(Popular listPopular){
        String content =listPopular.getContent();
        String regex = "<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        String src = "";
        //避免重复的图片链接
        List<String> set = new ArrayList<String>();
        List<String> set1 = new ArrayList<String>();

        while (matcher.find()) {
            src = matcher.group(1);
            set1.add(src);
            set.add(Base64.encode(src));
        }
        int i=0;
        for(String str : set) {
            System.out.println(set1.get(i));

            content = content.replaceAll(set1.get(i), "data:image/*;base64,"+str);
            System.out.println(content);
            i++;
        }

        listPopular.setContent(content);
        return listPopular;
    }

    /**
     * 下载word
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/downloadPopular")
    @ResponseBody
    public void downloadBrifreport(Popular popular, HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("userfront");
        SimpleDateFormat datatime = new SimpleDateFormat("yyyyMMdd");
        // map内放模板需要的数据
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Popular listPopular = this.popularService.selectByPopularId(popular);
        // 显示正文、去除html标签
     //   listPopular = exchangeImg(listPopular);
        dataMap.put("popular", listPopular);
        dataMap.put("name", user.getName());
        //dataMap.put("title", "舆情简报");
        dataMap.put("secondTitle", "第" + popular.getPeriods() + "期");
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dataMap.put("date",sdf.format( dt));
        // 生成文件名
        String fileName="";
        String template="";
        String type="";
        if("PDF".equals(popular.getDownLoadType().toUpperCase())){
            fileName = dt.getTime() + ".html";
            template = "popularPDF.ftl";
            if(listPopular.getContent().indexOf("file://")!=-1){
                listPopular.setContent(listPopular.getContent().replaceAll("file://" ,""));
            }
            if(listPopular.getContent().indexOf("211.145.15.147")!=-1){
                listPopular.setContent(listPopular.getContent().replaceAll("http://211.145.15.147:8081" ,"/var/apache-tomcat-7.0.81/peony-tancms/"));
            }
            type="PDF";
        }else{
            if(listPopular.getContent().indexOf("211.145.15.147")!=-1){
                listPopular.setContent(listPopular.getContent().replaceAll("http://211.145.15.147:8081" ,"/var/apache-tomcat-7.0.81/peony-tancms/"));
            }
           // exchangeImg(listPopular);
            fileName = dt.getTime() + ".doc";
            template = "popular.ftl";
            type="WORD";
        }
        popularService.createDoc(listPopular.getTitle(),fileName, template, dataMap, request, response,type);
    }
   public byte[] ImageToBytes(String path)
    {
        File file = new File(path);//"C:\\Documents and Settings\\All Users\\Documents\\My Pictures\\示例图片\\Sunset.jpg"
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(file);
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            ByteArrayOutputStream out= new ByteArrayOutputStream();
            int i = fin.read();
            while (i != -1) {
                out.write(i);
                i= fin.read();
            }
            return out.toByteArray();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        return null;
    }


}