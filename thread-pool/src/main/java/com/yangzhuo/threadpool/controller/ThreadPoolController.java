package com.yangzhuo.threadpool.controller;

import com.yangzhuo.threadpool.service.ThreadPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ThreadPoolController {
    
    @Autowired
    private ThreadPoolService threadPoolService;
    
    @GetMapping("/newCachedThreadPool")
    public void cachedThreadPool() throws InterruptedException {
        threadPoolService.newCachedThreadPoolDemo();
    }

    @GetMapping("/newFixedThreadPool")
    public void newFixedThreadPool() {
        threadPoolService.newFixedThreadPoolDemo();
    }
    
    @GetMapping("/newSingleThreadExecutor")
    public void newSingleThreadExecutor() {
        threadPoolService.newSingleThreadExecutorDemo();
    }
    
    @GetMapping("/newScheduledThreadPool")
    public void newScheduledThreadPool() {
        threadPoolService.newScheduledThreadPoolDemo();
    }

    @GetMapping("/customThreadPoolExecute")
    public void customThreadPoolExecute() {
        threadPoolService.customThreadPoolExecuteDemo();
    }

    @GetMapping("/customThreadPoolSubmit")
    public void customThreadPoolSubmit() {
        threadPoolService.customThreadPoolSubmitDemo();
    }
}
