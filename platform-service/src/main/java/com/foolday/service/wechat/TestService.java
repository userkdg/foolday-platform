package com.foolday.service.wechat;

import com.foolday.service.api.wechat.TestServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class TestService implements TestServiceApi {
    private static final String mysql_config_name = "service-config.yml";

    @Autowired
    DataSource dataSource;
    @Override
    public void test() {
        try {
            Connection connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
