package com.peony.peonyfront.popular.service;


/*import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;*/
import com.peony.core.base.pojo.Pagination;
import com.peony.peonyfront.popular.dao.PopularMapper;
import com.peony.peonyfront.popular.model.Popular;
import com.peony.peonyfront.popular.util.ToPdfUtil;
import com.peony.peonyfront.popular.util.Util;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;
import org.w3c.tidy.Tidy;

/*
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
*/

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/8.
 */
@Service
public class PopularServiceImpl implements PopularService {
    @Resource
    private PopularMapper popularMapper;
    private Configuration configuration = null;
    public PopularServiceImpl() {
        configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
    }

    @Override
    public Pagination<Popular> selectByPage(Popular popular) {
        List<Popular> briefreports = this.popularMapper.selectByPage(popular);
        return new Pagination<Popular>(briefreports, popular.getPageParameter());
    }


    @Override
    public Popular selectByPopularId(Popular popular) {
        return popularMapper.selectByPopularId(popular);
    }

    @Override
    public void delete(Popular popular) {
        popularMapper.update(popular);
    }


    @Override
    public void createDoc(String title,String fileName, String template, Map<String, Object> dataMap, HttpServletRequest request, HttpServletResponse response, String type) {
        SimpleDateFormat datatime = new SimpleDateFormat("yyyyMMdd");
        String ctxPath = request.getSession().getServletContext().getRealPath("/") + "files/" + datatime.format(new Date()) + "/";
        String path = request.getSession().getServletContext().getRealPath("/") + "template/popular/";
        File templateFile = new File(path);
        if (!templateFile.isDirectory()) {
            templateFile.mkdirs();
        }
        Template t = null;
        try {
            configuration.setDirectoryForTemplateLoading(templateFile);
            // test.ftl为要装载的模�?
            t = configuration.getTemplate(template);
        } catch (IOException e) {
            System.out.println(" createDoc   Error ,.........");
            e.printStackTrace();
        }
        File dirFile = new File(ctxPath);
        if (!dirFile.isDirectory()) {
            dirFile.mkdirs();
        }
        System.out.println("====================="+fileName+"==========================");
        File outFile = new File(ctxPath + fileName);
        Writer out = null;
        try {
            try {
                out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        try {
            t.process(dataMap, out);
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 添加到response，提供下载
        try {
            if("PDF".equals(type)){
                String filePath =ctxPath +title + ".pdf";
                exportPdf(ctxPath + fileName,filePath, request, response);
                export(filePath,title+ ".pdf",request, response,type);
            }else{
                export(ctxPath + fileName, title, request, response,type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 导出pdf add by huangt 2012.6.1
    public void exportPdf(String urlStr ,String outputFile, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ToPdfUtil pdf = new ToPdfUtil();
        pdf.convert2(urlStr,outputFile);
    }

    /**
     *
     * @param fileName
     * @param request
     * @param response
     * @throws Exception
     */
    public void export(String downLoadPath, String fileName, HttpServletRequest request, HttpServletResponse response,String type) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        java.io.BufferedInputStream bis = null;
        java.io.BufferedOutputStream bos = null;
        try {
            long fileLength = new File(downLoadPath).length();
            if("PDF".equals(type)){
                response.setCharacterEncoding("UTF-8");
                response.setHeader("Expires", "0");
                response.setHeader("Cache-Control",
                        "must-revalidate, post-check=0, pre-check=0");
                response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.replace(" ","").getBytes("GB2312"), "ISO_8859_1"));
                response.setHeader("Pragma", "public");
                response.setContentType("application/pdf");
            }else{
                response.setContentType("application/msword");
                response.setHeader("Content-disposition", "attachment; filename=" +  new String(fileName.replace(" ","").getBytes("GB2312"), "ISO_8859_1")+".doc");
                response.setHeader("Content-Length", String.valueOf(fileLength));
            }
            bis = new BufferedInputStream(new FileInputStream(downLoadPath));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }

    }
}
