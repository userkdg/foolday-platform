package com.foolday.wechat.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.foolday.common.dto.FantResult;
import com.foolday.common.exception.PlatformException;
import com.foolday.dao.image.ImageEntity;
import com.foolday.service.api.base.Image2DiskServiceApi;
import com.foolday.wechat.base.MultipartFileUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Api(value = "文件上传", tags = "文件上传")
@Controller
@RequestMapping("/file")
public class FileController {
    @Resource
    private Image2DiskServiceApi image2DiskServiceApi;

    @ApiOperation(value = "文件上传功能")
    @PostMapping(value = "/upload")
    @ResponseBody
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
    @ResponseBody
    public FantResult<String> delete(@ApiParam(value = "文件id", required = true)
                                     @RequestParam(value = "fileIdArr") String... fileIdArr) {
        image2DiskServiceApi.deleteAll(fileIdArr);
        return FantResult.ok();
    }


    @ApiOperation(value = "查看图片")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "正常返回", response = ResponseEntity.class)})
    @PostMapping("/viewImage/size")
    public ResponseEntity<FileSystemResource> view(@ApiParam("原文件id") @RequestParam("id") String id,
                                                   @ApiParam("宽度(px)") @RequestParam("width") Integer width,
                                                   @ApiParam("高度(px)") @RequestParam("height") Integer height) {
        File file = image2DiskServiceApi.getThumbnail(id, width, height).getFile();
        if (file == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
                .cacheControl(CacheControl.maxAge(1, TimeUnit.DAYS))
                .body(new FileSystemResource(file));

    }

    @ApiOperation("获取图片 list")
    @GetMapping("/list")
    @ResponseBody
    public FantResult<List<ImageEntity>> list() {
        @SuppressWarnings("unchecked")
        LambdaQueryWrapper<ImageEntity> eq = image2DiskServiceApi.lqWrapper()
                .orderByDesc(ImageEntity::getType, ImageEntity::getUpdateTime);
        List<ImageEntity> entity = image2DiskServiceApi.selectList(eq);
        return FantResult.ok(entity);
    }
}






