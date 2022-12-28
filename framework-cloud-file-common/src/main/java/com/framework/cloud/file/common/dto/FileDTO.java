package com.framework.cloud.file.common.dto;

import com.framework.cloud.common.group.Save;
import com.framework.cloud.common.group.Update;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * 文件 新增修改DTO
 *
 * @author wusiwei
 */
@Data
public class FileDTO {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "文件原名称")
    private String originalName;

    @ApiModelProperty(value = "文件新名称")
    private String newName;

    @ApiModelProperty(value = "文件后缀")
    private String ext;

    @ApiModelProperty(value = "文件大小(单位:kb)")
    private Long size;

    @ApiModelProperty(value = "文件路径")
    private String path;

    @ApiModelProperty(value = "业务id(业务未设置，则生成雪花返回给业务)")
    private Long bizId;

    public FileDTO(String originalName, String newName, String ext, Long size, String path, Long bizId) {
        this.originalName = originalName;
        this.newName = newName;
        this.ext = ext;
        this.size = size;
        this.path = path;
        this.bizId = bizId;
    }
}
