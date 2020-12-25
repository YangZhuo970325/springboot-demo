package com.yangzhuo.zookeeper.curator.lock.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ZookeeperDistributeLock {

    String lockNode() default "/lock"; // 节点名称

}