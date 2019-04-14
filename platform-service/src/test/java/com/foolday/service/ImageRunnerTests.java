package com.foolday.service;

import com.foolday.service.api.base.Image2DiskServiceApi;
import com.foolday.service.common.ImageConfigProperty;
import com.foolday.serviceweb.dto.image.FileDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * service 层服务测试
 * 设计上是没有web层的配置文件，要是测试配置文件，要到web层的测试类，目前只允许web和service之间通过公共的javabean 或Java原生对象或者common包等基础包实体来进行数据传输
 */
@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"application.yml", "platform-service.yml"}, classes = PlatformServiceApplication.class)
public class ImageRunnerTests {
    @Resource
    private Image2DiskServiceApi image2DiskServiceApi;

    @Test
    public void category() {
        File file = new File("C:\\Users\\Administrator\\Pictures\\login1.jpg");
        Optional<String> upload = image2DiskServiceApi.upload(file);
        System.out.println(upload);
    }

    @Test
    public void upl() throws IOException {
        File file = new File("C:\\Users\\Administrator\\Pictures\\login1.jpg");
        FileDto build = FileDto.builder().name(file.getName())
                .inputStream(Files.newInputStream(file.toPath()))
                .originalFilename(file.getName())
                .size(file.length())
                .bytes(com.google.common.io.Files.toByteArray(file))
                .build();
        image2DiskServiceApi.uploadImages(Collections.unmodifiableList(new ArrayList<FileDto>(){{add(build);}}));
    }

    @Resource
    ImageConfigProperty configuration;

    @Test
    public void list() {
        List<String> enableContentType = configuration.getEnableContentType();
        System.out.println(enableContentType);


    }
}
