package com.yangzhuo.redis.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class Order {
    
    private Long id;
    
    private Date createTime;
    
    private String userName;
}
