package com.foolday.dao.shop;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.TableName;
import com.foolday.common.enums.ShopStatus;
import com.foolday.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.Date;

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

    private Date createtime;

    private Date updatetime;

}
