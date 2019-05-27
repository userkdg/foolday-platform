package com.foolday.dao.system.auth;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SysAuthMapper extends BaseMapper<SysAuthEntity> {
    @Select("select a.* from t_sys_auth a " +
            "left join t_sys_role_auth b on a.id = b.auth_id " +
            "where b.role_id = #{roleId} and a.status = 1 ")
    Set<SysAuthEntity> findByRoleId(@Param("roleId") String roleId);
}
