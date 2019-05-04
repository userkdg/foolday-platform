package com.foolday.wechat.controller;

import com.foolday.common.dto.FantResult;
import com.foolday.common.exception.PlatformException;
import com.foolday.service.api.base.Image2DiskServiceApi;
import com.foolday.wechat.base.MultipartFileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Slf4j
@Api("文件上传")
@RestController
@RequestMapping("/file")
public class FileController {
    @Resource
    private Image2DiskServiceApi image2DiskServiceApi;

    @ApiOperation(value = "文件上传功能")
    @PostMapping(value = "/upload")
    public FantResult<String> upload(@ApiParam(value = "file", required = true) @RequestParam(value = "file", required = false) MultipartFile multipartFile) {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String imageId = image2DiskServiceApi.uploadImage(MultipartFileUtils.toFileDto(multipartFile))
                    .orElseThrow(() -> new PlatformException("文件上传失败"));
            return FantResult.ok(imageId);
        }
        return FantResult.fail("上传文件为空");
    }

    @ApiOperation(value = "文件删除功能")
    @PostMapping(value = "/delete")
    public FantResult<String> delete(@ApiParam(value = "文件id", required = true)
                                     @RequestParam(value = "fileIdArr") String... fileIdArr) {
        image2DiskServiceApi.deleteAll(fileIdArr);
        return FantResult.ok();
    }


}






