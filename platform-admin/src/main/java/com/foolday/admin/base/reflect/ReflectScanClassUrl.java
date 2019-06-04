package com.foolday.admin.base.reflect;

import com.foolday.admin.base.aspectj.HttpUtils;
import com.foolday.common.enums.CommonStatus;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.HttpMethod;
import org.springframework.util.ReflectionUtils;
import reactor.util.function.Tuple5;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 扫描指定目录的url
 *
 * @author userkdg
 * @date 2019/5/24 23:25
 **/
@Slf4j
public final class ReflectScanClassUrl {

    /**
     * @param cls 类
     * @return
     */
    public static Set<Tuple5<HttpMethod, String, String, Boolean, CommonStatus>> findControllerUrlInfoByClass(Class<?> cls) {
        return Arrays.stream(ReflectionUtils.getAllDeclaredMethods(cls))
                .map(HttpUtils::methodOf)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    /**
     * 包路径
     *
     * @param basePackage 包路径
     * @throws IOException error
     */
    public static Set<Class<?>> findClazzByPackage(String basePackage) throws IOException {
        String pkgDirName = basePackage;
        if (basePackage.contains(".")) {
            String[] uri = basePackage.split("\\.", -1);
            pkgDirName = String.join(File.separator, uri);
        }

        Set<Class<?>> classes = Sets.newHashSet();
        log.info("处理file的包路径{}", pkgDirName);
        Enumeration resources = Thread.currentThread().getContextClassLoader().getResources(pkgDirName);

        while(resources.hasMoreElements()) {
            URL url = (URL)resources.nextElement();
            String protocol = url.getProtocol();
            if ("file".equalsIgnoreCase(protocol)) {
                String filePath = URLDecoder.decode(url.getFile(), StandardCharsets.UTF_8.name());
                findClassesByFile(basePackage, filePath, classes);
            }
        }

        String scanJarClassPath = "com/foolday/admin/controller/";
        log.info("处理jar的包路径{}", scanJarClassPath);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        try {
            Resource[] resourcesJar = resolver.getResources(scanJarClassPath + "*.*");
            log.info("获取资源数量{}", resourcesJar.length);
            Resource[] var7 = resourcesJar;
            int var8 = resourcesJar.length;

            for(int var9 = 0; var9 < var8; ++var9) {
                Resource resource = var7[var9];
                String fileStr = resource.getFilename();
                if (StringUtils.isNotBlank(fileStr)) {
                    String classesName = scanJarClassPath.replaceAll("/", "\\.").concat(fileStr.substring(0, fileStr.lastIndexOf(".class")));
                    Class<?> aClass = loadClass(classesName);
                    if (log.isInfoEnabled()) {
                        log.info("放置位置  [" + aClass + "]");
                    }

                    classes.add(aClass);
                }
            }
        } catch (Exception var14) {
            log.error(var14.getMessage());
            var14.printStackTrace();
        }

        return classes;
    }

    /**
     * 扫描包路径下的所有class文件
     *
     * @param pkgName 包名
     * @param pkgPath 包对应的绝对地址
     * @param classes 包路径集合
     */
    public static void findClassesByFile(String pkgName, String pkgPath, Set<Class<?>> classes) {
        File dir = new File(pkgPath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        // 过滤获取目录，or class文件
        File[] dirfiles = dir.listFiles(pathname -> pathname.isDirectory() || pathname.getName().endsWith("class"));
        if (dirfiles == null || dirfiles.length == 0) {
            return;
        }
        String className;
        Class clz;
        for (File f : dirfiles) {
            if (f.isDirectory()) {
                findClassesByFile(pkgName + "." + f.getName(), pkgPath + File.separator + f.getName(), classes);
                continue;
            }
            // 获取类名，干掉 ".class" 后缀
            className = f.getName();
            className = className.substring(0, className.length() - 6);

            // 加载类
            clz = loadClass(pkgName + "." + className);
            if (clz != null) {
                classes.add(clz);
            }
        }
    }

    public static void main(String[] args) {
        String s = "com/foolday/admin/controller/AdminBannerController.class".replaceAll("/", "\\.");
        Class<?> aClass = loadClass(s.substring(0, s.lastIndexOf(".class")));
        System.out.println(aClass);
    }

    public static Class<?> loadClass(String fullClzName) {
        try {
            return Thread.currentThread().getContextClassLoader().loadClass(fullClzName);
        } catch (ClassNotFoundException e) {
            log.error("load class error! clz: {}, e:{}", fullClzName, e);
        }
        return null;
    }


}
