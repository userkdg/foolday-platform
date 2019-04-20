package com.foolday.service.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.foolday.common.base.BaseServiceUtils;
import com.foolday.common.enums.CommonStatus;
import com.foolday.common.util.PlatformAssert;
import com.foolday.dao.coupon.CouponEntity;
import com.foolday.dao.coupon.CouponMapper;
import com.foolday.service.api.admin.AdminCouponServiceApi;
import com.foolday.serviceweb.dto.coupon.CouponVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class AdminCouponService implements AdminCouponServiceApi {
    @Resource
    private CouponMapper couponMapper;

    /**
     * 获取列表
     *
     * @return
     */
    @Override
    public List<CouponEntity> list() {
        LambdaQueryWrapper<CouponEntity> queryWrapper = Wrappers.lambdaQuery(new CouponEntity())
                .orderByDesc(CouponEntity::getCreateTime);
        List<CouponEntity> couponEntities = couponMapper.selectList(queryWrapper);
        return couponEntities;
    }

    /**
     * 获取单
     *
     * @param couponId
     * @return
     */
    @Override
    public Optional<CouponEntity> get(String couponId) {
        return Optional.ofNullable(couponMapper.selectById(couponId));
    }


    /**
     * 新建优惠券
     *
     * @param couponVo
     * @return
     */
    @Override
    public CouponEntity add(CouponVo couponVo) {
        CouponEntity couponEntity = new CouponEntity();
        BeanUtils.copyProperties(couponVo, couponEntity);
        couponEntity.setCreateTime(LocalDateTime.now());
        couponMapper.insert(couponEntity);
        return couponEntity;
    }

    /**
     * @param couponIds
     * @return
     */
    @Override
    public boolean delete(String... couponIds) {
        List<CouponEntity> couponEntities = BaseServiceUtils.checkAllByIds(couponMapper, couponIds);
        long deleteSize = couponEntities.stream().peek(couponEntity -> {
            couponEntity.setUpdateTime(LocalDateTime.now());
            couponEntity.setStatus(CommonStatus.删除);
        }).map(couponEntity -> couponMapper.updateById(couponEntity)).count();
        log.info("删除优惠券id:{}条", Arrays.asList(couponIds));
        return deleteSize != 0;
    }

    /**
     * 更新状态
     *
     * @param couponId
     * @param status
     */
    @Override
    public void updateStatus(String couponId, CommonStatus status) {
        CouponEntity couponEntity = BaseServiceUtils.checkOneById(couponMapper, couponId);
        couponEntity.setStatus(status);
        couponEntity.setUpdateTime(LocalDateTime.now());
        int updateById = couponMapper.updateById(couponEntity);
        log.info("更新{}状态{}=>{}", couponId, status, (updateById == 1));
    }


    /**
     * 调整优惠券内容
     *
     * @param couponId
     * @param couponVo
     */
    @Override
    public void edit(String couponId, CouponVo couponVo) {
        PlatformAssert.isFalse(Objects.isNull(couponVo) || StringUtils.isBlank(couponId), "优惠券调整信息不可为空");
        CouponEntity couponEntity = BaseServiceUtils.checkOneById(couponMapper, couponId);
        BeanUtils.copyProperties(couponVo, couponEntity);
        couponEntity.setUpdateTime(LocalDateTime.now());
        couponMapper.updateById(couponEntity);
    }

}
