package com.yangzhuo.threadpool.service.impl;

import com.yangzhuo.threadpool.service.ThreadPoolService;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.*;
import java.util.concurrent.*;

@Service
public class ThreadPoolServiceImpl implements ThreadPoolService {


    /**
     * newCachedThreadPool:如果线程池中的线程有空闲,就直接复用空闲线程,如果没有空闲的线程则创建新的线程
     * 优点：提高线程的复用率
     * 缺点：无法控制线程池中线程数量,如果线程创建过多，会导致oom
     **/
    @Override
    public void newCachedThreadPoolDemo() {
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            final int index = i;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " " + index);
                }
            });

        }
    }

    /**
     * newFixedThreadPool线程数是可以控制的，但是等待（阻塞）队列会无线增长，如果等待的任务太多，会导致oom
     */
    @Override
    public void newFixedThreadPoolDemo() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 5; i++) {
            final int index = i;
            executor.execute(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " " + index);
            });
        }
        executor.shutdown();
    }

    /**
     * newSingleThreadExecutor：线程池中只有一个线程
     * 如果有大量请求，则进入等待队列，如果等待任务较多，会导致等待队列无线增长出现oom
     */
    @Override
    public void newSingleThreadExecutorDemo() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 5; i++) {
            final int index = i;
            executor.execute(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " " + index);
            });
        }
    }

    @Override
    public void newScheduledThreadPoolDemo() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        executor.scheduleAtFixedRate(() -> {
            long start = System.currentTimeMillis();
            System.out.println("scheduleAtFixedRate 开始执行时间:" + DateFormat.getTimeInstance().format(new Date()));
            try {
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long end = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName());
            System.out.println("scheduleAtFixedRate 执行花费时间=" + (end - start) / 1000 + "m");
            System.out.println("scheduleAtFixedRate 执行完成时间：" + DateFormat.getTimeInstance().format(new Date()));
            System.out.println("======================================");
        }, 1, 5, TimeUnit.SECONDS);

        executor.scheduleWithFixedDelay(() -> {
            long start = System.currentTimeMillis();
            System.out.println("scheduleWithFixedRate 开始执行时间:" + DateFormat.getTimeInstance().format(new Date()));
            try {
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long end = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName());
            System.out.println("scheduleWithFixedRate 执行花费时间=" + (end - start) / 1000 + "m");
            System.out.println("scheduleWithFixedRate 执行完成时间：" + DateFormat.getTimeInstance().format(new Date()));
            System.out.println("======================================");
        }, 1, 5, TimeUnit.SECONDS);
    }

    @Override
    public void customThreadPoolExecuteDemo() {
        ThreadPoolExecutor threadPoolExecutor;

        //创建工作队列，用于存放提交的等待执行任务
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(16);
        //创建线程池
        threadPoolExecutor = new ThreadPoolExecutor(4,
                8, 1,
                TimeUnit.MINUTES, workQueue, new CustomRejectedExecutionHandler());

        for (int i = 0; i < 50; i++) {
            final int index = i;
            threadPoolExecutor.execute(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " " + index + new Date());
            });
        }
    }

    @Override
    public void customThreadPoolSubmitDemo() {
        ThreadPoolExecutor threadPoolExecutor;

        //创建工作队列，用于存放提交的等待执行任务
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(4);
        //创建线程池
        threadPoolExecutor = new ThreadPoolExecutor(1,
                2, 1,
                TimeUnit.MINUTES, workQueue, new CustomRejectedExecutionHandler());

        int result = 0;
        try {

            List<FutureTask<Integer>> futureList = new CopyOnWriteArrayList<>();
            int i = 0;
            for (int j = 0; i < 50 && j < 6; j++) {
                final int index = i;
                FutureTask<Integer> futureTask = new FutureTask<>(() -> index + 1000);
                threadPoolExecutor.submit(futureTask);
                System.out.println(i + " -------- 已经提交 -------" + new Date());
                futureList.add(futureTask);
                i++;
            }

            while (futureList.size() > 0) {
                Iterator<FutureTask<Integer>> iterator = futureList.iterator();
                while (iterator.hasNext()) {
                    Future<Integer> future = iterator.next();
                    if (future.isDone() && !future.isCancelled()) {
                        try {
                            result = result + future.get();
                            futureList.remove(future);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                    if (i < 50) {
                        final int index = i;
                        FutureTask<Integer> futureTask = new FutureTask<>(() -> index + 1000);
                        threadPoolExecutor.submit(futureTask);
                        System.out.println(i + " -------- 已经提交 -------" + new Date());
                        futureList.add(futureTask);
                        i++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPoolExecutor.shutdown();
        }

        System.out.println(result);
    }

    /**
     * 自定义拒绝策略
     */
    public static class CustomRejectedExecutionHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                // 核心改造点，由blockingqueue的offer改成put阻塞方法  
                executor.getQueue().put(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
