package com.foolday.service.wechat;

import com.foolday.service.api.wechat.TestServiceApi;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class TestService implements TestServiceApi {
    private static final String mysql_config_name = "service-config.yml";


    /**
     * service-config.yml
     *
     * datasource:
     *     type: com.zaxxer.hikari.HikariDataSource
     *     url: jdbc:mysql://localhost:3306/foolday_platform?useUnicode=true&characterEncoding=UTF-8&useSSL=false
     *     username: root
     *     password: 123456
     *     driver-class-name: com.mysql.jdbc.Driver
     *     hikari:
     *       data-source-properties:
     *         cachePrepStmts: true
     *         prepStmtCacheSize: 10
     *         prepStmtCacheSqlLimit: 16
     *         useServerPrepStmts: true
     *         maximumPoolSize: 5
     *
     */
    @Test
    @Override
    public void test() {
        Yaml yaml = new Yaml();
        InputStream stream = TestService.class.getClassLoader().getResourceAsStream("service-config.yml");
        Map load = (Map)yaml.load(stream);
        Object mysql = load.get(mysql_config_name.substring(0,mysql_config_name.indexOf("-")));
        if (mysql instanceof List){
            List list = (List)mysql;
            HikariDataSource hikariDataSource = new HikariDataSource();
            for (Object o : list) {
                System.out.println(o);
            }
        }else {
            new NoSuchElementException("xxx");
        }

    }
}
