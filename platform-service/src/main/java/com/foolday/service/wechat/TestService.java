package com.foolday.service.wechat;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.foolday.dao.test.TestEntity;
import com.foolday.dao.test.TestMapper;
import com.foolday.service.api.wechat.TestServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service
public class TestService implements TestServiceApi {
    private static final String mysql_config_name = "service-config.yml";

    @Autowired
    DataSource dataSource;

    @Autowired
    TestMapper testMapper;

    @Override
    public void test() {
        try {
            Connection connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        IPage<TestEntity> testEntityIPage = testMapper.selectPage(new Page<>(0, 1), null);
        List<TestEntity> records = testEntityIPage.getRecords();
    }
}
