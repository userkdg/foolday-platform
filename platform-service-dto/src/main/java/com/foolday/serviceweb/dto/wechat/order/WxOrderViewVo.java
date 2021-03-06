package com.foolday.serviceweb.dto.wechat.order;

import com.foolday.common.enums.EatType;
import com.foolday.common.enums.OrderStatus;
import com.foolday.common.enums.OrderType;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@ApiModel(description = "微信用户下单表单vo")
@Data
public class WxOrderViewVo implements Serializable {

    @ApiModelProperty(value = "订单列表信息", required = true)
    @NotEmpty(message = "订单商品不可为空")
    private List<OrderDetailViewVo> orderDetails = Lists.newArrayList();

    @ApiModelProperty(value = "店铺id必填，为查看订单使用", required = true)
    @NotNull(message = "店铺id必填")
    private String shopId;
    /*
    店名 和 店地址 字段冗余，便于前端显示
     */
    @ApiModelProperty(value = "店铺非必填，为查看订单使用", required = false)
    private String shopName;
    @ApiModelProperty(value = "店铺地址非必填，为查看订单使用", required = false)
    private String shopAddress;
    /*
    就餐类型
     */
    @ApiModelProperty(value = "就餐方式", required = true)
    @NotNull(message = "就餐方式必填")
    private EatType eatType;
    /*
    单独商品数量
     */
    @ApiModelProperty(value = "订单商品总数非必填，后台计算", required = false)
    private Integer goodsNum;
    /*
    总价格
     */
    @ApiModelProperty(value = "订单总价非必填后台计算", required = false)
    private Float allPrice;
    /*
    折扣价（有优惠券等产生的价格）
     */
    @ApiModelProperty(value = "折扣价", dataType = "Float")// todo 待定 本价格的计算再后端，需要传优惠券唯一标识！本字段只为查看订单显示用
    private Float discntPrice;
    /*
    其他优惠价
     */
    @ApiModelProperty(value = "其他优惠价", dataType = "Float")// todo 待定 本价格的计算再后端，传优惠券唯一标识！本字段只为查看订单显示用
    private Float otherDiscntPrice;
    /*
    实际价格= allPrice - discntPrice - otherDiscntPrice
     */
    @ApiModelProperty(value = "实际价格", dataType = "Float")// todo 待定 本价格的计算再后端，需要传优惠券唯一标识！本字段只为查看订单显示用
    private Float realPayPrice;

    @ApiModelProperty(value = "订单备注内容")
    @Max(value = 100L, message = "长度小于100L")
    private String remark;
    /*
    订单人数 餐具提供支持
     */
    @ApiModelProperty(value = "就餐人数", required = true, dataType = "Integer")
    @NotNull(message = "就餐人数必填")
    private Integer peopleCnt;
    /*
    客户id
     */
    @ApiModelProperty(value = "用户id", required = true, dataType = "String")
    @NotNull(message = "用户标识必填")
    private String userId;
    /*
    团购id 默认为'' 若有值为团购/拼团产生的订单
     */
    @ApiModelProperty(value = "团购订单的团购id,非团购为空")
    private String groupbuyId;
    /*
    使用优惠券 验证用户卷的有效性！
     */
    @ApiModelProperty(value = "优惠券标识id")
    private String couponId;

    /*
    订单状态
     */
    @ApiModelProperty(value = "订单状态必填", required = true)
    @NotNull(message = "订单状态必填")
    private OrderStatus status;
    /**
     * 订单编号 规则生成
     */
    @ApiModelProperty(value = "提交订单时提供的订单编号必填", required = true)
    @NotNull(message = "提交订单时提供的订单编号必填")
    private String orderNo;
    /*
    订单类型
     */
    @ApiModelProperty(value = "团购必须有团购id,订单团购id为空", required = true)
    @NotNull(message = "团购必须有团购id,订单团购id为空")
    private OrderType orderType;
    /*
    订单座位号
     */
    @ApiModelProperty(value = "桌位号，扫二维码中获取", required = true, dataType = "Integer")
    @NotNull(message = "桌位号，扫二维码中获取,必填")
    private Integer seatNo;
    /*
    排队号
     */
    @ApiModelProperty(value = "排队号", required = false)
    private String queueNo;
}
