package com.framework.cloud.file.domain.service;

import com.framework.cloud.common.base.PageVO;
import com.framework.cloud.file.common.vo.*;
import com.framework.cloud.file.common.dto.*;
import com.framework.cloud.file.domain.entity.File;

import java.util.List;

/**
 * 文件 服务层接口
 *
 * @author wusiwei
 */
public interface FileService {
    /**
     * 文件 分页
     *
     * @param param 分页参数
     * @return 数据
     */
    PageVO<FilePageVO> page(FilePageDTO param);

    /**
     * 详情
     *
     * @param id 主键
     * @return 详情
     */
    FileInfoVO info(Long id);

    /**
     * 新增
     *
     * @param param 新增参数
     * @return bool
     */
    boolean save(FileDTO param);

    /**
     * 删除
     *
     * @param ids 主键
     * @return bool
     */
    boolean removes(List<Long> ids);

}