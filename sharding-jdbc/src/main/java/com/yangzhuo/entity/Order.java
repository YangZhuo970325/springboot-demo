package com.yangzhuo.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class Order {

    private int orderId;

    private int orderNum;

    private Date createTime;

    private int userId;

    private Double orderAmount;
}
