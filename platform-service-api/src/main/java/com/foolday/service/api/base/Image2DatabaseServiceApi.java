package com.foolday.service.api.base;

import com.foolday.common.enums.ImageType;
import com.foolday.dao.image.ImageEntity;
import com.foolday.serviceweb.dto.image.FileDto;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Image2DatabaseServiceApi {
    /**
     * 定制图片两个bean名称
     */
    String BEAN_2_DISK_SERVICE = "image2diskService";

    String BEAN_2_DATABASE_SERVICE = "image2diskService";

    Optional<String> upload(File file);

    File download(String imageId);

    ImageEntity get(String imageId);

    /**
     * 定制图片缩略图
     *
     * @param imageId
     * @param width
     * @param height
     * @return
     */
    Map<File, ImageType> getThumbnail(String imageId, int width, int height);

    Optional<String> uploadImages(List<FileDto> fileDtos);
}
