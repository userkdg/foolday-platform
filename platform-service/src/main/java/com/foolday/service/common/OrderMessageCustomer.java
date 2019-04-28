package com.foolday.service.common;

import com.foolday.common.base.RedisBeanNameApi;
import com.foolday.dao.message.MessageEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;


/**
 * 订单消息的消费者
 */
@Slf4j
@Service
public class OrderMessageCustomer implements MessageListener {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Resource(name = RedisBeanNameApi.REDIS_TEMPLATE_S_O)
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onMessage(Message message, byte[] bytes) {
        log.info("接收到订单类消息{}", message);
        Object deserialize = redisTemplate.getValueSerializer().deserialize(message.getBody());
        if (deserialize instanceof MessageEntity) {
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
                default:
                    break;
            }
        }
    }

    private void handlerCommentOrderMsg(MessageEntity messageEntity) {

    }

    private void handlerRefundOrderMsg(MessageEntity messageEntity) {

    }

    private void handlerReplayOrderMsg(Serializable messageEntity) {

    }

    private void handlerCancelOrderMsg(MessageEntity messageEntity) {

    }

    private void handlerPayOrderMsg(MessageEntity messageEntity) {

    }

    private void handlerSubmitOrderMsg(MessageEntity messageEntity) {
        log.info("处理下单事件{}", messageEntity);
    }
}