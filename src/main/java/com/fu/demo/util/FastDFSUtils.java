package com.fu.demo.util;

import com.fu.demo.entity.Err;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * FastDFS文件管理工具类
 */
@Component
public class FastDFSUtils {

    private static final Logger log = LoggerFactory.getLogger(FastDFSUtils.class);

    @Resource
    private FastFileStorageClient fastFileStorageClient;

    public String uploadFile(MultipartFile file){
        String filePath;
        try {
            filePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), null).getFullPath();
        }catch (Exception e){
            throw new Err("上传失败："+e.getMessage());
        }
        return filePath;
    }

    public Boolean deleteFile(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return false;
        }
        try {
            StorePath storePath = StorePath.parseFromUrl(filePath);
            fastFileStorageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (Exception e) {
            log.error("删除失败！", e);
            throw new Err("删除失败："+e.getMessage());
        }
        return true;
    }
}
