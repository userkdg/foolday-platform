package com.foolday.dao.order;

import com.baomidou.mybatisplus.annotation.TableName;
import com.foolday.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 *
 */
@TableName("t_order_detail")
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderDetailEntity extends BaseEntity<OrderDetailEntity> {
    private String goodsName;
    private String orderId;
    private String goodsDesc;
    private String goodsImgId;
    private String goodsId;
    private Integer cnt;
    private Float price;
    private Float allPrice;
}
