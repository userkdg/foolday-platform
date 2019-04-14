package com.foolday.dao.image;

import com.baomidou.mybatisplus.annotation.TableName;
import com.foolday.common.enums.ImageType;
import com.foolday.common.enums.OriginType;
import com.foolday.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 存储图片
 */
@TableName("t_image")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ImageEntity extends BaseEntity<ImageEntity> {
    /*
    文件名称（有后缀）通过id+name 来获取磁盘中对应的文件
     */
    private String name;

    /*
    图片大小
     */
    private Integer width;

    private Integer height;
    /**
     * 文件类型
     * MediaType.valueOf(firmFile.getContentType()
     *
     */
    private ImageType type;

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
