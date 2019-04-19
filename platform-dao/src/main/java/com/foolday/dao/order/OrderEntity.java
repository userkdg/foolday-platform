package com.foolday.dao.order;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.TableName;
import com.foolday.common.enums.EatType;
import com.foolday.common.enums.OrderStatus;
import com.foolday.common.enums.OrderType;
import com.foolday.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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
 * other_coupon_id varchar(36) default '' comment '其他优惠价对应的优惠标识',
 * status TINYINT(2) default 0 comment '普通订单类型:0待付款,1待确认,2待评价,3已完成(已评价);通用:4退款,-1删除,拼团类型:10拼团中,11拼团成功,12拼团失败',
 * order_no varchar(50) default '' comment '订单编号',
 * order_type TINYINT(2) default 0 comment '0点餐订单 1拼团订单',
 * seat_no int(10) default 0 comment '座位号，目前只录入数值 不计号,为了按数值排序',
 * queue_no varchar(10) default '' comment '排队号',
 * create_time datetime not null, 目前下单时间以创建时间为准 目前下单时间以创建时间为准!!
 * update_time datetime comment '状态更新时间'
 */
@TableName("t_order")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrderEntity extends BaseEntity<OrderEntity> {
    private String shopId;
    /*
    店名 和 店地址 字段冗余，便于前端显示
     */
    private String shopName;
    private String shopAddress;
    /*
    就餐类型
     */
    @EnumValue
    private EatType eatType;
    /*
    单独商品数量
     */
    private Integer goodsNum;
    /*
    总价格
     */
    private Float allPrice;
    /*
    折扣价（有优惠券等产生的价格）
     */
    private Float discntPrice;
    /*
    其他优惠价
     */
    private Float otherDiscntPrice;
    /*
    实际价格= allPrice - discntPrice - otherDiscntPrice
     */
    private Float realPayPrice;
    private String remark;
    /*
    订单人数 餐具提供支持
     */
    private Integer peopleCnt;
    /*
    客户id
     */
    private String userId;
    /*
    团购id 默认为'' 若有值为团购/拼团产生的订单
     */
    private String groupbuyId;
    /*
    使用优惠券 验证用户卷的有效性！
     */
    private String couponId;
    /*
    其他优惠价的标识（认证有效性）
     */
    private String otherCouponId;
    /*
    订单状态
     */
    @EnumValue
    private OrderStatus status;
    /**
     * 订单编号 规则生成
     */
    private String orderNo;
    /*
    订单类型
     */
    @EnumValue
    private OrderType orderType;
    /*
    订单座位号
     */
    private Integer seatNo;
    /*
    排队号
     */
    private String queueNo;

    /*
    目前下单时间以创建时间为准
     */
}
