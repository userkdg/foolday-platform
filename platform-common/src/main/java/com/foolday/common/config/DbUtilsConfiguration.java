package com.foolday.common.config;

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
     * @param dataSource
     * @return
     */
    @Bean
    public static QueryRunner queryRunner(@Qualifier("dataSource") DataSource dataSource) {
        return new QueryRunner(dataSource);
    }

    @Bean
    public static AsyncQueryRunner asyncQueryRunner(@Qualifier(ThreadPoolType.AsyncQueryRunnerThreadPool) ExecutorService executor,
                                                    @Qualifier("queryRunner") QueryRunner queryRunner) {
        return new AsyncQueryRunner(executor, queryRunner);
    }


}
