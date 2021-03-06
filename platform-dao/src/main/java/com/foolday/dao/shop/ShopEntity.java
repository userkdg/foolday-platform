package com.foolday.dao.shop;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.TableName;
import com.foolday.common.base.BaseEntity;
import com.foolday.common.enums.ShopStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@TableName("t_shop")
@Data
@EqualsAndHashCode(callSuper = true)
public class ShopEntity extends BaseEntity<ShopEntity> {

    @NotNull(message = "店铺名称不能为空")
    private String name;

    private String addr;

    private String contact;

    private String description;

    private Float lnt;

    private Float lat;

    @EnumValue
    private ShopStatus status;

    /**
     * 默认店铺
     */
    private Boolean default_shop;
}
