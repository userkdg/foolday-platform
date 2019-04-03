package com.foolday.core.config;

import com.foolday.common.enums.ThreadPoolType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.concurrent.*;

import static com.foolday.common.util.DateUtils.formatStandard;

@Configuration
public class ThreadPoolConfiguration {

    /*
    用例
     */
//    @Resource(name = ThreadPoolType.CommonBlockThreadPool)
//    private ExecutorService executorService;

    @Bean(ThreadPoolType.AsyncQueryRunnerThreadPool)
    public static ExecutorService asyncQueryRunnerThreadPool() {
        int core = Runtime.getRuntime().availableProcessors() - 1;
        return new ThreadPoolExecutor(core,
                core,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                (r) -> new Thread(r, "asyncqueryrunner 异步交互数据库-" + Thread.currentThread().getName()),
                new RejectHandler());
    }

    @Bean(ThreadPoolType.CommonBlockThreadPool)
    public static ExecutorService commonBlockThreadPool() {
        int core = Runtime.getRuntime().availableProcessors() - 1;
        return new ThreadPoolExecutor(core,
                core,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                (r) -> new Thread(r, "异步阻塞线程池" + Thread.currentThread().getName()),
                new RejectHandler());
    }


    @Bean(ThreadPoolType.CommonNonBlockThreadPool)
    public static ExecutorService executor() {
        int core = Runtime.getRuntime().availableProcessors() - 1;
        return new ThreadPoolExecutor(core,
                core,
                0L,
                TimeUnit.MILLISECONDS,
                new SynchronousQueue<>(),
                (r) -> new Thread(r, "异步非阻塞线程池" + Thread.currentThread().getName()),
                new RejectHandler());
    }


    /**
     * 报错拒绝
     * 1.后期可以标记jvm 的拒绝线程进行缓存指定最大允许几次失败
     */
    private static class RejectHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            throw new RejectedExecutionException("线程池" + executor.toString() + "的线程被拒绝，当前时间为" + formatStandard(new Date()));
        }
    }
}
