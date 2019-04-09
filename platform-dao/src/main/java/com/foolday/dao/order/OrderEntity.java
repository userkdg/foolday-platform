package com.foolday.dao.order;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.TableName;
import com.foolday.core.base.BaseEntity;
import com.foolday.core.enums.EatType;
import com.foolday.core.enums.OrderStatus;
import com.foolday.core.enums.OrderType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * id varchar(64) not null PRIMARY key,
 * shop_id varchar(64) not null comment '店铺id',
 * shop_name varchar(100) not null comment '店铺名称，冗余字段，便于显示',
 * shop_address varchar(255) not null comment '店铺地址，冗余字段，便于显示',
 * eatType TINYINT default 1 comment '1 堂吃 0外带',
 * goods_num TINYINT(4) default 0 comment '订单商品数量',
 * all_price float default 0.00 comment '订单总价格',
 * discnt_price float default 0.00 comment '折扣价格 减多少或打几折后减多少',
 * other_discnt_price float default 0.00 comment '其他优惠价',
 * real_pay_price float default 0.00 comment '实付价格',
 * remark varchar(100) default '' comment '订单备注',
 * people_cnt TINYINT(4) default 1 comment '默认为1人用餐',
 * user_id varchar(36) not null comment '微信小程序用户系统id,非wxid',
 * groupbuy_id varchar(36) default '' comment '拼团id',
 * coupon_id varchar(36) default '' comment '使用优惠券的id,控制优惠券的状态',
 * status TINYINT(2) default 0 comment '普通订单类型:0待付款,1待确认,2待评价,3已完成(已评价);通用:4退款,-1删除,拼团类型:10拼团中,11拼团成功,12拼团失败',
 * order_no varchar(50) default '' comment '订单编号',
 * order_type TINYINT(2) default 0 comment '0点餐订单 1拼团订单',
 * seat_no int(10) default 0 comment '座位号，目前只录入数值 不计号,为了按数值排序',
 * queue_no varchar(10) default '' comment '排队号',
 * create_time datetime not null,
 * update_time datetime comment '状态更新时间'
 */
@TableName("t_order")
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderEntity extends BaseEntity<OrderEntity> {
    private String shopId;
    private String shopName;
    private String shopAddress;
    @EnumValue
    private EatType eatType;
    private Integer goodsNum;
    private Float allPrice;
    private Float discntPrice;
    private Float otherDiscntPrice;
    private Float realPayPrice;
    private String remark;
    private Integer peopleCnt;
    private String userId;
    private String groupbuyId;
    private String couponId;
    @EnumValue
    private OrderStatus status;
    private String orderNo;
    @EnumValue
    private OrderType orderType;
    private Integer seatNo;
    private String queueNo;
}
