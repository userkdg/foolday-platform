package com.foolday.dao.system.auth;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@Deprecated
public interface SysAdminAuthMapper extends BaseMapper<SysAdminAuthEntity> {
    @Select("select url_id from t_sys_admin_auth where user_id = #{userId}")
    Set<String> findUrlIdByUserId(@Param("userId")String userId);

    @Select("select user_id from t_sys_admin_auth where url_id = #{urlId}")
    Set<String> findUserIdByUserId(@Param("urlId")String urlId);
}
