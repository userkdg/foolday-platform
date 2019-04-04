package com.foolday.cloud.scheduleJob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

/**
 * 本启动类为定时调度，基于spring的配置 依赖到service模块
 */
@SpringBootApplication
@ComponentScan(value = {"com.foolday"})
@EnableScheduling
public class PlatformScheduleJobRunner extends AbstractScheduledJob{

    /**
     * test
     */
    @Scheduled(fixedRate = 10L, initialDelay = 1L)
    public void testSchedul() {
        logger.debug("cost {}", LocalDateTime.now());
    }

    public static void main(String[] args) {
        SpringApplication.run(PlatformScheduleJobRunner.class, args);
    }

}
