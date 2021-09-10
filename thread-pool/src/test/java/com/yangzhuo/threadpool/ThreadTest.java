package com.yangzhuo.threadpool;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

public class ThreadTest {
    
    @Test
    public void submitSingleTask() throws ExecutionException, InterruptedException {
        
        
        ThreadPoolExecutor threadPoolExecutor = null;

        //创建工作队列，用于存放提交的等待执行任务
        BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>();

        //创建线程池
                threadPoolExecutor = new ThreadPoolExecutor(4,
                8,
                1,
                TimeUnit.MINUTES,
                workQueue,
                new CustomRejectedExecutionHandler());

        List<FutureTask<Person>> futureList = new ArrayList<>();
        
        System.out.println("提交任务之前 "+getStringDate());
        for (int i = 0; i < 5; i++) {

            JeDocPostThread jeDocPostThread = new JeDocPostThread();

            FutureTask<Person> futureTask = new FutureTask<Person>(jeDocPostThread);

            threadPoolExecutor.submit(futureTask);
            futureList.add(futureTask);
        }
        
        System.out.println("提交任务之后，获取结果之前 "+getStringDate());
        
        while (futureList.size() > 0) {
            Iterator<FutureTask<Person>> iterable = futureList.iterator();
            
            while (iterable.hasNext()) {
                Future<Person> future = iterable.next();
                if (future.isDone() && !future.isCancelled()) {
                    System.out.println("获取返回值: "+ future.get().getName() + getStringDate());
                    iterable.remove();
                }
            }
            
        }
        
        System.out.println("获取到结果之后 "+getStringDate());
        
    }

    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
    
    
    @Test
    public void test() {
        Map<String, String> hashMap = new HashMap();
        
        hashMap.put("name", "yz");
        hashMap.put("sex", "male");

        System.out.println(123);
    }

    /**
     * 自定义拒绝策略
     */
    private class CustomRejectedExecutionHandler implements RejectedExecutionHandler {

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
