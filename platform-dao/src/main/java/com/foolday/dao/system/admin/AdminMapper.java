package com.foolday.dao.system.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 可加@Repository（加了就把实例给spring容器管理） 或者不加 都可以
 */
@Repository
public interface AdminMapper extends BaseMapper<AdminEntity> {

    @Select("select count(1) from t_sys_admin where account = #{account} and 1 = 1")
    boolean existAccount(@Param("account") String account);
}
