package com.foolday.service.wechat;

import com.foolday.common.base.BaseServiceUtils;
import com.foolday.common.base.RedisBeanNameApi;
import com.foolday.common.enums.ChannelType;
import com.foolday.common.enums.MessageAction;
import com.foolday.common.enums.OrderStatus;
import com.foolday.common.enums.OrderType;
import com.foolday.common.exception.PlatformException;
import com.foolday.common.util.KeyUtils;
import com.foolday.common.util.PlatformAssert;
import com.foolday.dao.coupon.CouponEntity;
import com.foolday.dao.goods.GoodsEntity;
import com.foolday.dao.goods.GoodsMapper;
import com.foolday.dao.message.MessageEntity;
import com.foolday.dao.order.OrderEntity;
import com.foolday.dao.order.OrderMapper;
import com.foolday.service.api.admin.CouponServiceApi;
import com.foolday.service.api.admin.OrderDetailServiceApi;
import com.foolday.service.api.common.MessageServiceApi;
import com.foolday.service.api.wechat.WxOrderServiceApi;
import com.foolday.service.api.wechat.WxUserCouponServiceApi;
import com.foolday.service.common.SpringContextUtils;
import com.foolday.serviceweb.dto.admin.base.LoginUserHolder;
import com.foolday.serviceweb.dto.wechat.order.OrderDetailVo;
import com.foolday.serviceweb.dto.wechat.order.WxOrderVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@Transactional
public class WxOrderService implements WxOrderServiceApi {
    @Resource
    private OrderMapper orderMapper;

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private WxAccessTokenService wxAccessTokenService;

    @Resource
    private CouponServiceApi couponServiceApi;

    @Resource
    private WxUserCouponServiceApi userCouponServiceApi;

    @Resource
    private OrderDetailServiceApi orderDetailServiceApi;

    @Resource(name = RedisBeanNameApi.REDIS_TEMPLATE_S_S)
    private RedisTemplate<String, String> redisTemplate;

    /**
     * @param orderVo
     * @return
     */
    @Override
    public OrderEntity submitOrder(WxOrderVo orderVo) {
        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(orderVo, orderEntity);
        orderEntity.setCreateTime(LocalDateTime.now());
        orderEntity.setShopId(LoginUserHolder.get().getShopId());
        final String orderNoOfDay = KeyUtils.generateOrderNoOfDay(redisTemplate);
        orderEntity.setOrderNo(orderNoOfDay);
        String userId = orderVo.getUserId();
        // 判断订单类型
        if (StringUtils.isNotBlank(orderVo.getGroupbuyId())) {
            // 判断团购id是否存在
//            PlatformAssert.isTrue();
            orderEntity.setOrderType(OrderType.拼团订单);
            // 订单与拼团的关联 todo
        }
        // todo 增加规格计算判断价格
        List<OrderDetailVo> orderDetailsVo = orderVo.getOrderDetails();
        // 计算价格
        Float sumPrice = orderDetailsVo.stream().map(orderDetail -> {
            GoodsEntity goodsEntity = BaseServiceUtils.checkOneById(goodsMapper, orderDetail.getGoodsId());
            List<String> goodsSpecIds = orderDetail.getGoodsSpecIds();
            // todo 增加规格计算判断价格
            if (goodsSpecIds.isEmpty()) {
                log.debug("本商品没有选择规格信息");
            } else {
                log.debug("本商品进行规格信息计算");
            }
            Float price = goodsEntity.getRealPrice();
            Integer cnt = orderDetail.getCnt();
            return price * cnt;
        }).reduce((f1, f2) -> f1 + f2).orElseThrow(() -> new PlatformException("商品总价计算异常"));
        orderEntity.setAllPrice(sumPrice);
        // 优惠
        float couponPrice = 0F;
        String couponId = orderVo.getCouponId();
        String otherCouponId = orderVo.getOtherCouponId();
        if (StringUtils.isNotBlank(couponId)) {
            CouponEntity couponEntity = couponServiceApi.get(couponId).orElseThrow(() -> new PlatformException("订单优惠券异常"));
            couponPrice = couponEntity.getTargetPriceBySourcePrice(sumPrice);
            userCouponServiceApi.updateUsedByUserIdAndCouponId(userId, couponId);
        }
        if (StringUtils.isNotBlank(otherCouponId)) {
            CouponEntity couponEntity = couponServiceApi.get(couponId).orElseThrow(() -> new PlatformException("订单优惠券异常"));
            couponPrice = couponEntity.getTargetPriceBySourcePrice(sumPrice);
            userCouponServiceApi.updateUsedByUserIdAndCouponId(userId, otherCouponId);
        }
        orderEntity.setDiscntPrice(couponPrice);
        orderEntity.setRealPayPrice(sumPrice - couponPrice);

        int insert = orderMapper.insert(orderEntity);
        log.info("写入订单{}", (insert == 1));
        // 异步消息发送 todo

        // 写入明细
        orderDetailsVo.forEach(orderDetailVo -> orderDetailServiceApi.add(orderDetailVo, orderEntity.getId()));
        return orderEntity;
    }

    /**
     * 发起支付
     *
     * @param userId
     * @param orderId
     */
    @Override
    public void toPay(String userId, String orderId) {
        OrderEntity orderEntity = BaseServiceUtils.checkOneById(orderMapper, orderId);
        if (StringUtils.equals(orderEntity.getUserId(), userId)) {
            //发起pay
            String accessToken = wxAccessTokenService.returnAccessToken().orElse(wxAccessTokenService.forceRefreshAccessToken());

        } else {
            throw new PlatformException("用户与订单信息关系不符");
        }
    }

    @Override
    public List<OrderEntity> listByOpenId(String userId) {
        return null;
    }

    /**
     * 获取用户的订单记录
     *
     * @param userId
     * @return
     */
    @Override
    public List<OrderEntity> listByUserId(String userId) {
        return null;
    }

    @Override
    public OrderEntity get(String orderId, String userId) {
        return null;
    }

    @Override
    public boolean cancelOrder(String orderId, String userId) {

        return false;
    }

    /**
     * 更新订单状态，通知店铺的后台人员，处理订单
     *
     * @param orderId
     * @param userId
     * @return
     */
    @Override
    public boolean refund(String orderId, String userId) {
        OrderEntity order = BaseServiceUtils.checkOneById(orderMapper, orderId);
        PlatformAssert.isTrue(StringUtils.equals(orderId, order.getUserId()), "非本人订单,结束退款操作");
        // 通知后台人员 处理退款单子,确认退款后进行退费
        OrderMessageHandler.notifyShopMsgFormUser(userId, order.getShopId(), orderId, "xxx发起退款申请", MessageAction.申请退款);
        // 更新状态
        order.setStatus(OrderStatus.申请退款);
        order.setUpdateTime(LocalDateTime.now());
        return order.updateById();
    }


    private static class OrderMessageHandler {

        /**
         * @param userId
         * @param shopId
         * @param orderId
         */
        public static void notifyShopMsgFormUser(String userId, String shopId, String orderId, String content, MessageAction messageAction) {
            MessageEntity messagePo = new MessageEntity();
            messagePo.setSender(userId);
            messagePo.setToShopId(shopId);
            messagePo.setContent(content);
            messagePo.setBusinessId(orderId);
            messagePo.setAction(messageAction);
            messagePo.setCreateTime(LocalDateTime.now());
            messagePo.setChannelType(ChannelType.订单类);
            SpringContextUtils.getBean(MessageServiceApi.class).publish(messagePo);
        }
    }


}
