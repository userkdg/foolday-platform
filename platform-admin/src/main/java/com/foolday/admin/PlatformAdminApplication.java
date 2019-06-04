package com.foolday.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@Slf4j
@SpringBootApplication
@ComponentScan(value = {"com.foolday"})
public class PlatformAdminApplication {


    public static void main(String[] args) throws IOException {
        SpringApplication.run(PlatformAdminApplication.class, args);
//        String scanClassPath = "com/foolday/admin/controller/";
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        try {
//            //获取所有匹配的文件
//            Resource[] resources = resolver.getResources(scanClassPath + "*.*");
//            for (Resource resource : resources) {
//                //获得文件流，因为在jar文件中，不能直接通过文件资源路径拿到文件，但是可以在jar包中拿到文件流
//                InputStream stream = resource.getInputStream();
//                if (log.isInfoEnabled()) {
//                    log.info("读取的文件流  [" + stream + "]");
//                }
//                String fileStr = resource.getFilename();
//                if (StringUtils.isNotBlank(fileStr)) {
//                    String classes = scanClassPath.replaceAll("/", "\\.").concat(fileStr.substring(0, fileStr.lastIndexOf(".class")));
//                    Class<?> aClass = ReflectScanClassUrl.loadClass(classes);
//                    if (log.isInfoEnabled()) {
//                        log.info("放置位置  [" + aClass + "]");
//                    }
//                }
//            }
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            e.printStackTrace();
//        }
    }

}
