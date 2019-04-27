package com.foolday.service;

import com.foolday.common.enums.ChannelType;
import com.foolday.common.enums.MessageAction;
import com.foolday.dao.message.MessageEntity;
import com.foolday.service.api.common.MessageServiceApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * service 层服务测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"application.yml"}, classes = PlatformServiceApplication.class)
public class MessageRunnerTests {

    @Resource
    private MessageServiceApi redisMessageService;

    @Test
    public void message() {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setBusinessId("businessId");
        messageEntity.setChannelType(ChannelType.订单类);
        messageEntity.setAction(MessageAction.下单);
        messageEntity.setContent("xxxxx");
        messageEntity.setToShopId("shopId");
        messageEntity.setSender("ima");
        redisMessageService.publish(messageEntity);
    }
}
