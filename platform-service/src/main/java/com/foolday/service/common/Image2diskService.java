package com.foolday.service.common;

import com.foolday.common.base.BaseServiceUtils;
import com.foolday.common.base.BeanFactory;
import com.foolday.common.enums.ImageType;
import com.foolday.common.enums.ThreadPoolType;
import com.foolday.common.util.ImageUtils;
import com.foolday.common.util.PlatformAssert;
import com.foolday.common.util.UuidUtils;
import com.foolday.dao.image.ImageEntity;
import com.foolday.dao.image.ImageMapper;
import com.foolday.service.api.base.Image2DiskServiceApi;
import com.foolday.serviceweb.dto.image.FileDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;

import static java.util.stream.Collectors.toList;

/**
 * 通过Image2DiskServiceApi注入实例时由于多个类实现了，要在@Auto wired 或@Resource 中选择对应的bean实例
 */
@Slf4j
@Service
@Transactional
public class Image2diskService implements Image2DiskServiceApi {

    @Resource
    private ImageMapper imageMapper;

    @Resource
    private ImageConfigProperty imageConfigProperty;

    @Resource(name = ThreadPoolType.CommonBlockThreadPool)
    private ExecutorService commonBlockThreadPool;

    @Override
    public File download(String imageId) {
        ImageEntity imageEntity = BaseServiceUtils.checkOneById(imageMapper, imageId);
        return Paths.get(imageConfigProperty.getStorePath(), (imageId + imageEntity.getName())).toFile();
    }

    @Override
    public FileDto download(String imageId, int width, int height) {
        return getThumbnail(imageId, width, height);
    }

    /**
     * 获取缩列图
     *
     * @param sourceImageId
     * @param width
     * @param height
     * @return
     */
    @Override
    public FileDto getThumbnail(String sourceImageId, int width, int height) {
        ImageEntity oldImage = BaseServiceUtils.checkOneById(imageMapper, sourceImageId);
        if (oldImage.getWidth().equals(width) && oldImage.getHeight().equals(height)) {
            return FileDto.builder().file(filePathByImageIdAndName(oldImage.getId(), oldImage.getName()).toFile()).build();
        } else if (width == 0 && height == 0) {
            return FileDto.builder().file(filePathByImageIdAndName(oldImage.getId(), oldImage.getName()).toFile()).build();
        } else {
            return createThumbnail(oldImage, width, height);
        }
    }


    /**
     * 通过配置+图片id+问图片名称获取文件磁盘路径
     *
     * @param imageId
     * @param name
     * @return
     */
    public Path filePathByImageIdAndName(String imageId, String name) {
        Path path = Paths.get(imageConfigProperty.getStorePath(), (imageId + name));
        PlatformAssert.isTrue(Files.exists(path), "获取文件{}失败!文件已不存在");
        return path;
    }


    /**
     * 通过配置+图片id+问图片名称获取文件磁盘路径
     *
     * @param storePath SpringContextUtils.getBean(ImageConfigProperty.class).getStorePath();
     * @param imageId
     * @param name
     * @return
     */
    @Override
    public Path filePathByImageIdAndName(@Nullable String storePath, String imageId, String name) {
        if (StringUtils.isBlank(storePath))
            storePath = SpringContextUtils.getBean(ImageConfigProperty.class).getStorePath();
        Path path = Paths.get(storePath, (imageId + name));
        PlatformAssert.isTrue(Files.exists(path), "获取文件" + path + "失败!文件已不存在");
        return path;
    }

    /**
     * 基于原图进行缩略图创建+入库保存
     *
     * @param imageEntity
     * @param width
     * @param height
     * @return
     */
    public FileDto createThumbnail(ImageEntity imageEntity, int width, int height) {
        Path oldImgPath = filePathByImageIdAndName(imageEntity.getId(), imageEntity.getName());
        // 生成缩略图
        // 写入image表和文件目录
        String newImgId = UuidUtils.uuid32();
        String newImagePath = imageConfigProperty.getStorePath() + File.separator + newImgId + imageEntity.getName();
        Path newImgPath = Paths.get(newImagePath);
        ImageUtils.resize(oldImgPath, newImgPath, width, height);
        // 异步写入数据库信息
        commonBlockThreadPool.execute(() -> {
            ImageEntity image = new ImageEntity();
            image.setId(newImgId);
            image.setHeight(height);
            image.setWidth(width);
            image.setName(imageEntity.getName());
            image.setType(ImageType.JPG);
            image.setDescription(imageEntity.getDescription());
            image.setOrigin(imageEntity.getOrigin());
            image.setRemark(imageEntity.getRemark());
            image.setCreateTime(LocalDateTime.now());
            image.setSize(newImgPath.toFile().length());
            int insert = imageMapper.insert(image);
            log.info("文件{}写入数据库,结果为{}", newImgId, (insert == 1));
        });
        return FileDto.builder().file(newImgPath.toFile()).imageId(newImgId).build();
    }

