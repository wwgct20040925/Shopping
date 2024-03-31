package com.gct.MyProperties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix="shopping.minio") //读取节点
@Configuration
public class MinioProperties {

    private String endpointUrl;
    private String username;
    private String password;
    private String bucketName;

}