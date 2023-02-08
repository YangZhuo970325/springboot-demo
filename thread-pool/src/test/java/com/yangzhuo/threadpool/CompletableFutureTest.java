package com.yangzhuo.threadpool;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureTest {

    @Test
    public void test2() throws Exception {

        // 创建异步执行任务，有返回值
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread()+" start,time->"+System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            if(false){
                throw new RuntimeException("test");
            }else{
                System.out.println(Thread.currentThread()+" exit,time->"+System.currentTimeMillis());
                return 1.2;
            }
        });
        System.out.println("main thread start,time->"+System.currentTimeMillis());
        //等待子任务执行完成
        System.out.println("run result->" + cf.get());
        System.out.println("main thread exit,time->"+System.currentTimeMillis());
    }
}
