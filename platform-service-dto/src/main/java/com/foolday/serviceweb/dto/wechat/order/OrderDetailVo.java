package com.foolday.serviceweb.dto.wechat.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel("订单详情对象，接收新增订单详情")
@Data
public class OrderDetailVo implements Serializable {

    /*
    商品名称
     */
    @ApiModelProperty(value = "订单的商品id", required = true)
    @NotNull(message = "订单的商品id 必填")
    private String goodsId;
    /*
    下单的商品数量
     */
    @ApiModelProperty(value = "订单的商品数量", required = true)
    @NotNull(message = "订单的商品数量 必填")
    @Min(value = 1, message = "下单数量最少1")
    private Integer cnt;
}
