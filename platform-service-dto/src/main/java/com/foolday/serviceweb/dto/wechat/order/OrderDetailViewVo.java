package com.foolday.serviceweb.dto.wechat.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *
 *
 */
@ApiModel("订单列表信息")
@Data
@ToString(callSuper = true)
public class OrderDetailViewVo implements Serializable {
    /*
    商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "订单id为查看订单时必填")
    private String orderId;

    @ApiModelProperty(value = "商品描述")
    private String goodsDesc;

    @ApiModelProperty(value = "商品图片id")
    private String goodsImgId;

    @ApiModelProperty(value = "商品id必填", required = true)
    @NotNull(message = "商品id必填")
    private String goodsId;
    /*
    下单的商品数量
     */
    @ApiModelProperty(value = "下单数量", required = true, dataType = "int")
    @NotNull(message = "下单数量必填")
    @Min(value = 1, message = "数量最少为1")
    private Integer cnt;
    /*
    商品单价
     */
    @ApiModelProperty(value = "商品单价", dataType = "float")
    private Float price;
    /*
    cnt * price
     */
    @ApiModelProperty(value = "商品总价", dataType = "float")
    private Float allPrice;
}
