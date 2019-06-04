package com.foolday.service.api.common;

import com.foolday.common.base.BaseServiceApi;
import com.foolday.dao.message.MessageEntity;

public interface MessageServiceApi extends BaseServiceApi<MessageEntity> {
    void publish(MessageEntity messageEntity);

    boolean readMessage(String messageId);
}
