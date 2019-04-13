package com.foolday.service.common;

import com.foolday.common.enums.ImageType;
import com.foolday.common.enums.OriginType;
import com.foolday.dao.image.ImageEntity;
import com.foolday.dao.image.ImageMapper;
import com.foolday.service.api.base.ImageServiceApi;
import com.google.common.io.Files;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class ImageService implements ImageServiceApi {
    @Resource
    private ImageMapper imageMapper;

    @Override
    public Optional<String> upload(File file) {
        try {
            final byte[] bytes = Files.toByteArray(file);
            byte[] base64Image = Base64.getEncoder().encode(bytes);
            ImageEntity imageEntity = new ImageEntity();
            imageEntity.setImgData(base64Image);
            imageEntity.setFileType(ImageType.JPG);
            imageEntity.setDescription("测试");
            imageEntity.setOrigin(OriginType.其他);
            imageEntity.setSize(file.length());
            imageEntity.setCreateTime(LocalDateTime.now());
            int insert = imageMapper.insert(imageEntity);
            System.out.println(imageEntity);
            return Optional.ofNullable(imageEntity.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
