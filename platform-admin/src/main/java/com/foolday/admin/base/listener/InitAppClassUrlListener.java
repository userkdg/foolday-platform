package com.foolday.admin.base.listener;

import com.foolday.admin.base.property.WebAuthUrlDataProperties;
import com.foolday.service.api.init.InitAuthUrl2DbApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;

/**
 * @author userkdg
 * @date 2019/5/25 14:37
 **/
@Slf4j
@Component
public class InitAppClassUrlListener implements CommandLineRunner {
    @Resource
    private InitAuthUrl2DbApi initAuthUrl2DbApi;

    @Resource
    private WebAuthUrlDataProperties webAuthUrlDataProperties;

    @Override
    public void run(String... args) {
        try {
            Date start = new Date();
            log.info("监听器开始时间为{}", start);
            initAuthUrl2DbApi.initAuthUrlFromClass(webAuthUrlDataProperties.getBaseSystemPath(), webAuthUrlDataProperties.getBasePackage());
            Date end = new Date();
            log.info("监听器结束时间为{}，耗时{}s", end, (end.getTime() - start.getTime()) / 1000L);
        } catch (IOException e) {
//            e.printStackTrace();
            log.error("初始化url2db失败{}", e.toString());
        }
    }
}
