package com.foolday.cloud.serviceweb.dto.admin.base;

/**
 * 本地内存存储用户信息
 */
public abstract class LoginUserHolder {
    private static final ThreadLocal<LoginUser> holder = new ThreadLocal<>();

    public LoginUserHolder() {
    }

    public static void clear() {
        holder.remove();
    }

    public static void set(LoginUser user) {
        holder.set(user);
    }

    public static LoginUser get() {
        return holder.get();
    }
}
