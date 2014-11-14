package com.changhong.common.thread;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

/**
 * User: Jack Wang
 * Date: 14-5-7
 * Time: 上午11:03
 */
public class ApplicationThreadPool {

    private static ThreadPoolTaskExecutor pool = null;

    static {
        pool = new ThreadPoolTaskExecutor();
        //线程池所使用的缓冲队列
        pool.setQueueCapacity(500);
        //线程池所使用的缓冲队列
        pool.setCorePoolSize(250);
        //线程池维护线程的最大数量
        pool.setMaxPoolSize(500);
        //线程池维护线程所允许的空闲时间
        pool.setKeepAliveSeconds(10000);
        pool.initialize();
    }

    public static void executeThread(Thread thread) {
        pool.execute(thread);
    }

}
