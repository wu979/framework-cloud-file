package com.framework.cloud.file.infrastructure.converter;

import com.framework.cloud.file.common.vo.*;
import com.framework.cloud.file.domain.entity.File;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * 文件 转换器
 *
 * @author wusiwei
 */
@Mapper(componentModel = "spring")
public interface FileConverter {

    /**
     * 文件 详情转换
     *
     * @param file 实体
     * @return 详情
     */
    FileInfoVO info(File file);

}