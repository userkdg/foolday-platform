package com.foolday.dao.carouse;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.TableName;
import com.foolday.common.base.BaseEntity;
import com.foolday.common.enums.CommentStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 存储轮播的图片和店铺
 * 一店铺对多张图片
 *
 * @author userkdg
 */
@TableName("t_carouse")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CarouseEntity extends BaseEntity<CarouseEntity> {
    /**
     *  排序 小到大
     */
    private Integer orderNo;

    private String shopId;

    private String imageId;

    @EnumValue
    private CommentStatus status;
}
