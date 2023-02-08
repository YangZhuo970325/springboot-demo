package com.yangzhuo.aop.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class AccountAdvice implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        return methodProxy.invokeSuper(o, objects);
    }

    /**
     * 前置增强
     */
    private void before() {
        System.out.println("对转账人身份进行验证.");
    }
}
