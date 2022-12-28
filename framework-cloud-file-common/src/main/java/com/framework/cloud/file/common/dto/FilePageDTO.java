package com.framework.cloud.file.common.dto;

import com.framework.cloud.common.base.BasePage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件 分页DTO
 *
 * @author wusiwei
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FilePageDTO extends BasePage {

    @ApiModelProperty(value = "文件原名称")
    private String name;

    @ApiModelProperty(value = "文件后缀")
    private String ext;

}