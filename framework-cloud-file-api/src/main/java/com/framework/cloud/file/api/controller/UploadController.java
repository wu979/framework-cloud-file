package com.framework.cloud.file.api.controller;

import com.framework.cloud.common.result.R;
import com.framework.cloud.common.result.Result;
import com.framework.cloud.file.domain.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * 上传 前端控制器
 *
 * @author wusiwei
 */
@Slf4j
@Api(tags = "文件上传")
@RestController
@RequestMapping(path = "/upload")
public class UploadController {

    @Resource
    private UploadService uploadService;

    @ApiOperation(value = "上传文件")
    @GetMapping(value = "/path")
    public Result<Long> upload(@RequestParam(value = "bizId", required = false) Long bizId,
                               @RequestParam(value = "paths", required = true) String... paths) {
        return R.success(uploadService.upload(bizId, paths));
    }

    @ApiOperation(value = "上传文件")
    @GetMapping(value = "/file")
    public Result<Long> upload(@RequestParam(value = "bizId", required = false) Long bizId,
                               @RequestParam(value = "files", required = true) MultipartFile... files) {
        return R.success(uploadService.upload(bizId, files));
    }

    @ApiOperation(value = "上传文件")
    @PostMapping(value = "/{id}/fail")
    public Result<List<String>> fail(@PathVariable("id") Long bizId) {
        return R.success(uploadService.fail(bizId));
    }
}
