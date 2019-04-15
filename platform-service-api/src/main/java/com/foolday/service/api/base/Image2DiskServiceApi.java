package com.foolday.service.api.base;

import com.foolday.serviceweb.dto.image.FileDto;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface Image2DiskServiceApi {

    File download(String imageId);

    /*
    下载
     */
    FileDto download(String imageId, int width, int height);

    /**
     * 定制图片缩略图
     *
     * @param imageId
     * @param width
     * @param height
     * @return
     */
    FileDto getThumbnail(String imageId, int width, int height);

    /**
     * 上传图片
     *
     * @param fileDto
     * @return
     */
    Optional<String> uploadImage(FileDto fileDto);

    /**
     * 上传图片
     *
     * @param fileDtos
     * @return
     */
    List<String> uploadImages(List<FileDto> fileDtos);
}
