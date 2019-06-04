package com.foolday.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.foolday.admin.base.MultipartFileUtils;
import com.foolday.common.dto.FantResult;
import com.foolday.common.util.PlatformAssert;
import com.foolday.dao.image.ImageEntity;
import com.foolday.service.api.base.Image2DiskServiceApi;
import com.foolday.serviceweb.dto.image.FileDto;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
@Api(tags = {"图片接口"})
@RestController
@RequestMapping("/image")
public class ImageController {

    @Resource
    private Image2DiskServiceApi image2DiskServiceApi;

    @ApiOperation(value = "上传一个文件")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "正常返回", response = FantResult.class)})
    @PostMapping("/upload/file")
    public FantResult<List<String>> add(@ApiParam(value = "文件数组", required = true)
                                        @RequestParam(value = "file") MultipartFile multipartFile) {
        PlatformAssert.isFalse(multipartFile.isEmpty(), "请上传文件");
        List<FileDto> fileDtos = Stream.of(multipartFile).map(MultipartFileUtils::toFileDto).collect(Collectors.toList());
//        List<FileDto> fileDtos = Lists.newArrayList();
        List<String> imageIds = image2DiskServiceApi.uploadImages(fileDtos);
        return FantResult.ok(imageIds);
    }

    //    public FantResult<List<String>> add(@RequestParam(value = "files", required = false) MultipartFile[] multipartFiles) {
    //    public FantResult<List<String>> add(HttpServletRequest request) {
//        List<MultipartFile> multipartFiles = ((MultipartHttpServletRequest) request).getFiles("files");
//    public FantResult<List<String>> add(@RequestParam("files") MultipartHttpServletRequest multipartFiles) {
//    @ApiIgnore(value = "目前多文件上传swagger测试有问题")
    @ApiOperation(value = "上传多个或一个文件")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "正常返回", response = FantResult.class)})
    @PostMapping(value = "/uplaod/files")
    public FantResult<List<String>> add(HttpServletRequest request) {
        List<MultipartFile> multipartFiles = ((MultipartHttpServletRequest) request).getFiles("files");
        PlatformAssert.isTrue(!multipartFiles.isEmpty(), "请上传文件");
        List<FileDto> fileDtos = multipartFiles.stream().map(MultipartFileUtils::toFileDto).collect(Collectors.toList());
        List<String> imageIds = image2DiskServiceApi.uploadImages(fileDtos);
        return FantResult.ok(imageIds);
    }

    @ApiOperation(value = "下载文件")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "正常返回", response = ResponseEntity.class)})
    @GetMapping("/download")
    public ResponseEntity<FileSystemResource> download(@ApiParam(value = "文件id", required = true)
                                                       @RequestParam("id") String id,
                                                       @ApiParam(value = "可指定下载文件的宽,填-1为原图下", required = true)
                                                       @RequestParam(value = "width", required = false) int width,
                                                       @ApiParam(value = "可指定下载文件的高,填-1为原图下", required = true)
                                                       @RequestParam(value = "height", required = false) int height,
                                                       HttpServletResponse response) throws UnsupportedEncodingException {
        File downloadFile;
        if (width == -1 || height == -1) {
            downloadFile = image2DiskServiceApi.download(id);
        } else {
            FileDto fileDto = image2DiskServiceApi.download(id, width, height);
            downloadFile = (fileDto == null) ? null : fileDto.getFile();
        }
        if (downloadFile == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        String fileName = URLEncoder.encode(downloadFile.getName(), StandardCharsets.UTF_8.toString());
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + downloadFile.getName() + "\"; filename*=\"utf-8''" + fileName + "\"")
//                .cacheControl(CacheControl.maxAge(1, TimeUnit.DAYS))
                .body(new FileSystemResource(downloadFile
                ));
    }

    @ApiOperation(value = "查看图片")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "正常返回", response = ResponseEntity.class)})
    @GetMapping("/viewImage/size")
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
    public FantResult<List<ImageEntity>> list() {
        @SuppressWarnings("unchecked")
        LambdaQueryWrapper<ImageEntity> eq = image2DiskServiceApi.lqWrapper()
                .orderByDesc(ImageEntity::getType, ImageEntity::getUpdateTime);
        List<ImageEntity> entity = image2DiskServiceApi.selectList(eq);
        return FantResult.ok(entity);
    }
}
