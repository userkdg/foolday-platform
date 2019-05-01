package com.foolday.serviceweb.dto.admin.comment;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.foolday.common.enums.CommentStatus;
import com.foolday.common.enums.CommentType;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 订单评论
 * 目前设计为基于订单的评论区，若订单多个商品 则每个商品生成各自的一条记录
 */
@Data
@ToString(callSuper = true)
public class CommentVo implements Serializable {

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
    private CommentStatus status;

    /**
     * 客户名称 字段冗余，偏于提供显示 前端需要限制不显示全名
     */
    private String userName;
    /*
    评论分类 内部人和客户
     */
    @EnumValue
    private CommentType commentType;

}
