package com.foolday.service.api.wechat;

import com.foolday.dao.order.OrderEntity;
import com.foolday.serviceweb.dto.wechat.order.WxOrderVo;

import java.util.List;

public interface WxOrderServiceApi {
    OrderEntity submitOrder(WxOrderVo orderVo, String shopId);

    void toPay(String userId, String orderId);

    List<OrderEntity> listByOpenId(String userId);

    List<OrderEntity> listByUserId(String userId);

    OrderEntity get(String orderId, String userId);

    boolean cancelOrder(String orderId, String userId);

    boolean refund(String orderId, String userId);
}
