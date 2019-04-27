package com.foolday.dao.message;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.TableName;
import com.foolday.common.enums.ChannelType;
import com.foolday.common.enums.MessageAction;
import com.foolday.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * 平台的消息持久化实体
 */
@TableName("t_message")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MessageEntity extends BaseEntity<MessageEntity> implements Serializable {
    /**
     * 发起人
     */
    private String sender;

    /**
     * string -> value-> varchar
     */
    @EnumValue
    private ChannelType channelType;

    private String businessId;

    private String content;

    /**
     * 店铺id=>获取对应的订阅者，提示给他
     */
    private String toShopId;

    /**
     * 区分类型=>订单类 对应的businessId 是orderId 而评论类就是commentId
     */
    @EnumValue
    private MessageAction action;

}
