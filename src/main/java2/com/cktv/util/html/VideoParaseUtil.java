package com.cktv.util.html;

import com.cktv.util.exception.MessageException;
import com.cktv.util.file.ReadFileUtil;
import com.cktv.util.string.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by hws on 16/7/15.
 */
public class VideoParaseUtil {


    /**
     * 根据传入的html解析出第一个video
     *
     * @param html
     * @return Element 视频元素
     */
    public static Element getFristVideoElement(String html) {
        Document doc = Jsoup.parse(html);
        Elements videoElements = doc.select("video");
        for (Element element : videoElements) {
            return element;
        }
        throw new MessageException("该视频模板没有视频地址");
    }

    public static String getFirstVideoSrcFromStr(String html) {
        Element element = getFristVideoElement(html);
        return element.attr("src");
    }

    public static long getFirstX_coordinateFromStr(String html) {
        Element element = getFristVideoElement(html);
        String style = element.attr("style");

        String[] styles = style.split(";");
        for (String attr : styles) {
            if (attr.contains("left")) {
                String[] x = attr.split(":");
                return StringUtil.removeLastTwoCharToInteger(x[1]);
            }
        }
        throw new MessageException("html中没有left属性");
    }

    public static long getFirstY_coordinateFromStr(String html) {
        Element element = getFristVideoElement(html);
        String style = element.attr("style");
        String[] styles = style.split(";");
        for (String attr : styles) {
            if (attr.contains("top")) {
                String[] x = attr.split(":");
                return StringUtil.removeLastTwoCharToInteger(x[1]);
            }
        }
        throw new MessageException("html中没有top属性");
    }

    public static long getFirstWidthFromStr(String html) {
        Element element = getFristVideoElement(html);
        //视频分辨率为1920*1080
        String widthPercent = element.attr("width");
        return StringUtil.removeLastTwoCharToInteger(widthPercent);
    }

    public static long getFirstHeightFromStr(String html) {
        Element element = getFristVideoElement(html);
        String heightPercent = element.attr("height");
        return StringUtil.removeLastTwoCharToInteger(heightPercent);
    }


    /**
     * 根据传入的html解析出第一个video标签的视频地址
     *
     * @return
     */
    public static String getFirstVideoSrcFromFile(String filePath) {
        String html = ReadFileUtil.readStrFile(filePath);
        return getFirstVideoSrcFromStr(html);
    }

    /**
     * 根据传入的html解析出第一个video的x坐标
     *
     * @return
     */
    public static long getFirstVideoXFromFile(String filePath) {
        String html = ReadFileUtil.readStrFile(filePath);
        return getFirstX_coordinateFromStr(html);
    }

    /**
     * 根据传入的html解析出第一个video的Y坐标
     *
     * @return
     */
    public static long getFirstVideoYFromFile(String filePath) {
        String html = ReadFileUtil.readStrFile(filePath);
        return getFirstY_coordinateFromStr(html);
    }

    /**
     * 根据传入的html解析出第一个video的width
     *
     * @return
     */
    public static long getFirstVideoWidthFromFile(String filePath) {
        String html = ReadFileUtil.readStrFile(filePath);
        return getFirstWidthFromStr(html);
    }

    /**
     * 根据传入的html解析出第一个video的height
     *
     * @return
     */
    public static long getFirstVideoHeightFromFile(String filePath) {
        String html = ReadFileUtil.readStrFile(filePath);
        return getFirstHeightFromStr(html);
    }


    /**
     * 替换视频的src地址
     * @param filePath html文件路径
     * @param newSrc 新地址
     * @return
     */
    public static String replaceVideoSrcFromFile(String filePath,String newSrc){
        String html = ReadFileUtil.readStrFile(filePath);
        return replaceVideoSrc(html,newSrc);
    }

    /**
     * 替换视频的src地址
     * @param html html字符串
     * @param newSrc 新地址
     * @return
     */
    public static String replaceVideoSrc(String html,String newSrc){
        //System.out.println(html);
        Document doc = Jsoup.parse(html);
        Elements videoElements = doc.select("video");
        for (Element element : videoElements) {
            element.attr("src",newSrc);
        }
        return doc.html();
    }


}
