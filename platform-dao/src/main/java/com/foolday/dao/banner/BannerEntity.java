package com.foolday.dao.banner;

import com.baomidou.mybatisplus.annotation.TableName;
import com.foolday.common.base.BaseEntity;
import com.foolday.common.enums.CommentStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


/**
 * banner entity
 * @author Administrator
 */
@TableName("t_banner")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BannerEntity extends BaseEntity<BannerEntity> {

    /**
     * 排序
     */
    private Integer orderNo;

    /**
     * 状态
     */
    private CommentStatus status;

    private String goodsId;

    /**
     * 显示图片
     */
    private String imageId;

    /**
     * 描述内容
     */
    private String description;

    /**
     * 商品价格
     */
    private Float price;

    private String shopId;

}
