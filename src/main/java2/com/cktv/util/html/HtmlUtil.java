package com.cktv.util.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by hws on 16/7/19.
 */
public class HtmlUtil {


    public String replaceVideoSrc(String html,String newSrc){
        Document doc = Jsoup.parse(html);
        Elements videoElements = doc.select("video");
        for (Element element : videoElements) {
            element.attr("src",newSrc);
        }
        return doc.html();
    }

}

