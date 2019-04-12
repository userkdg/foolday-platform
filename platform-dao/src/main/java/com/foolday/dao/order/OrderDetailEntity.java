package com.foolday.dao.order;

import com.baomidou.mybatisplus.annotation.TableName;
import com.foolday.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *
 *
 */
@TableName("t_order_detail")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrderDetailEntity extends BaseEntity<OrderDetailEntity> {
    /*
    商品名称
     */
    private String goodsName;
    private String orderId;
    private String goodsDesc;
    private String goodsImgId;
    private String goodsId;
    /*
    下单的商品数量
     */
    private Integer cnt;
    /*
    商品单价
     */
    private Float price;
    /*
    cnt * price
     */
    private Float allPrice;
}
