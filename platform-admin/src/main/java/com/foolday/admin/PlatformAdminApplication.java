package com.foolday.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication
@ComponentScan(value = {"com.foolday"})
public class PlatformAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatformAdminApplication.class, args);
    }

}