    /**
     * 上传一张
     *
     * @param fileDto
     * @return
     */
    @Override
    public Optional<String> uploadImage(FileDto fileDto) {
        if (fileDto != null && !fileDto.isEmpty()) {
            List<String> imageIds = uploadImages(Collections.singletonList(fileDto));
            if (!imageIds.isEmpty())
                return Optional.ofNullable(imageIds.get(0));
        }
        return Optional.empty();
    }

    @Override
    public List<String> uploadImages(List<FileDto> fileDtos) {
        if (fileDtos != null && !fileDtos.isEmpty()) {
            return fileDtos.stream()
                    .filter(Objects::nonNull)
                    .map(this::uploadFileByFileDto)
                    .filter(fileDto -> StringUtils.isNotBlank(fileDto.getImageId()))
                    .map(this::image2DbByFileDto)
                    .collect(toList());
        }
        return Collections.emptyList();
    }

    /**
     * 批量删除
     *
     * @param fileIdArr
     */
    @Override
    public void deleteAll(String... fileIdArr) {
        Arrays.stream(fileIdArr).forEach(this::deleteOne);
    }

    /**
     * 删除数据库的图片信息和磁盘信息
     *
     * @param fileId
     */
    @Override
    public void deleteOne(String fileId) {
        ImageEntity image = imageMapper.selectById(fileId);
        Path diskPath = filePathByImageIdAndName(imageConfigProperty.getStorePath(), image.getId(), image.getName());
        if (Files.exists(diskPath)) {
            boolean delete = false;
            try {
                Files.delete(diskPath);
                delete = true;
            } catch (IOException e) {
                log.error("删除文件{}失败", diskPath);
            }
            if (delete) imageMapper.deleteById(fileId);
        } else {
            log.warn("文件不存在或已删除");
        }
    }

    /**
     * fileDto 2 imageEntity 返回表id
     *
     * @param fileDto MulitpartFile
     * @return
     */
    @Override
    public String image2DbByFileDto(FileDto fileDto) {
        // ru db
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setId(fileDto.getImageId());
        imageEntity.setCreateTime(LocalDateTime.now());
        imageEntity.setName(fileDto.getOriginalFilename());
        imageEntity.setType(ImageType.JPG);
        File file = fileDto.getFile();
        if (file != null) {
            int[] sizeInfo = ImageUtils.getSizeInfo(file);
            imageEntity.setWidth(sizeInfo[0]);
            imageEntity.setHeight(sizeInfo[1]);
            imageEntity.setFilePath(file.getAbsolutePath());
        }
        imageEntity.setDescription(fileDto.getDescription());
        imageEntity.setSize(fileDto.getSize());
        int insert = imageMapper.insert(imageEntity);
        log.info("上传文件id{}名称{}结果为{}", fileDto.getImageId(), fileDto.getOriginalFilename(), insert == 1);
        return fileDto.getImageId();
    }

    /**
     * 文件上传到磁盘中
     *
     * @param fileDto
     * @return
     */
    @Override
    public FileDto uploadFileByFileDto(FileDto fileDto) {
        String imageId = UuidUtils.uuid32();
        try (InputStream inputStream = fileDto.getInputStream()) {
            Path imagePath = Paths.get(imageConfigProperty.getStorePath());
            if (Files.notExists(imagePath)) Files.createDirectories(imagePath);
            String newImagePath = imageConfigProperty.getStorePath() + File.separator + imageId + fileDto.getOriginalFilename();
            Path path = Paths.get(newImagePath);
            Files.copy(inputStream, path);
            fileDto.setFile(path.toFile());
        } catch (IOException e) {
            log.error("文件上传失败，e=>{}", e.toString());
            imageId = null;
        }
        if (imageId != null) {
            fileDto.setImageId(imageId);
            log.info("文件{}上传成功", imageId);
        }
        return fileDto;
    }

    @Override
    public BeanFactory<ImageEntity> beanFactory() {
        return ImageEntity::new;
    }
}
