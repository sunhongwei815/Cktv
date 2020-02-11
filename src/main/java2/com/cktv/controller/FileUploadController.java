//package com.cktv.controller;
//
//import com.cktv.com.cktv.config.Config;
//import com.cktv.util.exception.MessageException;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;
//import org.springframework.web.multipart.commons.CommonsMultipartResolver;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.File;
//import java.io.IOException;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.Map;
//
///
// * Created by linpeng123l on 16/5/6.
// * lp
// */
//@RequestMapping(value = "file")
//@Controller
//public class FileUploadController extends BaseController {
//
//    @RequestMapping(value = "fileUpload")
//    @ResponseBody
//    public Map<String, Object> fileUpload(HttpServletRequest request) throws IOException {
//        try {
//            //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
//            String saveRelativePath = "";
//            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
//            //检查form中是否有enctype="multipart/form-data"
//            if (multipartResolver.isMultipart(request)) {
//                //将request变成多部分request
//                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
//                //获取multiRequest 中所有的文件名
//                Iterator iter = multiRequest.getFileNames();
//                while (iter.hasNext()) {
//                    //一次遍历所有文件
//                    MultipartFile file = multiRequest.getFile(iter.next().toString());
//                    if (file != null) {
//                        saveRelativePath = Config.TPL_IMG_SAVE_PATH + "/" + new Date().getTime() + "." + file.getOriginalFilename().split("\\.")[1];
//                        String filePath = request.getSession().getServletContext().getRealPath("") + "/" + saveRelativePath;
//                        File saveFile = new File(filePath);
//                        if (!saveFile.getParentFile().exists()) {
//                            saveFile.getParentFile().mkdirs();
//                        }
//                        file.transferTo(saveFile);
//                    }
//                }
//            }
//            Map<String, Object> successMsg = generateSuccessMsg("上传成功");
//            successMsg.put("filePath", "/" + Config.PROJECT_NAME + "/" + saveRelativePath);
////            return successMsg;
////        } catch (Exception e) {
//            e.printStackTrace();
//            throw new MessageException("文件上传出错");
//        }
//    }
//
//}
