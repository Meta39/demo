package com.fu.demo.controller;

import com.fu.demo.util.FastDFSUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
public class FastDFSController {

    @Resource
    private FastDFSUtils fastDFSUtils;

    /**
     * fastdfs文件上传
     * @param file 文件
     * @return
     * @throws IOException
     */
    @PostMapping("fileUpload")
    public String upload(@RequestParam(name = "file") MultipartFile file){
        return fastDFSUtils.uploadFile(file);
    }

    /**
     * 文件删除
     * @param filePath fastdfs文件相对路径
     * @return
     */
    @PostMapping("fileDelete")
    public Boolean download(@RequestParam String filePath){
        return fastDFSUtils.deleteFile(filePath);
    }
}
