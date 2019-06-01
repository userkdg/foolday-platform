package com.foolday.service.common;

import com.foolday.dao.message.MessageEntity;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 订单消息的消费者
 */
@Slf4j
@Service
public class OrderMessageCustomer implements MessageListener {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private WxMpService wxMpService;

    @Override
    public void onMessage(Message message, byte[] bytes) {
        log.info("接收到订单类消息{}", message.getBody());
        Object deserialize = redisTemplate.getValueSerializer().deserialize(message.getBody());
        if (deserialize instanceof MessageEntity) {
            try {
                MessageEntity messageEntity = (MessageEntity) deserialize;
                switch (messageEntity.getAction()) {
                    case 下单:
                        handlerSubmitOrderMsg(messageEntity);
                        break;
                    case 付款:
                        handlerPayOrderMsg(messageEntity);
                        break;
                    case 取消订单:
                        handlerCancelOrderMsg(messageEntity);
                        break;
                    case 回复订单:
                        handlerReplayOrderMsg(messageEntity);
                        break;
                    case 申请退款:
                        handlerRefundOrderMsg(messageEntity);
                        break;
                    case 评论订单:
                        handlerCommentOrderMsg(messageEntity);
                        break;
                    case 审核订单:
                        handlerAuditOrderMsg(messageEntity);
                        break;
                    case 确认接单:
                        handlerConfirmOrderMsg(messageEntity);
                        break;
                    default:
                        break;
                }
            } catch (WxErrorException e) {
                e.printStackTrace();
            }
        }
    }

    private void handlerConfirmOrderMsg(MessageEntity messageEntity) throws WxErrorException {
        sendMessage(messageEntity);
    }

    private void handlerAuditOrderMsg(MessageEntity messageEntity) throws WxErrorException {
        sendMessage(messageEntity);
    }

    private void handlerCommentOrderMsg(MessageEntity messageEntity) throws WxErrorException {
        sendMessage(messageEntity);
    }

    private void handlerRefundOrderMsg(MessageEntity messageEntity) throws WxErrorException {
        sendMessage(messageEntity);
    }

    private void handlerReplayOrderMsg(MessageEntity messageEntity) throws WxErrorException {
        sendMessage(messageEntity);
    }

    private void handlerCancelOrderMsg(MessageEntity messageEntity) throws WxErrorException {
        sendMessage(messageEntity);
    }

    private void handlerPayOrderMsg(MessageEntity messageEntity) throws WxErrorException {
        sendMessage(messageEntity);
    }

    // 模板消息字体颜色
    private static final String TEMPLATE_FRONT_COLOR = "#32CD32";

    private void handlerSubmitOrderMsg(MessageEntity messageEntity) throws WxErrorException {
        log.info("处理下单事件{}", messageEntity);
        sendMessage(messageEntity);
    }

    private void sendMessage(MessageEntity messageEntity) throws WxErrorException {
        WxMpTemplateMessage orderMessageTemplate = WxMpTemplateMessage.builder().build();
        orderMessageTemplate.setToUser(messageEntity.getSender());//openid
        // 在公共平台定义的模板的id
        orderMessageTemplate.setTemplateId("ENp7UwpOtlhvieebUvDm0mK4n0hTvbH0Me83HdBUvC0");
        orderMessageTemplate.setUrl(messageEntity.getCallbackUrl());// 回掉会有返回 传递的参数信息
        WxMpTemplateData firstData = new WxMpTemplateData("first", messageEntity.getContent(), TEMPLATE_FRONT_COLOR);
        WxMpTemplateData remarkData = new WxMpTemplateData("Remark", messageEntity.getRemark(), TEMPLATE_FRONT_COLOR);
        orderMessageTemplate.addData(firstData).addData(remarkData);
        log.info("开始发送消息");
        wxMpService.getTemplateMsgService().sendTemplateMsg(orderMessageTemplate);
    }
}