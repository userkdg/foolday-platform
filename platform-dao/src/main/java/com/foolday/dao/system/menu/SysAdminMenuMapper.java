package com.foolday.dao.system.menu;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author userkdg
 * @date 2019/5/26 19:21
 **/
@Repository
public interface SysAdminMenuMapper extends BaseMapper<SysAdminMenuEntity> {

    @Delete("delete from t_sys_admin_menu where user_id = #{userId} ")
    int deleteByUserId(@Param("userId") String userId);

    @Select("select a.* from t_sys_menu a inner join t_sys_admin_menu b where a.id = b.menu_id and b.user_id =#{userId}  and a.status = 1")
    Set<SysMenuEntity> findMenusByUserId(@Param("userId") String userId);
}
