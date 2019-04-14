package com.foolday.common.enums;

public final class ThreadPoolType {
    private ThreadPoolType() {
    }

    /**
     * 定义初始化的线程时类型
     */
    public static final String CommonBlockThreadPool = "commonBlockThreadPool";
    public static final String CommonNonBlockThreadPool = "commonNonBlockThreadPool";
    public static final String SingleThreadPool = "singleThreadPool";
    public static final String AsyncQueryRunnerThreadPool = "asyncQueryRunnerThreadPool";

}
