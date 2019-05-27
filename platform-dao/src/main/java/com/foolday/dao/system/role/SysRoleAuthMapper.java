package com.foolday.dao.system.role;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author userkdg
 * @date 2019/5/26 19:21
 **/
@Repository
public interface SysRoleAuthMapper extends BaseMapper<SysRoleAuthEntity> {
    @Delete(value = "delete from t_sys_role_auth where role_id = #{roleId} ")
    int deleteByRoleId(@Param("roleId") String roleId);

    @Delete(value = "delete from t_sys_role_auth where auth_id = #{authId} ")
    int deleteByAuthId(@Param("authId") String authId);

    int deleteByRoleIds(@Param("roleIds") String[] roleIds);
}
