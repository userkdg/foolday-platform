package com.foolday.admin.base.reflect;

import com.foolday.admin.base.aspectj.HttpUtils;
import com.foolday.common.enums.CommonStatus;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
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
            pkgDirName = basePackage.replaceAll("\\.", "/");
        }
        /*保存包路径下class的集合*/
        final Set<Class<?>> classes = Sets.newHashSet();
        Enumeration<URL> resources = ReflectScanClassUrl.class.getClassLoader().getResources(pkgDirName);
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            String protocol = url.getProtocol();
            if ("file".equalsIgnoreCase(protocol)) {
                String filePath = URLDecoder.decode(url.getFile(), StandardCharsets.UTF_8.name());// 获取包的物理路径
                findClassesByFile(basePackage, filePath, classes);
            }
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
                findClassesByFile(pkgName + "." + f.getName(), pkgPath + "/" + f.getName(), classes);
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

    public static Class<?> loadClass(String fullClzName) {
        try {
            return Thread.currentThread().getContextClassLoader().loadClass(fullClzName);
        } catch (ClassNotFoundException e) {
            log.error("load class error! clz: {}, e:{}", fullClzName, e);
        }
        return null;
    }


}
