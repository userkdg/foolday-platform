package com.foolday.service.api.admin;

import com.foolday.common.base.BaseServiceApi;
import com.foolday.common.base.BeanFactory;
import com.foolday.common.dto.FantPage;
import com.foolday.common.enums.OrderStatus;
import com.foolday.dao.order.OrderEntity;
import com.foolday.serviceweb.dto.admin.OrderQueryVo;
import com.foolday.serviceweb.dto.admin.base.LoginUser;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface OrderServiceApi extends BaseServiceApi<OrderEntity> {

    void updateOrderStatus(@NotNull String orderId, OrderStatus status);

    FantPage<OrderEntity> page(OrderQueryVo queryVo);

    void delete(String orderId);

    List<OrderEntity> findCancelOrders(LoginUser user);

    List<OrderEntity> findByOrderStatus(OrderStatus orderStatus, LoginUser user);

    void auditOrder(String orderId, boolean success);

    /**
     * 实例化实体类的工厂
     *
     * @return
     */
    @Override
    default BeanFactory<OrderEntity> beanFactory() {
        return OrderEntity::new;
    }
}
