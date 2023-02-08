package com.yangzhuo.aop.jdk;

import java.lang.reflect.Proxy;

public class Client {

    public static void main(String[] args) {
        //创建目标对象
        IAccountService target = new AccountServiceImpl();
        //创建代理对象
        IAccountService proxy = (IAccountService) Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new AccountAdvice(target)
        );
        proxy.transfer();
    }

}
