package com.foolday.dao.test;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 可加@Repository（加了就把实例给spring容器管理） 或者不加 都可以
 */
@Repository
public interface TestMapper extends BaseMapper<TestEntity> {
    @Insert("insert into test(id,name,upper_case_name) values(#{id},#{name},#{upperCaseName})")
    int add(@Param("id") int id, @Param("name") String name, @Param("upperCaseName") String upperCaseName);
}
