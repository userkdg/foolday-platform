package com.foolday.service.wechat;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.foolday.common.base.BaseServiceUtils;
import com.foolday.common.enums.*;
import com.foolday.common.exception.PlatformException;
import com.foolday.common.util.KeyUtils;
import com.foolday.common.util.PlatformAssert;
import com.foolday.dao.comment.CommentEntity;
import com.foolday.dao.coupon.CouponEntity;
import com.foolday.dao.goods.GoodsEntity;
import com.foolday.dao.goods.GoodsMapper;
import com.foolday.dao.order.OrderDetailEntity;
import com.foolday.dao.order.OrderEntity;
import com.foolday.dao.order.OrderMapper;
import com.foolday.dao.shop.ShopEntity;
import com.foolday.dao.shop.ShopMapper;
import com.foolday.dao.specification.GoodsSpecEntity;
import com.foolday.dao.user.UserEntity;
import com.foolday.dao.user.UserMapper;
import com.foolday.service.api.admin.CouponServiceApi;
import com.foolday.service.api.admin.OrderDetailServiceApi;
import com.foolday.service.api.wechat.WxOrderServiceApi;
import com.foolday.service.api.wechat.WxUserCouponServiceApi;
import com.foolday.service.api.wechat.WxUserServiceApi;
import com.foolday.service.common.CommonMessageManager;
import com.foolday.serviceweb.dto.admin.comment.CommentVo;
import com.foolday.serviceweb.dto.wechat.order.EntInvoiceVo;
import com.foolday.serviceweb.dto.wechat.order.OrderDetailVo;
import com.foolday.serviceweb.dto.wechat.order.WxOrderVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class WxOrderService implements WxOrderServiceApi {
    @Resource
    private OrderMapper orderMapper;

    @Resource
    private WxUserServiceApi userServiceApi;

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

    @Resource(name = "stringRedisTemplate")
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private ShopMapper shopMapper;
    @Resource
    private UserMapper userMapper;

    /**
     * 计算商品的总价格和是否含有折扣商品
     *
     * @param orderDetailsVo
     * @param goodsMapper
     * @return
     */
    public static Tuple2<Float, AtomicBoolean> calcGoodsSumPriceAndExistDiscntGoods(List<OrderDetailVo> orderDetailsVo, GoodsMapper goodsMapper) {
        AtomicBoolean existDiscountGoods = new AtomicBoolean(false);
        Float sumPrice = orderDetailsVo.stream().map(orderDetail -> {
            GoodsEntity goodsEntity = BaseServiceUtils.checkOneById(goodsMapper, orderDetail.getGoodsId());
            if (Boolean.TRUE.equals(goodsEntity.getDiscntGoods()) &&
                    (goodsEntity.getDiscntPrice() != null && goodsEntity.getDiscntPrice() != 0.0F)) {
                existDiscountGoods.set(true);
            }
            List<String> goodsSpecIds = orderDetail.getGoodsSpecIds();
            // todo 增加规格计算判断价格
            Float appendGoodsPrice = 0.0F;
            if (goodsSpecIds.isEmpty()) {
                log.debug("本商品没有选择规格信息");
            } else {
                log.debug("本商品进行规格信息计算");
                appendGoodsPrice = goodsSpecIds.stream().map(goodsSpecId -> {
                    GoodsSpecEntity goodsSpecEntity = new GoodsSpecEntity();
                    goodsSpecEntity.setId(goodsSpecId);
                    GoodsSpecEntity specEntity = goodsSpecEntity.selectById();
                    return specEntity.getAdjustPrice() ? specEntity.getGoodsAppendPrice() : 0.0F;
                }).reduce((f1, f2) -> f1 + f2).orElse(0.0F);
            }
            Float price = goodsEntity.calcRealPriceByDiscntCondition();
            Integer cnt = orderDetail.getCnt();
            return price * cnt + appendGoodsPrice;
        }).reduce((f1, f2) -> f1 + f2).orElseThrow(() -> new PlatformException("商品总价计算异常"));
        return Tuples.of(sumPrice, existDiscountGoods);
    }

    /**
     * @param orderVo
     * @param shopId
     * @return
     */
    @Override
    public OrderEntity submitOrder(WxOrderVo orderVo, String userId, String userName, String shopId) {
        // 检查店铺信息有效性
        ShopEntity shopEntity = BaseServiceUtils.checkOneById(shopMapper, shopId, "店铺信息无效,无法下单");
        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(orderVo, orderEntity);
        orderEntity.setCreateTime(LocalDateTime.now());
        orderEntity.setUserId(userId);
        orderEntity.setShopId(shopId);
        orderEntity.setShopName(shopEntity.getName());
        orderEntity.setShopAddress(shopEntity.getAddr());
        orderEntity.setUserName(userName);
        final String orderNoOfDay = KeyUtils.generateOrderNoOfDay(redisTemplate);
        orderEntity.setOrderNo(orderNoOfDay);
        // 判断订单类型
        if (StringUtils.isNotBlank(orderVo.getGroupbuyId())) {
            // todo 判断团购id是否存在
//            PlatformAssert.isTrue();
            orderEntity.setOrderType(OrderType.拼团订单);
            orderEntity.setGroupbuyId(orderVo.getGroupbuyId());
            // 订单与拼团的关联 todo
        }
        List<OrderDetailVo> orderDetailsVo = orderVo.getOrderDetails();
        // 计算价格和判断是否存在折扣商品
        Tuple2<Float, AtomicBoolean> sumPriceAndExistDiscntGoods = calcGoodsSumPriceAndExistDiscntGoods(orderDetailsVo, goodsMapper);
        /*
        处理优惠
         */
        Float sumPrice = sumPriceAndExistDiscntGoods.getT1();
        AtomicBoolean existDiscntGoods = sumPriceAndExistDiscntGoods.getT2();
        float couponRealPrice;
        if (existDiscntGoods.get()) {
            couponRealPrice = 0F;
        } else {
            couponRealPrice = calcRealPriceByCouponAndUpdateCouponStatus(orderVo, userId, sumPrice, existDiscntGoods.get());
        }
        orderEntity.setCouponId(orderVo.getCouponId());
        orderEntity.setOtherCouponId(orderVo.getOtherCouponId());
        orderEntity.setAllPrice(couponRealPrice);
        int insert = orderMapper.insert(orderEntity);
        log.info("写入订单{}", (insert == 1));
        // 写入明细
        orderDetailsVo.forEach(orderDetailVo -> orderDetailServiceApi.add(orderDetailVo, orderEntity.getId()));
        // 异步消息发送 todo
        CommonMessageManager.OrderMsgHandler.notifyShopMsgFormUser(userId, shopId, orderEntity.getId(),
                "用户下单通知", "您已成功下单,点击可查看订单详情", MessageAction.下单, ChannelType.订单类);
        return orderEntity;
    }

    /**
     * 计算优惠价格 和更新用户的优惠券信息
     *
     * @param orderVo
     * @param userId
     * @param sourceSumPrice
     * @param b
     * @return
     */
    public float calcRealPriceByCouponAndUpdateCouponStatus(WxOrderVo orderVo, String userId, Float sourceSumPrice, boolean b) {
        float couponRealPrice = sourceSumPrice;
        // 计算优惠
        String couponId = orderVo.getCouponId();
        if (StringUtils.isNotBlank(couponId)) {
            CouponEntity couponEntity = couponServiceApi.get(couponId).orElseThrow(() -> new PlatformException("订单优惠券异常"));
            couponRealPrice = couponEntity.getTargetPriceBySourcePrice(couponEntity.getType(), couponRealPrice);
            userCouponServiceApi.updateUsedByUserIdAndCouponId(userId, couponId, true);
//            userCouponMapper.updateUsed(userId, couponId);
        }
        String otherCouponId = orderVo.getOtherCouponId();
        if (StringUtils.isNotBlank(otherCouponId)) {
            CouponEntity couponEntity = couponServiceApi.get(otherCouponId).orElseThrow(() -> new PlatformException("订单优惠券异常"));
            couponRealPrice = couponEntity.getTargetPriceBySourcePrice(couponEntity.getType(), couponRealPrice);
            userCouponServiceApi.updateUsedByUserIdAndCouponId(userId, otherCouponId, true);
//            userCouponMapper.updateUsed(userId, couponId);
        }
        return couponRealPrice;
    }

    /**
     * 支付成功 使用微信支付
     *
     * @param openId
     * @param outTradeNo    商户单号
     * @param transactionId 微信单号
     */
    @Override
    public void pay(String openId, String outTradeNo, String transactionId) {
//        UserEntity userEntities = userMapper.findOneByOpenId(openId);
//        String userId = userEntities.getId();
//        LambdaQueryWrapper<OrderEntity> eq = lqWrapper().eq(OrderEntity::getUserId, userId).eq(OrderEntity::getOrderNo, outTradeNo);
//        List<OrderEntity> orderEntities = orderMapper.selectList(eq);
    }

    @Override
    public List<OrderEntity> listByOpenId(String openId) {
        return null;
    }

    /**
     * 获取用户的订单记录
     * OrderStatus == null 为获取用户所有订单信息
     *
     * @param userId
     * @param orderStatus
     * @return
     */
    @Override
    public List<OrderEntity> listByUserId(String userId, OrderStatus orderStatus) {
        LambdaQueryWrapper<OrderEntity> queryWrapper = Wrappers.lambdaQuery(new OrderEntity()).eq(OrderEntity::getUserId, userId);
        if (Objects.isNull(orderStatus)) {
            return orderMapper.selectList(queryWrapper);
        }
        return orderMapper.selectList(queryWrapper.eq(OrderEntity::getStatus, orderStatus));
    }

    @Override
    public OrderEntity get(String orderId, String userId) {
        return BaseServiceUtils.checkOneById(orderMapper, orderId, "订单信息不存在");
    }

    @Override
    public boolean cancelOrder(String orderId, String userId) {

        return false;
    }

    /**
     * 更新订单状态，通知店铺的后台人员，处理订单
     *
     * @param orderId
     * @param openId
     * @return
     */
    @Override
    public boolean refund(String orderId, String openId) {
        UserEntity user = userServiceApi.findByOpenId(openId).orElseThrow(() -> new PlatformException("非法用户信息"));
        OrderEntity order = BaseServiceUtils.checkOneById(orderMapper, orderId);
        PlatformAssert.isTrue(StringUtils.equals(orderId, order.getUserId()), "非本人订单,结束退款操作");
        // 通知后台人员 处理退款单子,确认退款后进行退费
        CommonMessageManager.OrderMsgHandler.notifyShopMsgFormUser(openId, order.getShopId(), orderId,
                "用户【" + user.getName() + "】发起退款", "发起退款申请,请审批是否通过", MessageAction.申请退款, ChannelType.订单类);
        // 更新状态
        order.setStatus(OrderStatus.申请退款);
        order.setUpdateTime(LocalDateTime.now());
        return order.updateById();
    }

    /**
     * 更新订单状态
     *
     * @param orderId
     * @param userId
     * @param orderStatus
     * @return
     */
    @Override
    public boolean updateOrderStatusByIdAndUserId(String orderId, String userId, OrderStatus orderStatus) {
        OrderEntity entity = BaseServiceUtils.checkOneById(orderMapper, orderId, "订单信息不存在");
        entity.setUserId(userId);
        entity.setStatus(orderStatus);
        return entity.updateById();
    }

    /**
     * 加餐
     *
     * @param orderDetailvo
     * @param orderId
     * @return
     */
    @Override
    public OrderDetailEntity appendOrderDetail(OrderDetailVo orderDetailvo, String orderId) {
        OrderEntity entity = BaseServiceUtils.checkOneById(orderMapper, orderId, "订单信息不存在");
        PlatformAssert.isTrue(OrderStatus.canAppendGoodsStatus(entity.getStatus()), "订单状态异常，无法加餐");
        OrderDetailEntity orderDetailEntity = orderDetailServiceApi.add(orderDetailvo, orderId);
        return orderDetailEntity;
    }

    @Override
    public void newBill(String orderId, EntInvoiceVo invoiceVo) {
        log.warn("需要处理发票");
    }

    /**
     * 通过订单获取商品，对每个商品进行生成一条评论
     *
     * @param orderId
     * @param commentVo
     * @param userName
     * @param userId
     * @return
     */
    @Override
    public void addComment(String orderId, CommentVo commentVo, String userId, String shopId, String userName) {
        BaseServiceUtils.checkOneById(orderMapper, orderId, "订单信息不存在");
        String imgIdStr = null;
        if (commentVo.getImgIds() != null && !commentVo.getImgIds().isEmpty()) {
            imgIdStr = String.join(",", commentVo.getImgIds());
        }
        List<OrderDetailEntity> orderDetails = orderDetailServiceApi.findByOrderId(orderId);
        final String finalImgIdStr = imgIdStr;
        List<CommentEntity> commentEntities = orderDetails.stream().map(orderDetail -> {
            CommentEntity commentEntity = new CommentEntity();
            BeanUtils.copyProperties(commentVo, commentEntity);
            commentEntity.setOrderId(orderId);
            commentEntity.setUserId(userId);
            commentEntity.setCreateTime(LocalDateTime.now());
            commentEntity.setGoodsId(orderDetail.getGoodsId());
            commentEntity.setStatus(CommentStatus.有效);
            commentEntity.setAdminId("");
            commentEntity.setAdminName("");
            commentEntity.setImgIds(finalImgIdStr);
            commentEntity.setShopId(shopId);
            commentEntity.setUserName(userName);
            commentEntity.insert();
            return commentEntity;
        }).collect(Collectors.toList());
        log.info("生成了评论信息=>{}", commentEntities);
    }

}
