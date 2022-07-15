package com.fu.demo.controller;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import com.fu.demo.entity.Err;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

/**
 * word转pdf
 * 所使用的jar包需要单独引入
 */
@RestController
public class WordToPdfController {

    @PostMapping("wordToPdf")
    public String wordToPdf(@RequestParam("file") MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            String uploadPath = "D:/uploadFile";
            // 如果目录不存在则创建
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            //生成的文件名称
            String path = uploadPath + "/" + UUID.randomUUID() + ".pdf";
            File pdf = new File(path);
            if (!pdf.exists()) {
                pdf.createNewFile();
            }
            //输出文件
            FileOutputStream os = new FileOutputStream(pdf);
            //获取上传的文件流
            Document doc = new Document(file.getInputStream());
            doc.save(os, SaveFormat.PDF);//支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            //关闭流
            os.close();
            return path;
        } else {
            throw new Err("文件不能为空！");
        }
    }
}
