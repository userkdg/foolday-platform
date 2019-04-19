package com.foolday.cloud.scheduleJob;

import com.foolday.service.api.schedule.WxAccessTokenServiceApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

/**
 * 本启动类为定时调度，基于spring的配置 依赖到service模块
 */
@SpringBootApplication
@ComponentScan(value = {"com.foolday"})
@EnableScheduling
public class PlatformScheduleJobRunner extends AbstractScheduledJob {

    @Resource
    private WxAccessTokenServiceApi wxAccessTokenServiceApi;

    /**
     * 定期刷新access_token微信有效时间为2h
     */
    @Scheduled(cron = "*/1 * * * * *")
    public void refreshAccessToken() {
        wxAccessTokenServiceApi.refreshAccessToken();
    }

    public static void main(String[] args) {
        SpringApplication.run(PlatformScheduleJobRunner.class, args);
    }

}
