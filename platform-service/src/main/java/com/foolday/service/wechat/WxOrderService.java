package com.foolday.service.wechat;

import com.foolday.common.base.BaseServiceUtils;
import com.foolday.common.enums.OrderType;
import com.foolday.common.exception.PlatformException;
import com.foolday.dao.coupon.CouponEntity;
import com.foolday.dao.goods.GoodsEntity;
import com.foolday.dao.goods.GoodsMapper;
import com.foolday.dao.order.OrderEntity;
import com.foolday.dao.order.OrderMapper;
import com.foolday.service.api.admin.CouponServiceApi;
import com.foolday.service.api.admin.OrderDetailServiceApi;
import com.foolday.service.api.wechat.WxOrderServiceApi;
import com.foolday.service.api.wechat.WxUserCouponServiceApi;
import com.foolday.serviceweb.dto.admin.base.LoginUserHolder;
import com.foolday.serviceweb.dto.wechat.order.OrderDetailVo;
import com.foolday.serviceweb.dto.wechat.order.WxOrderVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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
        String userId = orderVo.getUserId();
        // 判断订单类型
        if (StringUtils.isNotBlank(orderVo.getGroupbuyId())) {
            // 判断团购id是否存在
//            PlatformAssert.isTrue();
            orderEntity.setOrderType(OrderType.拼团订单);
            // 订单与拼团的关联 todo
        }
        List<OrderDetailVo> orderDetailsVo = orderVo.getOrderDetails();
        // 计算价格
        Float sumPrice = orderDetailsVo.stream().map(orderDetail -> {
            GoodsEntity goodsEntity = BaseServiceUtils.checkOneById(goodsMapper, orderDetail.getGoodsId());
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
}
