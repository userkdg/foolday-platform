package com.foolday.admin.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyCommandRunner implements CommandLineRunner {
    @Value("${spring.auto.openUrl}")
    private Boolean autoOpen;

    @Value("${spring.web.loginUrl}")
    private String loginUrl;

    @Value("${spring.web.blowserExecute}")
    private String blowserExecute;

    @Value("${server.port}")
    private String port;

    @Value("${server.servlet.context-path}")
    private String context;

    @Override
    public void run(String... args) {
        if(autoOpen){
            loginUrl = loginUrl.replace("port", port)
                                .replace("context", context.replace("/", ""));
            String cmd = blowserExecute + " " + loginUrl;
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec(cmd);
                log.info("启动浏览器成功");
            } catch (Exception e){
                e.printStackTrace();
                log.error("启动浏览器失败");
            }
        }
    }
}
