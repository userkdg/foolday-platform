package com.foolday.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 可加@Repository（加了就把实例给spring容器管理） 或者不加 都可以
 */
@Repository
public interface UserMapper extends BaseMapper<UserEntity> {

    @Select("select * from foolday_platform.t_user where open_id=#{openId} order by create_time desc, update_time desc limit 1")
    UserEntity findOneByOpenId(@Param("openId") String openId);

    @Select("select * from foolday_platform.t_user where open_id=#{openId} and union_id = #{unionId}  order by create_time desc, update_time desc limit 1")
    UserEntity findOneByOpenIdAndUnionId(@Param("openId") String openId, @Param("unionId") String unionId);
}
