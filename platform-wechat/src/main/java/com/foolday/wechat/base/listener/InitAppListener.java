package com.foolday.wechat.base.listener;

import com.foolday.wechat.base.session.WxUserSessionApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 启动完成后
 * @author userkdg
 * @date 2019/5/25 14:37
 **/
@Slf4j
@Component
public class InitAppListener implements CommandLineRunner {
    @Resource
    private WxUserSessionApi wxUserSessionApi;

    @Override
    public void run(String... args) {
        Date start = new Date();
        log.info("监听器开始时间为{}", start);
        wxUserSessionApi.initTestSession();
        Date end = new Date();
        log.info("监听器结束时间为{}，耗时{}s", end, (end.getTime() - start.getTime()) / 1000L);
    }
}
