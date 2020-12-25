package com.yangzhuo.zookeeper.single.controller;

import com.yangzhuo.zookeeper.single.service.OrderIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SingleZkLockController {

    @Autowired
    private OrderIdGenerator orderIdGenerator;


    @RequestMapping("/generatorId")
    public void generatorId() {
        log.info("开始生成id");
        for(int i = 0; i < 10; i++) {
            new Thread(orderIdGenerator).start();
        }
    }

}
