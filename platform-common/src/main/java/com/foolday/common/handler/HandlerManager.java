package com.foolday.common.handler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HandlerManager {
    /**
     * 利用一个线程执行
     *
     * @param handler
     */
    public static void executeSingle(IHandler handler) {
        if (handler != null) {
            /**
             * 开一条线程取异步处理事务
             */
            ExecutorService single = Executors.newSingleThreadExecutor();
            try {
                CompletableFuture.runAsync(handler::handler, single);
            } finally {
                if (!single.isShutdown())
                    single.shutdown();
            }
        }
    }

    /**
     * 利用默认线程执行
     *
     * @param handler
     */
    public static void execute(IHandler handler) {
        /**
         * 开默认条线程取异步处理事务
         */
        if (handler != null)
            CompletableFuture.runAsync(handler::handler);
    }
}
