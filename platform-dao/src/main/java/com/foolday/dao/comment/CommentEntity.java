package com.foolday.dao.comment;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.TableName;
import com.foolday.common.enums.CommentStatus;
import com.foolday.common.enums.CommentType;
import com.foolday.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 订单评论
 * 目前设计为基于订单的评论区，若订单多个商品 则每个商品生成各自的一条记录
 */
@TableName("t_comment")
@Data
@EqualsAndHashCode(callSuper = true)
public class CommentEntity extends BaseEntity<CommentEntity> {
    private String shopId;
    /*
    字段冗余
     */
    private String shopName;
    /**
     * 几星 0<= x <= 5
     */
    private Integer star;
    /**
     * 图片array
     */
    private String imgIds;
    /*
    不用desc 其为sql 关键字
     */
    private String description;
    /*
    订单id
     */
    private String orderId;
    /*
    商品id
     */
    private String goodsId;
    /*
    评论状态
     */
    @EnumValue
    private CommentStatus status;
    /*
    客户id
     */
    private String userId;
    /**
     * 客户名称 字段冗余，偏于提供显示 前端需要限制不显示全名
     */
    private String userName;
    /*
    评论分类 内部人和客户
     */
    @EnumValue
    private CommentType commentType;

    /*
    记录内部人谁评论的
     */
    private String adminId;
    /*
    admin nickname
     */
    private String adminName;
}
