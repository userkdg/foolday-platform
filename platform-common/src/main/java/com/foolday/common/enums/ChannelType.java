package com.foolday.common.enums;

import com.foolday.common.base.BaseEnum;
import com.foolday.common.constant.WebConstant.RedisKey;

/**
 * 管道用来管理redis的消息发布道那个管道上
 */
public enum ChannelType implements BaseEnum {
    订单类(RedisKey.REDIS_ORDER_MESSAGE_CHANNEL_KEY),
    评论类(RedisKey.REDIS_COMMENT_MESSAGE_CHANNEL_KEY);

    private String value;

    ChannelType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
