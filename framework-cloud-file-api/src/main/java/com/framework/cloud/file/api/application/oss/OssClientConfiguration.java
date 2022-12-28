package com.framework.cloud.file.api.application.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.framework.cloud.file.domain.properties.FileProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 对象存储
 *
 * @author wusiwei
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(FileProperties.class)
@ConditionalOnProperty(prefix = "framework.file", name = "env", havingValue = "ONLINE")
public class OssClientConfiguration {

    private final FileProperties fileProperties;

    @Bean("ossClient")
    public OSS ossClient() {
        FileProperties.OssProperties ossProperties = fileProperties.getOss();
        String endpoint = ossProperties.getEndpoint();
        String accessKey = ossProperties.getAccessKey();
        String accessSecret = ossProperties.getAccessSecret();
        return new OSSClientBuilder().build(endpoint, accessKey, accessSecret);
    }

}
