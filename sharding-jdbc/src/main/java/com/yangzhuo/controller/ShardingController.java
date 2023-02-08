package com.yangzhuo.controller;

import com.yangzhuo.entity.Order;
import com.yangzhuo.service.ShardingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ShardingController {

    @Autowired
    private ShardingService shardingService;

    @PostMapping("/order")
    public int insertOrder(@RequestBody Order order) {
        return shardingService.insertOrder(order);
    }

    @GetMapping("/order")
    public Order queryOrder(@RequestParam("orderId") int orderId){
        return shardingService.queryOrder(orderId);
    }
}
