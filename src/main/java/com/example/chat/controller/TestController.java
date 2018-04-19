package com.example.chat.controller;

import com.example.chat.common.FileUtil;
import com.example.chat.config.WebAppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author liuyiqian
 */
@Controller
public class TestController {

    @Autowired
    private WebAppConfig webAppConfig;

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {
        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();
        String path = webAppConfig.getLocation();
        try {
            FileUtil.saveFile(file, path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }
}
