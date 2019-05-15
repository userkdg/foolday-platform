package com.foolday.service.api.admin;

import com.foolday.common.base.BaseServiceApi;
import com.foolday.dao.order.OrderDetailEntity;
import com.foolday.serviceweb.dto.wechat.order.OrderDetailVo;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface OrderDetailServiceApi extends BaseServiceApi<OrderDetailEntity> {

    /**
     * 新增订单详情返回有id的实体
     */
    OrderDetailEntity add(OrderDetailVo orderDetailVo, @NotNull String orderId);

    /**
     * 通过订单id获取详情
     *
     * @param orderId
     * @return
     */
    List<OrderDetailEntity> findByOrderId(@NotNull String orderId);
}
