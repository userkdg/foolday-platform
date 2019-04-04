package com.foolday.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.foolday.dao.test.TestEntity;
import com.foolday.dao.test.TestMapper;
import com.foolday.service.api.TestServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class TestService implements TestServiceApi {
//    private static final String mysql_config_name = "platform-service.yml";

    @Autowired
    DataSource dataSource;

    @Autowired
    TestMapper testMapper;

    @Autowired
    RedisTemplate redisTemplate;


    @Override
    public void test() {

        IPage<TestEntity> testEntityIPage = testMapper.selectPage(new Page<>(0, 1), null);
        List<TestEntity> records = testEntityIPage.getRecords();
        System.out.println(records);
    }
}
