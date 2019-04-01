package com.foolday.wechat;

import com.foolday.service.api.wechat.TestServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@SpringBootApplication
@ComponentScan(value = {"com.foolday.wechat", "com.foolday.service", "com.foolday.service.api", "com.foolday.common"})
@Controller
public class PlatformWechatApplication {
    @Autowired
    private TestServiceApi testService;

    @GetMapping("/test")
    public void test() {
        testService.test();
    }

    public static void main(String[] args) {
        SpringApplication.run(PlatformWechatApplication.class, args);
    }

}
