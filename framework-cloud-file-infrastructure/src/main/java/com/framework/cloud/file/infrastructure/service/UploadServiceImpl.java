package com.framework.cloud.file.infrastructure.service;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.framework.cloud.cache.cache.RedisSetCache;
import com.framework.cloud.common.utils.AssertUtil;
import com.framework.cloud.common.utils.DateUtil;
import com.framework.cloud.file.common.constant.FileConstant;
import com.framework.cloud.file.common.dto.FileDTO;
import com.framework.cloud.file.common.enums.EnvType;
import com.framework.cloud.file.common.msg.FileMsg;
import com.framework.cloud.file.domain.properties.FileProperties;
import com.framework.cloud.file.domain.service.FileService;
import com.framework.cloud.file.domain.service.UploadService;
import com.framework.cloud.file.domain.utils.FileUtil;
import com.framework.cloud.file.domain.utils.OssUtil;
import com.framework.cloud.file.domain.utils.UploadUtil;
import com.framework.cloud.mybatis.utils.IdUtil;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.apache.skywalking.apm.toolkit.trace.RunnableWrapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * 上传 服务实现类
 *
 * @author wusiwei
 */
@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(FileProperties.class)
public class UploadServiceImpl implements UploadService {

    private final RedisSetCache redisSetCache;
    private final FileService fileService;
    private final FileProperties fileProperties;
    private final AsyncTaskExecutor filePool;

    @Override
    public Long upload(Long bizId, String... paths) {
        AssertUtil.isEmpty(Arrays.asList(paths), FileMsg.NOT_FILE.getMsg());
        if (Objects.isNull(bizId)) {
            bizId = IdUtil.getId();
        }
        for (String path : paths) {
            MultipartFile file = FileUtil.fileToMultipartFile(path);
            if (!FileUtil.checkFile(file)) {
                redisSetCache.add(FileConstant.UPLOAD_KEY + bizId, String.format(FileMsg.FILE_NOT_FOUND.getMsg(), path));
                continue;
            }
            upload(bizId, file);
        }
        return bizId;
    }

    @Override
    public Long upload(Long bizId, MultipartFile... files) {
        AssertUtil.isEmpty(Arrays.asList(files), FileMsg.NOT_FILE.getMsg());
        if (Objects.isNull(bizId)) {
            bizId = IdUtil.getId();
        }
        List<String> errorMsg = new ArrayList<>();
        for (MultipartFile file : files) {
            String originalName = file.getOriginalFilename();
            if (!FileUtil.checkFile(file)) {
                errorMsg.add(String.format(FileMsg.FILE_NOT_FOUND.getMsg(), originalName));
                continue;
            }
            if (!FileUtil.checkFileName(originalName)) {
                errorMsg.add(String.format(FileMsg.FILE_NAME_NULL.getMsg(), originalName));
                continue;
            }
            long size = file.getSize();
            if (!FileUtil.checkSize(file.getSize(), fileProperties.getMaxSize())) {
                errorMsg.add(String.format(FileMsg.FILE_OVER_LIMIT.getMsg(), originalName, FileUtil.switchSize(fileProperties.getMaxSize())));
                continue;
            }
            String suffix = originalName.substring(originalName.lastIndexOf(StringPool.DOT));
            if (FileUtil.checkSuffix(fileProperties.getNotAllowExtension(), suffix)) {
                errorMsg.add(String.format(FileMsg.FILE_SUFFIX_NOT_SUPPORT.getMsg(), originalName));
                continue;
            }
            String uploadPath = uploadPath(bizId);
            String newName = FileUtil.newName(originalName, suffix);
            boolean flag = uploadFile(uploadPath, newName, file);
            if (!flag) {
                errorMsg.add(String.format(FileMsg.FILE_UPLOAD_ERROR.getMsg(), originalName));
                continue;
            }
            FileDTO fileDTO = new FileDTO(originalName, newName, suffix, size, uploadPath, bizId);
            filePool.execute(RunnableWrapper.of(() -> fileService.save(fileDTO)));
        }
        if (errorMsg.size() > 0) {
            redisSetCache.add(FileConstant.UPLOAD_KEY + bizId, errorMsg);
        }
        return bizId;
    }

    @Override
    public List<String> fail(Long bizId) {
        Set<String> failList = redisSetCache.get(FileConstant.UPLOAD_KEY + bizId, String.class);
        return Lists.newArrayList(failList);
    }

    private String uploadPath(Long bizId) {
        if (fileProperties.getPrefix().endsWith(StringPool.SLASH)) {
            return fileProperties.getPrefix() + DateUtil.getDate() + StringPool.SLASH + bizId;
        }
        return fileProperties.getPrefix() + StringPool.SLASH + DateUtil.getDate() + StringPool.SLASH + bizId;
    }

    private boolean uploadFile(String uploadPath, String newName, MultipartFile file) {
        boolean flag ;
        if (fileProperties.getEnv().equals(EnvType.OFFLINE)) {
            flag = UploadUtil.upload(file, uploadPath, newName);
        } else {
            flag = OssUtil.upload(file, fileProperties.getOss().getBucketName(), uploadPath, newName);
        }
        return flag;
    }
}
