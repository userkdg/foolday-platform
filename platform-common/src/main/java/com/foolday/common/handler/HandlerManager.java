package com.foolday.common.handler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HandlerManager {

    private static ExecutorService single = Executors.newSingleThreadExecutor();

    /**
     * 利用一个线程执行
     *
     * @param handler
     */
    public static void executeSingle(IHandler handler) {
        if (handler != null) {
            /*
            shut down 后
             */
            synchronized (single) {
                if (single == null) {
                    single = Executors.newSingleThreadExecutor();
                }
            }
            /*
              开一条线程取异步处理事务
             */
            try {
                single.execute(handler::handler);
            } finally {
                single.shutdown();
            }

        }
    }

    /**
     * 利用默认线程执行
     *
     * @param handler
     */
    public static void asyncExecute(IHandler handler) {
        /**
         * 开默认条线程取异步处理事务
         */
        if (handler != null) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(handler::handler);
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
