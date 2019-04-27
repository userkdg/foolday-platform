package com.foolday.cloud.scheduleJob;

import com.foolday.service.api.schedule.WxAccessTokenServiceApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
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
     * 每小时55分 刷新一次
     * 定期刷新access_token微信有效时间为2h
     */
    @Profile("prod")
    @Scheduled(cron = "0 55 * * * ?", initialDelay = -1L)
    public void refreshAccessToken() {
        wxAccessTokenServiceApi.refreshAccessToken();
    }

    /**
     * 定期刷新access_token微信有效时间为2h
     * cron = "${platform.weixin.accessToken.refresh.cron}",
     */
    @Profile(value = {"dev", "test"})
    @Scheduled(
            initialDelayString = "${platform.schedule.weixin.accessToken.refresh.initialDelay}",
            fixedRateString = "${platform.schedule.weixin.accessToken.refresh.fixedRate}"
    )
    public void devRefreshAccessToken() {
        wxAccessTokenServiceApi.refreshAccessToken();
    }

    public static void main(String[] args) {
        SpringApplication.run(PlatformScheduleJobRunner.class, args);
    }

}
