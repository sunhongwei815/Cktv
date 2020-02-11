package com.cktv.util.file;

import com.cktv.util.exception.MessageException;

import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.TextRun;
import org.apache.poi.hslf.usermodel.RichTextRun;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.drawingml.x2006.main.*;
import org.openxmlformats.schemas.presentationml.x2006.main.CTGroupShape;
import org.openxmlformats.schemas.presentationml.x2006.main.CTShape;
import org.openxmlformats.schemas.presentationml.x2006.main.CTSlide;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 11835 on 2016/8/9.
 */
public class PptUtil {
    /**
     * @param file :ppt文件    （只能处理2007以下的）
     * @param save_adress;保存的磁盘位置
     * @param pictureSWebPath:图片访问的网络地址
     * @return List<Stirng>: 图片的具体位置
     */
    public static List<String> ChangePptToPictures(File file,String fileName, String save_adress,String pictureSWebPath){
        boolean isppt = checkFile(fileName);  //检查文件是否为ppt
        File files=new File(save_adress);
        if(!files.exists()){
            files.mkdirs();
        }
        List<String>pictures_adress=new ArrayList<>();//返回所有ppt路径
        if (!isppt) {
            throw new MessageException("该文件不是ppt,请上传PPT！");
        }
        try{
            InputStream is = new FileInputStream(file);
            SlideShow ppt=new SlideShow(is);
            is.close();
            Dimension pgsize = ppt.getPageSize();
            Slide[] slide = ppt.getSlides();
            for (int i = 0; i < slide.length; i++) {
                System.out.print("第" + i + "页。");
                TextRun[] truns = slide[i].getTextRuns();
                for (int k = 0; k < truns.length; k++) {
                    RichTextRun[] rtruns = truns[k].getRichTextRuns();
                    for (int l = 0; l < rtruns.length; l++) {
                        int index = rtruns[l].getFontIndex();
                        String name = rtruns[l].getFontName();
                        rtruns[l].setFontIndex(1);
                        rtruns[l].setFontName("宋体");
                        //System.out.println(rtruns[l].getText());
                    }
                }
                BufferedImage img = new BufferedImage(pgsize.width,
                        pgsize.height, BufferedImage.TYPE_INT_RGB);

                Graphics2D graphics = img.createGraphics();
                graphics.setPaint(Color.white);
                graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width,
                        pgsize.height));
                slide[i].draw(graphics);

                // 这里设置图片的存放路径和图片的格式(jpeg,png,bmp等等),注意生成文件路径
                FileOutputStream out = new FileOutputStream(save_adress + (i + 1) + ".jpeg");
                pictures_adress.add(("assets/" + (i + 1) + ".jpeg"));
                javax.imageio.ImageIO.write(img, "jpeg", out);
                out.close();
            }
        return pictures_adress;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new MessageException("文件未找到！");
        } catch (Exception e) {
            //如果出现其他异常，采用第二种方式，比如上传的文件是2007时
            return ChangePptToPictures2(file, fileName, save_adress, pictureSWebPath);
        }

    }

    /**
     * @param file :ppt文件    （可以处理2007以上的）
     * @param save_adress;保存的磁盘位置
     * @param pictureSWebPath:图片访问的网络地址
     * @return List<Stirng>: 图片的具体位置
     */
    public static List<String> ChangePptToPictures2(File file,String fileName, String save_adress,String pictureSWebPath) {
        boolean isppt = checkFile(fileName);  //检查文件是否为ppt
        File files = new File(save_adress);
        if (!files.exists()) {
            files.mkdirs();
        }
        List<String> pictures_adress = new ArrayList<>();//返回所有ppt路径
        if (!isppt) {
            throw new MessageException("该文件不是ppt,请上传PPT！");
        }
        InputStream is = null;
        XMLSlideShow ppt = null;
        try {
            is = new FileInputStream(file);
            ppt = new XMLSlideShow(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Dimension pgsize = ppt.getPageSize();
        //获得ppt所有页面
        XSLFSlide[] slide = ppt.getSlides();
        /**
         * 下面的XML配置文件定义转换后的图片内的文字字体，否则将会出现转换后的图片内的中文为乱码
         */
        String xmlFontFormat1 = "<xml-fragment xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" xmlns:p=\"http://schemas.openxmlformats.org/presentationml/2006/main\">";
        String xmlFontFormat2 = "<a:rPr lang=\"zh-CN\" altLang=\"en-US\" dirty=\"0\" smtClean=\"0\"> ";
        String xmlFontFormat3 = "<a:latin typeface=\"+mj-ea\"/> ";
        String xmlFontFormat4 = "</a:rPr>";
        String xmlFontFormat5 = "</xml-fragment>";
        StringBuffer xmlFontFormatStringBuffer = new StringBuffer();
        xmlFontFormatStringBuffer.append(xmlFontFormat1);
        xmlFontFormatStringBuffer.append(xmlFontFormat2);
        xmlFontFormatStringBuffer.append(xmlFontFormat3);
        xmlFontFormatStringBuffer.append(xmlFontFormat4);
        xmlFontFormatStringBuffer.append(xmlFontFormat5);
        for (int i = 0; i < slide.length; i++) {
            System.out.print("第" + i + "页。");
            CTSlide ctSlide = slide[i].getXmlObject();
            CTGroupShape gs = ctSlide.getCSld().getSpTree();
            CTShape[] shapes = gs.getSpArray();
            for (CTShape ctShape : shapes) {
                CTTextBody oneCTTextBody = ctShape.getTxBody();
                if (null == oneCTTextBody) {
                    continue;
                }
                CTTextParagraph[] oneCTTextParagraph = oneCTTextBody.getPArray();
                //设置字体
                CTTextFont oneCTTextFont = null;
                try {
                    oneCTTextFont = CTTextFont.Factory.parse(xmlFontFormatStringBuffer.toString());
                } catch (XmlException e) {
                    e.printStackTrace();
                }
                for (CTTextParagraph textParagraph : oneCTTextParagraph) {
                    CTRegularTextRun[] oneCTRegularTextRunArray = textParagraph.getRArray();
                    for (CTRegularTextRun oneCTRegularTextRun : oneCTRegularTextRunArray) {
                        CTTextCharacterProperties oneCTTextCharacterProperties = oneCTRegularTextRun.getRPr();
                        oneCTTextCharacterProperties.setLatin(oneCTTextFont);
                    }
                }
            }
            //设置图片属性
            BufferedImage img = new BufferedImage(pgsize.width,
                    pgsize.height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = img.createGraphics();
            graphics.setPaint(Color.white);
            graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width,
                    pgsize.height));
            slide[i].draw(graphics);
            // 这里设置图片的存放路径和图片的格式(jpeg,png,bmp等等),注意生成文件路径
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(save_adress + (i + 1) + ".jpeg");
                javax.imageio.ImageIO.write(img, "jpeg", out);
                out.close();
                pictures_adress.add(("assets/" + (i + 1) + ".jpeg"));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new MessageException("文件未找到！");
            } catch (IOException e) {
                e.printStackTrace();
                throw new MessageException("IO异常！");
            }
        }
        return pictures_adress;
    }


    // function 检查文件是否为PPT
    public static boolean checkFile(String filename) {

        boolean isppt = false;
        String suffixname = null;

        if (filename != null && filename.indexOf(".") != -1) {
            suffixname = filename.substring(filename.indexOf("."));
            System.out.println(suffixname);
            if (suffixname.equals(".ppt")||suffixname.equals(".pptx")) {
                isppt = true;
            }
            return isppt;
        } else {
            return isppt;
        }
    }
}

