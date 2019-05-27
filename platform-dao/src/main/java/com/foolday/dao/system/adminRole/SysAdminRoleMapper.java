package com.foolday.dao.system.adminRole;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.foolday.dao.system.role.SysRoleEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SysAdminRoleMapper extends BaseMapper<SysAdminRoleEntity> {
    @Select("select role_id from t_sys_admin_role where user_id = #{userId}")
    Set<String> findUrlIdByUserId(@Param("userId") String userId);

    @Select("select user_id from t_sys_admin_role where role_id = #{roleId}")
    Set<String> findUserIdByUserId(@Param("roleId") String roleId);

    @Delete("delete from t_sys_admin_role where user_id = #{userId} ")
    int deleteByUserId(@Param("userId") String userId);

    @Select("select a.* from t_sys_role a, t_sys_admin_role b where a.id = b.role_id and b.user_id = #{userId} and a.status = 1 ")
    Set<SysRoleEntity> findRolesByUserId(@Param("userId") String userId);
}
