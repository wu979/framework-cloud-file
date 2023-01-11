package com.framework.cloud.file.infrastructure.service;

import com.framework.cloud.common.base.PageVO;
import com.framework.cloud.common.utils.CopierUtil;
import com.framework.cloud.common.utils.MD5Util;
import com.framework.cloud.file.common.dto.FileDTO;
import com.framework.cloud.file.common.dto.FilePageDTO;
import com.framework.cloud.file.common.vo.FileInfoVO;
import com.framework.cloud.file.common.vo.FilePageVO;
import com.framework.cloud.file.domain.entity.File;
import com.framework.cloud.file.domain.repository.FileRepository;
import com.framework.cloud.file.domain.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文件 服务实现类
 *
 * @author wusiwei
 */
@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Override
    public PageVO<FilePageVO> page(FilePageDTO param) {
        return fileRepository.page(param);
    }

    @Override
    public FileInfoVO info(Long id) {
        return fileRepository.info(id);
    }

    @Override
    public boolean save(FileDTO param) {
        File file = new File();
        CopierUtil.copyProperties(param, file);
        file.setSalt(MD5Util.encode(param.getPath()));
        return fileRepository.save(file);
    }

    @Override
    public boolean removes(List<Long> ids) {
        return fileRepository.removeByIds(ids);
    }

}