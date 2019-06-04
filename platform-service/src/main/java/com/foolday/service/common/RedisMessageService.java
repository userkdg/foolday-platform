package com.foolday.service.common;

import com.foolday.common.base.BaseServiceUtils;
import com.foolday.common.base.BeanFactory;
import com.foolday.dao.message.MessageEntity;
import com.foolday.dao.message.MessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 基于redis的消息 发布与订阅
 */
@Slf4j
@Transactional
@Service
public class RedisMessageService extends AbstractMessageService {
    @Resource
    private MessageMapper messageMapper;

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public void publish(MessageEntity messageEntity) {
        int insert = messageMapper.insert(messageEntity);
        log.info("入库数据{},结果{}", messageEntity, insert);
        redisTemplate.convertAndSend(messageEntity.getChannelType().getValue(), messageEntity);
    }

    /**
     * 更新消息为已读
     * @param messageId
     * @return
     */
    @Override
    public boolean readMessage(String messageId) {
        MessageEntity messageEntity = BaseServiceUtils.checkOneById(messageMapper, messageId, "查看消息已删除，请刷新");
        messageEntity.setUnread(Boolean.TRUE);
        return messageEntity.updateById();
    }


    @Override
    public BeanFactory<MessageEntity> beanFactory() {
        return MessageEntity::new;
    }
}
