package com.foolday.admin.base;

public final class SystemConfig {
    private SystemConfig() {
    }

    public static final String url_split = "/";

    public static String systemBaseUrl = url_split.concat("system/").concat(url_split);

}
