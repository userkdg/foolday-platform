package com.foolday.service.common;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * web admin 中的yaml文件配置
 * platform:
 * # 图片定制
 * image:
 * # 支持的文件类型
 * enable-content-type:
 * - png
 * - jpeg
 * - jpg
 * - gif
 * # 存储在磁盘目录
 * store-path: D://home/image
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "platform.image")
public class ImageConfigProperty {

    private List<String> enableContentType = Lists.newArrayList();

    /*
    测试过不能直接注册为Path
    Caused by: org.springframework.core.convert.ConverterNotFoundException: No converter found capable of converting from type [java.lang.String] to type [java.nio.file.Path]
     */
    private String storePath;


}
