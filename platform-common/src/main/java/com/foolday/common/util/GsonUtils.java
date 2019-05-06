package com.foolday.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

/**
 * 复用gson对象,内部维护gson
 */
@Slf4j
@UtilityClass
public class GsonUtils {
    /**
     * 线程安全的gsonBuilder类
     */
    @ThreadSafe
    private static class Holder {
        private static GsonBuilder instance = new GsonBuilder();
    }

    private static GsonBuilder getGsonBuilderInstance() {
        return Holder.instance;
    }

    public static Gson create() {
        return getGsonBuilderInstance().create();
    }

    public static <T> T fromJson(@Nullable String jsonStr, Class<T> cls) {
        return create().fromJson(jsonStr, cls);
    }

}
