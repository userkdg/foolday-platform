package com.foolday.dao.image;

import com.baomidou.mybatisplus.annotation.TableName;
import com.foolday.common.enums.ImageType;
import com.foolday.common.enums.OriginType;
import com.foolday.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.sql.Blob;

/**
 * 存储图片
 */
@TableName("t_image")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ImageEntity extends BaseEntity<ImageEntity> {

    /*
    原图
     */
    private byte[] imgData;

    /**
     * 缩略图
     */
    private Blob imgMinData;

    /**
     * 文件类型
     */
    private ImageType fileType;

    /**
     * 来源
     */
    private OriginType origin;

    /*
    图片描述信息
     */
    private String description;

    /**
     * 备注
     */
    private String remark;

    /*
    为 byte 单位
     */
    private Long size;
}
