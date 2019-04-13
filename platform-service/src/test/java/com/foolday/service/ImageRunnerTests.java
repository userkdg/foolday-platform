package com.foolday.service;

import com.foolday.service.api.base.ImageServiceApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.util.Optional;

/**
 * service 层服务测试
 * 设计上是没有web层的配置文件，要是测试配置文件，要到web层的测试类，目前只允许web和service之间通过公共的javabean 或Java原生对象或者common包等基础包实体来进行数据传输
 */
@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"application.yml"}, classes = PlatformServiceApplication.class)
public class ImageRunnerTests {
    @Resource
    private ImageServiceApi imageServiceApi;

    @Test
    public void category() {
        File file = new File("C:\\Users\\Administrator\\Pictures\\login1.jpg");
        Optional<String> upload = imageServiceApi.upload(file);
        System.out.println(upload);
    }

    @Test
    public void list() {

    }
}
