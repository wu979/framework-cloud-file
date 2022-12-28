package com.framework.cloud.file.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.framework.cloud.common.base.BaseTenant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.framework.cloud.mybatis.annotation.LongToBigDecimal;
import lombok.*;

/**
 * 文件 实体
 *
 * @author wusiwei
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_file")
@ApiModel(value="文件对象", description="文件")
public class File extends BaseTenant {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
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

    @ApiModelProperty(value = "文件路径盐值")
    private String salt;

    @ApiModelProperty(value = "业务id(业务未设置，则生成雪花返回给业务)")
    private Long bizId;

}