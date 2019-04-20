package com.foolday.service.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.foolday.common.base.BaseServiceUtils;
import com.foolday.common.dto.FantPage;
import com.foolday.common.enums.OrderStatus;
import com.foolday.dao.order.OrderEntity;
import com.foolday.dao.order.OrderMapper;
import com.foolday.service.api.admin.AdminOrderServiceApi;
import com.foolday.serviceweb.dto.admin.OrderQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Service
@Slf4j
@Transactional
public class AdminOrderService implements AdminOrderServiceApi {
    @Resource
    private OrderMapper orderMapper;

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


}
