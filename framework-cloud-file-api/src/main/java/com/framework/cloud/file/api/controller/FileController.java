package com.framework.cloud.file.api.controller;

import com.framework.cloud.common.base.PageVO;
import com.framework.cloud.common.group.Save;
import com.framework.cloud.common.group.Update;
import com.framework.cloud.common.result.R;
import com.framework.cloud.common.result.Result;
import com.framework.cloud.file.common.dto.*;
import com.framework.cloud.file.common.vo.*;
import com.framework.cloud.file.domain.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 文件 前端控制器
 *
 * @author wusiwei
 */
@Slf4j
@Api(tags = "文件")
@RestController
@RequestMapping(path = "/file")
public class FileController {

    @Resource
    private FileService fileService;

    @ApiOperation(value = "文件列表")
    @PostMapping(value = "/page")
    public Result<PageVO<FilePageVO>> page(@ApiParam("条件") @RequestBody FilePageDTO param) {
        return R.success(fileService.page(param));
    }

    @ApiOperation(value = "文件详情")
    @GetMapping(value = "/{id}/info")
    public Result<FileInfoVO> info(@ApiParam("主键") @PathVariable("id") Long id) {
        return R.success(fileService.info(id));
    }

    @ApiOperation(value = "文件删除")
    @DeleteMapping(value = "/removes")
    public Result<Boolean> removes(@ApiParam("主键") @RequestBody List<Long> ids) {
        return R.success(fileService.removes(ids));
    }

}
