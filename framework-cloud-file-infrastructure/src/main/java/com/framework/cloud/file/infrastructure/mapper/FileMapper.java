package com.framework.cloud.file.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.framework.cloud.file.common.vo.*;
import com.framework.cloud.file.common.dto.*;
import com.framework.cloud.file.domain.entity.File;
import org.apache.ibatis.annotations.Param;

/**
 * 文件 数据库接口
 *
 * @author wusiwei
 */
public interface FileMapper extends BaseMapper<File> {

    IPage<FilePageVO> page(@Param("pg") Page<FilePageVO> page, @Param("param") FilePageDTO param);

}