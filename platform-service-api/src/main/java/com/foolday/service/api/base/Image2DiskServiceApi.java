package com.foolday.service.api.base;

import com.foolday.serviceweb.dto.image.FileDto;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface Image2DiskServiceApi {

    Optional<String> upload(File file);

    File download(String imageId);

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
     * @param fileDtos
     * @return
     */
    List<String> uploadImages(List<FileDto> fileDtos);
}
