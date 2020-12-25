package com.yangzhuo.zookeeper.curator.lock.aspect;

import com.yangzhuo.zookeeper.curator.lock.annotation.ZookeeperDistributeLock;
import com.yangzhuo.zookeeper.curator.lock.service.ZookeeperDsLock;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ZookeeperDistributeLockAspcet {

    @Pointcut("@annotation(com.yangzhuo.zookeeper.curator.lock.annotation.ZookeeperDistributeLock)")
    public void zookeeperLock() {
    }

    @Autowired
    private ZookeeperDsLock dsLock;

    @Around("zookeeperLock()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        ZookeeperDistributeLock annotation = signature.getMethod().getAnnotation(ZookeeperDistributeLock.class);
        boolean lockResult = false;
        try {
            String lockKey = annotation.lockNode();
            dsLock.init(lockKey);
            lockResult = dsLock.lock();
            if (lockResult) {
                return pjp.proceed();
            }
        } catch (Exception e) {
            log.error("method:{},运行错误！", methodName, e);
        } finally {
            if (lockResult) {
                dsLock.unlock();
            }
        }
        return null;
    }

}