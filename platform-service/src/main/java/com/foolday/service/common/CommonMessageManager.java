package com.foolday.service.common;

import com.foolday.common.enums.ChannelType;
import com.foolday.common.enums.MessageAction;
import com.foolday.dao.message.MessageEntity;
import com.foolday.service.api.common.MessageServiceApi;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
public class CommonMessageManager {

    /**
     * 订单消息内部类
     */
    public static class OrderMsgHandler {

        /**
         * @param wxId
         * @param shopId
         * @param orderId
         */
        public static void notifyShopMsgFormUser(String wxId, String shopId,
                                                 String orderId, String title, String content,
                                                 MessageAction messageAction, ChannelType channelType) {
            MessageEntity messagePo = new MessageEntity();
            messagePo.setSender(wxId);
            messagePo.setToShopId(shopId);
            messagePo.setTitle(title);
            messagePo.setContent(content);
            messagePo.setBusinessId(orderId);
            messagePo.setAction(messageAction);
            messagePo.setCreateTime(LocalDateTime.now());
            messagePo.setChannelType(channelType);
            // 入库db
            boolean insert = messagePo.insert();
            log.info("入库数据{},结果{}", messagePo, insert);
            SpringContextUtils.getBean(MessageServiceApi.class).publish(messagePo);
        }
    }

    /**
     * 评论类
     */
    public static class CommentMsgHandler{

    }


}
