package com.framework.cloud.file.domain.properties;

import com.framework.cloud.file.common.enums.EnvEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * file
 *
 * @author wusiwei
 */
@Data
@ConfigurationProperties(prefix = "framework.file")
public class FileProperties {

    /**
     * 环境变量 ( offline , online )
     */
    private EnvEnum env;

    /**
     * 文件上传路径前缀
     */
    private String prefix;

    /**
     * 允许上传文件大小(默认50MB)
     */
    private Integer maxSize = 50 * 1024 * 1024;

    /**
     * oss 配置
     */
    private OssProperties oss;

    /**
     * 不允许的格式
     */
    private List<String> notAllowExtension;

    @Data
    public static class OssProperties {

        private String endpoint;

        /**
         * 秘钥
         */
        private String accessKey;

        /**
         * 秘钥
         */
        private String accessSecret;

        /**
         * 桶
         */
        private String bucketName;

        /**
         * 批量下载最大个数
         */
        private Integer maxKeys;

    }
}
