package com.foolday.service.api.admin;

import com.foolday.common.dto.FantPage;
import com.foolday.common.enums.OrderStatus;
import com.foolday.dao.order.OrderEntity;
import com.foolday.serviceweb.dto.admin.OrderQueryVo;

import javax.validation.constraints.NotNull;

public interface AdminOrderServiceApi {

    void updateOrderStatus(@NotNull String orderId, OrderStatus status);

    FantPage<OrderEntity> page(OrderQueryVo queryVo);
}
