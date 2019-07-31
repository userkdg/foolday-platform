package com.foolday.service.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.foolday.common.base.BaseServiceUtils;
import com.foolday.common.util.PlatformAssert;
import com.foolday.dao.goods.GoodsEntity;
import com.foolday.dao.goods.GoodsMapper;
import com.foolday.dao.order.OrderDetailEntity;
import com.foolday.dao.order.OrderDetailMapper;
import com.foolday.service.api.admin.OrderDetailServiceApi;
import com.foolday.serviceweb.dto.wechat.order.OrderDetailVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


/**
 * 订单详情
 */
@Service
@Slf4j
@Transactional
public class OrderDetailService implements OrderDetailServiceApi {

    @Resource
    private OrderDetailMapper orderDetailMapper;

    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public OrderDetailEntity add(OrderDetailVo orderDetailVo, @NotNull String orderId) {
        PlatformAssert.isTrue(StringUtils.isNotBlank(orderId), " 订单id为空，无法创建详情");
        String goodsId = orderDetailVo.getGoodsId();
        GoodsEntity goodsEntity = BaseServiceUtils.checkOneById(goodsMapper, goodsId);
        // 详情 前端数据
        OrderDetailEntity orderDetail = new OrderDetailEntity();
        orderDetail.setGoodsId(orderDetailVo.getGoodsId());
        orderDetail.setCnt(orderDetailVo.getCnt());
        orderDetail.setOrderId(orderId);
        // 根据前端标识
        orderDetail.setGoodsDesc(goodsEntity.getDescription());
        orderDetail.setPrice(goodsEntity.calcRealPriceByDiscntCondition());
        orderDetail.setAllPrice(goodsEntity.calcRealPriceByDiscntCondition() * orderDetailVo.getCnt());
        orderDetail.setGoodsImgId(goodsEntity.getImgId());
        orderDetail.setGoodsName(goodsEntity.getName());
        orderDetail.setCreateTime(LocalDateTime.now());
        int insert = orderDetailMapper.insert(orderDetail);
        log.info("创建订单{}的商品{}的详情{}：结果为{}", orderId, orderDetailVo.getGoodsId(), orderDetail.getId(), (insert == 1));
        return orderDetail;
    }

    /**
     * 根据订单获取详情
     *
     * @param orderId
     * @return
     */
    @Override
    public List<OrderDetailEntity> findByOrderId(@NotNull String orderId) {
        if (StringUtils.isBlank(orderId)) return Collections.emptyList();
        LambdaQueryWrapper<OrderDetailEntity> where = Wrappers.lambdaQuery(new OrderDetailEntity()).eq(OrderDetailEntity::getOrderId, orderId);
        List<OrderDetailEntity> orderDetailEntities = orderDetailMapper.selectList(where);
        return orderDetailEntities;
    }
}
