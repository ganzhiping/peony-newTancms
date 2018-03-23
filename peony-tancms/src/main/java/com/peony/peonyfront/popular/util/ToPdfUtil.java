package com.peony.peonyfront.popular.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.Pipeline;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.exceptions.CssResolverException;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

import java.io.*;

public class ToPdfUtil {
    public static class MyFontsProvider extends XMLWorkerFontProvider{
        public MyFontsProvider(){
            super(null,null);
        }
        @Override
        public Font getFont(final String fontname, String encoding, float size, final int style) {

            String fntname = fontname;
            if(fntname==null){
              // fntname="宋体";
               System.out.println("fileName=============1231==================");
               System.out.println("fileName=============123==================");
               System.out.println("fileName=============123==================");
                fntname = "/usr/share/fonts/simsun.ttc";
            }
            return super.getFont(fntname, encoding, size, style);
        }
    }
    private static String dirpath = "";
    public static void main(String[] args) throws FileNotFoundException, IOException, DocumentException, CssResolverException {
        convert2("D:\\InstallSoft\\Tomcat 7.0\\webapps\\ROOT\\files\\20180111\\123_1515651004116_专报.html","d:/test2.pdf");
        System.out.println("success");
    }

    public static void convert2(String infile, String outfile)
            throws FileNotFoundException, IOException, DocumentException,
            CssResolverException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document,
                new FileOutputStream(outfile));
        writer.setStrictImageSequence(true);
        document.open();
        ToPdfUtil.MyFontsProvider fontProvider = new ToPdfUtil.MyFontsProvider();
        fontProvider.addFontSubstitute("lowagie", "garamond");
        fontProvider.setUseUnicode(true);
        //使用我们的字体提供器，并将其设置为unicode字体样式
        CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);
        HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
        htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
        CSSResolver cssResolver = XMLWorkerHelper.getInstance()
                .getDefaultCssResolver(true);
        Paragraph paragraph=new Paragraph();
        paragraph.setLeading(1.5f);
        document.add(paragraph);
        Pipeline<?> pipeline = new CssResolverPipeline(cssResolver,
                new HtmlPipeline(htmlContext, new PdfWriterPipeline(document,
                        writer)));
        XMLWorker worker = new XMLWorker(pipeline, true);
        XMLParser p = new XMLParser(worker);
        File input = new File(infile);

        p.parse(new InputStreamReader(new FileInputStream(input), "UTF-8"));
        document.close();
    }
}