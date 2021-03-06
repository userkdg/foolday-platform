package com.foolday.service.api.wechat;

import com.foolday.common.base.BaseServiceApi;
import com.foolday.common.base.BeanFactory;
import com.foolday.common.enums.OrderStatus;
import com.foolday.dao.order.OrderDetailEntity;
import com.foolday.dao.order.OrderEntity;
import com.foolday.serviceweb.dto.admin.comment.CommentVo;
import com.foolday.serviceweb.dto.wechat.order.EntInvoiceVo;
import com.foolday.serviceweb.dto.wechat.order.OrderDetailVo;
import com.foolday.serviceweb.dto.wechat.order.WxOrderVo;

import java.util.List;

public interface WxOrderServiceApi extends BaseServiceApi<OrderEntity> {
    /**
     * 实例化实体类的工厂
     *
     * @return
     */
    @Override
    default BeanFactory<OrderEntity> beanFactory() {
        return OrderEntity::new;
    }

    OrderEntity submitOrder(WxOrderVo orderVo, String userId,String userName, String shopId);

    void pay(String openId, String outTradeNo, String transactionId);

    List<OrderEntity> listByOpenId(String userId);

    List<OrderEntity> listByUserId(String userId, OrderStatus orderStatus);

    OrderEntity get(String orderId, String userId);

    boolean cancelOrder(String orderId, String userId);

    boolean refund(String orderId, String userId);

    boolean updateOrderStatusByIdAndUserId(String orderId, String userId, OrderStatus orderStatus);

    OrderDetailEntity appendOrderDetail(OrderDetailVo orderDetailvo, String orderId);

    void newBill(String orderId, EntInvoiceVo invoiceVo);

    void addComment(String orderId, CommentVo commentVo, String userId, String shopId, String name);
}
