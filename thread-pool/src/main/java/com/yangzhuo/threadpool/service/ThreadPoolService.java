package com.yangzhuo.threadpool.service;

public interface ThreadPoolService {

    /**
     * newCachedThreadPool 线程池
     */
    void newCachedThreadPoolDemo() throws InterruptedException;


    /**
     * newFixedThreadPool 线程池
     */
    void newFixedThreadPoolDemo();

    /**
     * newSingleThreadExecutor 线程池
     */
    void newSingleThreadExecutorDemo();

    /**
     * newScheduledThreadPool 线程池
     */
    void newScheduledThreadPoolDemo();

    /**
     * 自定义线程池，使用execute提交
     */
    void customThreadPoolExecuteDemo();

    /**
     * 自定义线程池，使用submit提交
     */
    void customThreadPoolSubmitDemo();
}
