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

    @PostMapping("upload")
    public String upload(@RequestParam(name = "file") MultipartFile file) throws IOException {
        return fastDFSUtils.uploadFile(file);
    }
}
