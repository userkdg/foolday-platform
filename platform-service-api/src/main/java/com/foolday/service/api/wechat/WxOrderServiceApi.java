package com.foolday.service.api.wechat;

import com.foolday.dao.order.OrderEntity;
import com.foolday.serviceweb.dto.wechat.order.WxOrderVo;

public interface WxOrderServiceApi {
    OrderEntity submitOrder(WxOrderVo orderVo);

    void toPay(String userId, String orderId);
}
