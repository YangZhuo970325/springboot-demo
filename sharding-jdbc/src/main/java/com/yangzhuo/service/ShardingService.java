package com.yangzhuo.service;

import com.yangzhuo.entity.Order;

public interface ShardingService {

    int insertOrder(Order order);

    Order queryOrder(int orderId);
}
