package com.framework.cloud.file.common.msg;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文件消息
 *
 * @author wusiwei
 */
@Getter
@AllArgsConstructor
public enum FileMsg {

    /**
     * 异常消息
     */
    NOT_FILE("至少选择一个文件"),
    FILE_NOT_FOUND("%s :找不到指定文件"),
    FILE_PATH_NOT_FOUND("%s :找不到指定盘符文件"),
    FILE_NAME_NULL("%s :文件名称不存在"),
    FILE_OVER_LIMIT("%s :文件最大支持%s"),
    FILE_SUFFIX_NOT_SUPPORT("%s :不支持的文件类型"),
    FILE_UPLOAD_ERROR("%s :上传失败，未知异常"),
    ;

    private final String msg;

}
