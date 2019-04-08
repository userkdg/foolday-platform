package com.foolday.common.util;

import java.util.UUID;

public class UuidUtils {
    private UuidUtils() {
        // nothing
    }

    /**
     * 无-
     *
     * @return
     */
    public static String uuid32() {
        return uuid36().replaceAll("-", "");
    }

    /**
     * 常规
     *
     * @return
     */
    public static String uuid36() {
        return UUID.randomUUID().toString();
    }
}
