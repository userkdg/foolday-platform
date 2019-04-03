package com.foolday.core.config;

import com.foolday.common.enums.ThreadPoolType;
import org.apache.commons.dbutils.AsyncQueryRunner;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.concurrent.ExecutorService;


/**
 * 针对common-dbutils 的工具包自动配置
 * 1.系统首次初始化
 * 2.多个web程序公用共享对象，单列模式
 */
@Configuration
@ConditionalOnBean(value = ThreadPoolConfiguration.class)
public class DbUtilsConfiguration {
    /**
     * 查询数据库的工厂类
     *
     * @param dataSource 获取application.yml的自动配置的数据源 实例
     * @return QueryRunner
     * @see org.springframework.boot.autoconfigure.jdbc.DataSourceConfiguration 基于本类的基础上，进行的注入dataSource
     * 用法通过注入方式进行获取jvm的单列
     */
    @Bean
    public static QueryRunner queryRunner(@Qualifier("dataSource") DataSource dataSource) {
        return new QueryRunner(dataSource);
    }

    /**
     * 基于线程池的异步
     *
     * @param executor
     * @param queryRunner
     * @return
     */
    @Bean
    public static AsyncQueryRunner asyncQueryRunner(@Qualifier(ThreadPoolType.AsyncQueryRunnerThreadPool) ExecutorService executor,
                                                    @Qualifier("queryRunner") QueryRunner queryRunner) {
        return new AsyncQueryRunner(executor, queryRunner);
    }


}
