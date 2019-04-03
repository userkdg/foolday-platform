package com.foolday.core.config;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * 自动配置分页拦截器
 * 用法
 * 方式一 、传参区分模式【官方推荐】
 * <code>
 * public interface UserMapper{//可以继承或者不继承BaseMapper
 * //查询 : 根据state状态查询用户列表，分页显示
 * //@param page  翻页对象，可以作为 xml 参数直接使用，传递参数 Page 即自动分页
 * //@param state 状态
 * //@return List<User> selectUserList(Pagination page,Integer state);
 * *}
 * </code>
 * <p>
 * UserServiceImpl.java 调用翻页方法，需要 page.setRecords 回传给页面
 * public Page<User> selectUserPage(Page<User> page, Integer state) {
 * // 不进行 count sql 优化，解决 MP 无法自动优化 SQL 问题
 * // page.setOptimizeCountSql(false);
 * // 不查询总记录数
 * // page.setSearchCount(false);
 * // 注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
 * return page.setRecords(userMapper.selectUserList(page, state));
 * }
 * UserMapper.xml 等同于编写一个普通 list 查询，mybatis-plus 自动替你分页
 * <select id="selectUserList" resultType="User">
 * SELECT * FROM user WHERE state=#{state}
 * </select>
 * <p>
 * 方式二、ThreadLocal 模式【容易出错，不推荐】
 * PageHelper 使用方式如下：
 * // 开启分页
 * PageHelper.startPage(1, 2);
 * List<User> data = userService.findAll(params);
 * // 获取总条数
 * int total = PageHelper.getTotal();
 * // 获取总条数，并释放资源
 * int total = PageHelper.freeTotal();
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.foolday.dao*")
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * SQL执行效率插件
     * 参数：maxTime SQL 执行最大时长，超过自动停止运行，有助于发现问题。
     * 参数：format SQL SQL是否格式化，默认false。
     */
    @Bean
    @Profile({"dev", "test"})// 设置 dev test 环境开启
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        performanceInterceptor.setFormat(true);
        return performanceInterceptor;
    }

    /**
     * 1.乐观锁，并发使用
     * <p>
     * 2.注解实体字段 @Version 必须要！
     * public class User {
     *
     * @return
     * @Version private Integer version;
     * <p>
     * ...
     * }
     * 特别说明： 仅支持int,Integer,long,Long,Date,Timestamp
     * <p>
     * int id = 100;
     * int version = 2;
     * <p>
     * User u = new User();
     * u.setId(id);
     * u.setVersion(version);
     * u.setXXX(xxx);
     * <p>
     * if(userService.updateById(u)){
     * System.out.println("Update successfully");
     * }else{
     * System.out.println("Update failed due to modified by others");
     * }
     * sql 原理：
     * update tbl_user set name='update',version=3 where id=100 and version=2;
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }
}
