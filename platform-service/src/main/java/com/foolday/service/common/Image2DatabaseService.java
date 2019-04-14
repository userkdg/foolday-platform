//package com.foolday.service.common;
//
//import com.foolday.common.base.BaseServiceUtils;
//import com.foolday.common.enums.ImageType;
//import com.foolday.common.enums.OriginType;
//import com.foolday.common.exception.PlatformException;
//import com.foolday.common.util.ImageUtils;
//import com.foolday.common.util.PlatformAssert;
//import com.foolday.dao.image.ImageEntity;
//import com.foolday.dao.image.ImageMapper;
//import com.foolday.service.api.base.Image2DatabaseServiceApi;
//import com.foolday.service.api.base.Image2DiskServiceApi;
//import com.foolday.serviceweb.dto.image.FileDto;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.Resource;
//import java.io.File;
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//
///**
// *
// */
//@Slf4j
//@Service
//@Transactional
//@Deprecated
//public class Image2DatabaseService implements Image2DatabaseServiceApi {
//
//    @Resource
//    private ImageMapper imageMapper;
//
//    /**
//     * 上传图片
//     *
//     * @param file
//     * @return
//     */
//    @Override
//    public Optional<String> upload(File file) {
//        PlatformAssert.isTrue(ImageUtils.isImage(file), "图片" + file.getName() + "的格式不支持");
//        ImageEntity imageEntity = new ImageEntity();
////        imageEntity.setImgData(ImageUtils.getOptimizeBase64Bytes(file, 0.90F));
////        imageEntity.setImgMinData(ImageUtils.getOptimizeBase64Bytes(file, 0.75F));
////        imageEntity.setFileType(ImageType.JPG);
//        imageEntity.setDescription("测试");
//        imageEntity.setOrigin(OriginType.其他);
//        imageEntity.setSize(file.length());
//        imageEntity.setCreateTime(LocalDateTime.now());
//        int insert = imageMapper.insert(imageEntity);
//        log.info("上传图片Id{},名称{}结果为{}", imageEntity.getId(), file.getName(), insert == 1);
//        System.out.println(imageEntity);
//        return Optional.ofNullable(imageEntity.getId());
//    }
//
//    @Override
//    public File download(String imageId) {
//        ImageEntity imageEntity = BaseServiceUtils.checkOneById(imageMapper, imageId);
//        return ImageUtils.base642ImageFile(imageEntity.getImgData()).orElse(null);
//    }
//
//    @Override
//    public ImageEntity get(String imageId) {
//        return BaseServiceUtils.checkOneById(imageMapper, imageId);
//    }
//
//    /**
//     * 获取图片和类型
//     *
//     * @param imageId
//     * @param width
//     * @param height
//     * @return
//     */
//    @Override
//    public Map<File, ImageType> getThumbnail(String imageId, int width, int height) {
//        ImageEntity imageEntity = BaseServiceUtils.checkOneById(imageMapper, imageId);
//        File file = ImageUtils.base642ImageFile(imageEntity.getImgMinData())
//                .orElseThrow(() -> new PlatformException("无法转为图片"));
//        return new HashMap<File, ImageType>() {{
//            put(file, imageEntity.getFileType());
//        }};
//    }
//
//    @Override
//    public Optional<String> uploadImages(List<FileDto> fileDtos) {
//        return Optional.empty();
//    }
//
//}
