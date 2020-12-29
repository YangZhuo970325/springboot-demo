package com.yangzhuo.memcache.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MemCached {

    // key的前缀 default=STATIC_
    String prefix() default "STATIC_";

    // key
    String key() default "";

    // 缓存有效期默认一小时 单位s
    int expiration() default 60 * 60;

}