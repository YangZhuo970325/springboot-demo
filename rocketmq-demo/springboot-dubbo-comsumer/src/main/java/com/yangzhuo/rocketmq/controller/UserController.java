package com.yangzhuo.rocketmq.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yangzhuo.rocketmq.service.IUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Reference
    private IUserService userService;
    
    @RequestMapping("/sayHello")
    public String sayHello(String name) {
        return userService.sayHello(name);
    }
    
}
