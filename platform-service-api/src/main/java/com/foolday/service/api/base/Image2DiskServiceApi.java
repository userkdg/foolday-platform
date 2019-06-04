package com.foolday.service.api.base;

import com.foolday.common.base.BaseServiceApi;
import com.foolday.dao.image.ImageEntity;
import com.foolday.serviceweb.dto.image.FileDto;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public interface Image2DiskServiceApi extends BaseServiceApi<ImageEntity> {

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
     * 根据图片id+图片名称+文件目录 获取文件的路径
     *
     * @param storePath
     * @param imageId
     * @param name
     * @return
     */
    Path filePathByImageIdAndName(String storePath, String imageId, String name);

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

    /**
     * 文件id
     *
     * @param fileIdArr
     */
    void deleteAll(String... fileIdArr);


    void deleteOne(String fileId);

    String image2DbByFileDto(FileDto fileDto);

    FileDto uploadFileByFileDto(FileDto fileDto);
}
