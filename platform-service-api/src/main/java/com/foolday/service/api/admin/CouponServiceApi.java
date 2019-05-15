package com.foolday.service.api.admin;

import com.foolday.common.base.BaseServiceApi;
import com.foolday.common.enums.CommonStatus;
import com.foolday.dao.coupon.CouponEntity;
import com.foolday.serviceweb.dto.coupon.CouponVo;

import java.util.List;
import java.util.Optional;

public interface CouponServiceApi extends BaseServiceApi<CouponEntity> {

    List<CouponEntity> list();

    Optional<CouponEntity> get(String couponId);

    /**
     * 新增优惠券返回实体类
     *
     * @param couponVo
     * @return
     */
    CouponEntity add(CouponVo couponVo);

    boolean delete(String... couponId);

    /**
     * 针对优惠券来说
     *
     * @param couponId
     * @param status
     */
    void updateStatus(String couponId, CommonStatus status);

    void edit(String couponId, CouponVo couponVo);


    List<CouponEntity> findValidByShopId(String shopId);

    List<CouponEntity> findValidByUserId(String userId);
}
