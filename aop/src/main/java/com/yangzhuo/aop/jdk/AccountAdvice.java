package com.yangzhuo.aop.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class AccountAdvice implements InvocationHandler {

    private IAccountService target;

    public AccountAdvice(IAccountService target) {
        this.target = target;
    }

    /**
     * 代理方法
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        return method.invoke(target, args);
    }

    /**
     * 前置增强
     */
    private void before() {
        System.out.println("对转账人身份进行验证.");
    }
}
