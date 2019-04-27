package com.foolday.service.api.common;

import com.foolday.dao.message.MessageEntity;

public interface MessageServiceApi {
    void publish(MessageEntity messageEntity);
}
