package com.framework.cloud.file.domain.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 上传 服务层接口
 *
 * @author wusiwei
 */
public interface UploadService {

    /**
     * 上传
     * @param paths 文件路径
     * @return 唯一id
     */
    Long upload(Long bizId, String... paths);

    /**
     * 上传
     * @param files 文件
     * @return 唯一id
     */
    Long upload(Long bizId, MultipartFile... files);

    /**
     * 失败文件
     * @param bizId 唯一id
     * @return 错误消息列表
     */
    List<String> fail(Long bizId);
}
