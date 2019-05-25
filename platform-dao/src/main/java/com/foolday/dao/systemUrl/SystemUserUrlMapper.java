package com.foolday.dao.systemUrl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SystemUserUrlMapper extends BaseMapper<SystemUserUrlEntity> {
    @Select("select url_id from t_system_user_url where user_id = #{userId}")
    Set<String> findUrlIdByUserId(@Param("userId")String userId);

    @Select("select user_id from t_system_user_url where url_id = #{urlId}")
    Set<String> findUserIdByUserId(@Param("urlId")String urlId);
}
