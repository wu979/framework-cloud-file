package com.framework.cloud.file.domain.repository;

import com.framework.cloud.common.base.PageVO;
import com.framework.cloud.mybatis.repository.BaseRepository;
import com.framework.cloud.file.common.vo.*;
import com.framework.cloud.file.common.dto.*;
import com.framework.cloud.file.domain.entity.File;

/**
 * 文件 数据层接口
 *
 * @author wusiwei
 */
public interface FileRepository extends BaseRepository<File> {

    /**
     * 文件分页列表
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

}