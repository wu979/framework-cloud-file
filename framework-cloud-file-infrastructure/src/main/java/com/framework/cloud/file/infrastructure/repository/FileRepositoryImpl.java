package com.framework.cloud.file.infrastructure.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.framework.cloud.common.base.PageParam;
import com.framework.cloud.common.base.PageVO;
import com.framework.cloud.mybatis.repository.impl.BaseRepositoryImpl;
import com.framework.cloud.file.common.vo.*;
import com.framework.cloud.file.common.dto.*;
import com.framework.cloud.file.domain.entity.File;
import com.framework.cloud.file.domain.repository.FileRepository;
import com.framework.cloud.file.infrastructure.converter.FileConverter;
import com.framework.cloud.file.infrastructure.mapper.FileMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文件 数据实现层
 *
 * @author wusiwei
 */
@Repository
@AllArgsConstructor
public class FileRepositoryImpl extends BaseRepositoryImpl<FileMapper, File> implements FileRepository {

    private final FileConverter fileConverter;

    @Override
    public PageVO<FilePageVO> page(FilePageDTO param) {
        Page<FilePageVO> page = PageParam.buildOrder(param);
        IPage<FilePageVO> list = this.baseMapper.page(page, param);
        return PageVO.page(list);
    }

    @Override
    public FileInfoVO info(Long id) {
        File file = this.getById(id);
        return fileConverter.info(file);
    }
}