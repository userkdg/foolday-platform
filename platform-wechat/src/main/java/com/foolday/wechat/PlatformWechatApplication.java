package com.foolday.wechat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(value = {"com.foolday", "com.foolday.wechat", "com.foolday.service", "com.foolday.service.api", "com.foolday.common"})
public class PlatformWechatApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatformWechatApplication.class, args);
    }

}
