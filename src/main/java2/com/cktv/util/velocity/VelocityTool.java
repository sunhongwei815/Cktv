package com.cktv.util.velocity;

import com.cktv.util.exception.MessageException;
import com.trt.util.user.SessionUtil;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.*;
import java.util.Properties;

/**
 * Created by 11835 on 2016/8/17.
 */
public class VelocityTool {
    /**
     * 通过velocity生成Html
     *
     * @param htmlName:html名字
     * @param aimPath:保存html地址
     * @param vmPath:vm文件路径
     * @param context          :内容变量
     */
    public static void generateHtmlByVelocity(String htmlName, String aimPath, String vmPath, VelocityContext context) {
        Properties p = new Properties();
        p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, SessionUtil.getCurrentPath());//关键，制定访问路径，注意和配置的路径（pages）区别
        p.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
        p.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        p.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
        Velocity.init(p);
        Template template = Velocity.getTemplate(vmPath, "utf-8");
        File htmlFilefolder = new File(aimPath);
        PrintWriter pw = null;
        if (!htmlFilefolder.exists()) {
            htmlFilefolder.mkdirs();
        }
        try {
            File htmlFile=new File(aimPath+htmlName);
            pw = new PrintWriter(new FileOutputStream(htmlFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new MessageException("文件不存在！");
        }
        template.merge(context, pw);
        pw.close();
    }
}
