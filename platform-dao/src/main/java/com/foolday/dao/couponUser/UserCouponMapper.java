package com.foolday.dao.couponUser;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * 可加@Repository（加了就把实例给spring容器管理） 或者不加 都可以
 */
@Repository
public interface UserCouponMapper extends BaseMapper<UserCouponEntity> {

    @Update("update t_coupon_user set used = 1, status = 0 where user_id = #{userId} and coupon_id = #{couponId} ")
    int updateUsed(@Param("userId") String userId, @Param("couponId") String couponId);
}
