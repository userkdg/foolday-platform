package com.foolday.service.api.admin;

import com.foolday.common.dto.FantPage;
import com.foolday.common.enums.OrderStatus;
import com.foolday.dao.order.OrderEntity;
import com.foolday.serviceweb.dto.admin.OrderQueryVo;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface OrderServiceApi {

    void updateOrderStatus(@NotNull String orderId, OrderStatus status);

    FantPage<OrderEntity> page(OrderQueryVo queryVo);

    void delete(String orderId);

    List<OrderEntity> findCancelOrders();

    List<OrderEntity> findByOrderStatus(OrderStatus orderStatus);

    void auditOrder(String orderId, boolean success);
}
