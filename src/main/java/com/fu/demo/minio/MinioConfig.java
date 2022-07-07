package com.fu.demo.minio;

import io.minio.MinioClient;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {
    @Value("${minio.endpoint}")
    private String endpoint;
    @Value("${minio.accessKey}")
    private String accessKey;//账号
    @Value("${minio.secretKey}")
    private String secretKey;//密码

    @Bean
    public MinioClient getMinioClient() throws InvalidPortException, InvalidEndpointException {
        return new MinioClient(endpoint,accessKey,secretKey);
    }
}
