package com.fu.demo.minio;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {
    @Value("${minio.endpoint}")
    private String endpoint;//url
    @Value("${minio.port}")
    private int port;//端口号
    @Value("${minio.secure}")
    private boolean secure;//https?true:false
    @Value("${minio.accessKey}")
    private String accessKey;//账号
    @Value("${minio.secretKey}")
    private String secretKey;//密码

    @Bean
    public MinioClient getMinioClient(){
        return MinioClient.builder().endpoint(endpoint, port, secure).credentials(accessKey, secretKey).build();
    }
}
