package com.foolday.service.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.foolday.common.base.BaseServiceUtils;
import com.foolday.common.dto.FantPage;
import com.foolday.common.enums.ChannelType;
import com.foolday.common.enums.MessageAction;
import com.foolday.common.enums.OrderStatus;
import com.foolday.dao.order.OrderEntity;
import com.foolday.dao.order.OrderMapper;
import com.foolday.dao.user.UserEntity;
import com.foolday.dao.user.UserMapper;
import com.foolday.service.api.admin.OrderServiceApi;
import com.foolday.service.wechat.WxOrderService;
import com.foolday.serviceweb.dto.admin.OrderQueryVo;
import com.foolday.serviceweb.dto.admin.base.LoginUserHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class OrderService implements OrderServiceApi {
    @Resource
    private OrderMapper orderMapper;

    @Resource
    private UserMapper userMapper;

    /**
     * 后台管理员更新订单状态
     *
     * @param orderId
     * @param status
     */
    @Override
    public void updateOrderStatus(@NotNull String orderId, OrderStatus status) {
        OrderEntity orderEntity = BaseServiceUtils.checkOneById(orderMapper, orderId);
        orderEntity.setStatus(status);
        orderEntity.setUpdateTime(LocalDateTime.now());
        orderMapper.updateById(orderEntity);
    }

    @Override
    public FantPage<OrderEntity> page(OrderQueryVo queryVo) {
        Page<OrderEntity> page = new Page<>(queryVo.getCurrentPage(), queryVo.getPageSize());
        page.setDesc("create_time");
        QueryWrapper<OrderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .ge(queryVo.getOrderStartTime() != null, OrderEntity::getCreateTime, queryVo.getOrderStartTime())
                .le(queryVo.getOrderEndTime() != null, OrderEntity::getCreateTime, queryVo.getOrderEndTime())
                .like(StringUtils.isNotBlank(queryVo.getDescription()), OrderEntity::getOtherDiscntPrice, queryVo.getDescription());
        IPage<OrderEntity> selectPage = orderMapper.selectPage(page, queryWrapper);
        return FantPage.Builder.ofPage(selectPage);
    }

    /**
     * 后台逻辑删除
     *
     * @param orderId
     */
    @Override
    public void delete(String orderId) {
        OrderEntity orderEntity = BaseServiceUtils.checkOneById(orderMapper, orderId);
        orderEntity.setStatus(OrderStatus.删除);
        orderEntity.setUpdateTime(LocalDateTime.now());
        orderMapper.updateById(orderEntity);
        log.info("逻辑删除订单{}", orderEntity);
    }

    /**
     * 获取退单列表
     *
     * @return
     */
    @Override
    public List<OrderEntity> findCancelOrders() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setStatus(OrderStatus.申请退款);
        orderEntity.setShopId(LoginUserHolder.get().getShopId());
        return orderMapper.selectList(Wrappers.lambdaQuery(orderEntity))
                .stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(OrderEntity::getUpdateTime))
                .collect(Collectors.toList());
    }

    @Override
    public void auditOrder(String orderId, boolean success) {
        OrderEntity orderEntity = BaseServiceUtils.checkOneById(orderMapper, orderId);
        orderEntity.setStatus(success ? OrderStatus.同意退款 : OrderStatus.不同意退款);
        orderEntity.setUpdateTime(LocalDateTime.now());
        orderMapper.updateById(orderEntity);
        log.info("审核订单{}的退款情况{},开始异步通知客户", orderId, success ? OrderStatus.同意退款 : OrderStatus.不同意退款);
        //具体实现，service中异步处理
        UserEntity userEntity = BaseServiceUtils.checkOneById(userMapper, orderEntity.getUserId());
        WxOrderService.OrderMessageHandler.notifyShopMsgFormUser(userEntity.getOpenId(), orderEntity.getShopId(), orderId,
                "系统审核结果", "后台管理员审核你的订单结果为" + orderEntity.getStatus().name(), MessageAction.审核订单, ChannelType.订单类);
    }


}
