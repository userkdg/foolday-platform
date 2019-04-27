package com.foolday.service.common;

import com.foolday.common.base.RedisBeanNameApi;
import lombok.extern.slf4j.Slf4j;
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
public class CommentMessageCustomer implements MessageListener {
    @Resource(name = RedisBeanNameApi.REDIS_TEMPLATE_S_S)
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void onMessage(Message message, byte[] bytes) {
        String deserialize = redisTemplate.getStringSerializer().deserialize(message.getBody());
        System.out.println(deserialize);
    }
}
