package com.foolday.service.api.admin;

import com.foolday.common.dto.FantPage;
import com.foolday.common.enums.OrderStatus;
import com.foolday.dao.order.OrderEntity;
import com.foolday.serviceweb.dto.admin.OrderQueryVo;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface AdminOrderServiceApi {

    void updateOrderStatus(@NotNull String orderId, OrderStatus status);

    FantPage<List<OrderEntity>> page(OrderQueryVo queryVo);
}
