package com.foolday.common.dto;

import lombok.Data;

@Data
public class ClientInfo {
    public static final String OS_WINDOWS = "windows";
    public static final String OS_MAC = "mac";
    public static final String OS_UNIX = "unix";
    public static final String OS_LINUX = "linux";
    public static final String OS_IPHONE = "iphone";
    public static final String OS_ANDROID = "android";
    public static final String OS_UNKNOWN = "unknown";
    public static final String BROWSER_IE = "ie";
    public static final String BROWSER_FIREFOX = "firefox";
    public static final String BROWSER_EDGE = "edge";
    public static final String BROWSER_OPERA = "opera";
    public static final String BROWSER_SAFARI = "safari";
    public static final String BROWSER_CHROME = "chrome";
    public static final String BROWSER_NETSCAPE = "netscape";
    public static final String BROWSER_UNKNOWN = "unknown";
    public static final String APP_BROWSER = "browser";
    public static final String APP_WECHAT = "wechat";
    public static final String APP_DINGTALK = "dingtalk";
    public static final String APP_UNKNOWN = "unknown";
    private String os;
    private String browser;
    private String app;
    private String ip;

    public ClientInfo() {
    }

}
