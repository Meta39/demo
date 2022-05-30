package com.fu.demo.util;

import com.github.tobato.fastdfs.domain.conn.FdfsWebServer;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Description FastDFS文件管理工具类
 */
@Component
public class FastDFSUtils {

    private static final Logger log = LoggerFactory.getLogger(FastDFSUtils.class);

    @Resource
    private FastFileStorageClient fastFileStorageClient;
    @Resource
    private FdfsWebServer fdfsWebServer;

    public String uploadFile(MultipartFile file) throws IOException {
        StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), null);
        String fullPath = storePath.getFullPath();
//        return getResAccessUrl(fullPath);
        return fullPath;
    }

    public String uploadFile(File file) {
        try {
            FileInputStream inputStream = new FileInputStream(file);
            StorePath storePath = fastFileStorageClient.uploadFile(inputStream, file.length(), FilenameUtils.getExtension(file.getName()), null);
            return storePath.getFullPath();
        } catch (Exception e) {
            log.error("上传文件失败！", e);
            return null;
        }
    }

    public byte[] downloadFile(String filePath) throws IOException{
        StorePath storePath = StorePath.parseFromUrl(filePath);
        byte[] bytes = fastFileStorageClient.downloadFile(storePath.getGroup(), storePath.getPath(), new DownloadByteArray());
        return bytes;
    }

    public Boolean deleteFile(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return false;
        }
        try {
            StorePath storePath = StorePath.parseFromUrl(filePath);
            fastFileStorageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (Exception e) {
            log.error("上传文件失败！", e);
            return false;
        }
        return true;
    }

    /*
     * 封装文件完整URL地址
     */
    public String getResAccessUrl(String path) {
        String url = fdfsWebServer.getWebServerUrl() + path;
        log.debug("上传文件地址为：\n" + url);
        return url;
    }
}
