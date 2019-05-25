package com.foolday.admin;

import com.foolday.serviceweb.dto.init.InitAuthUrl2DbApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlatformAdminApplication.class, properties = "application.yml")
public class PlatformAdminApplicationTests {

    @Resource
    private InitAuthUrl2DbApi initAuthUrl2DbApi;

    @Test
    public void initSystemUrl() throws IOException {
        String basePackage = "com.foolday.admin.controller";
        String baseSystemUrl = "/system";
        initAuthUrl2DbApi.initAuthUrlFromClass(baseSystemUrl, basePackage);

    }

}
