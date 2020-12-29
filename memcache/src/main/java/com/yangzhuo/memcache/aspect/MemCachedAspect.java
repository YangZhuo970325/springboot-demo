package com.yangzhuo.memcache.aspect;

import com.alibaba.fastjson.JSON;
import com.yangzhuo.memcache.annotation.MemCached;
import com.yangzhuo.memcache.until.MemcachedUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @Author: yangzhuo
 * @Date: 2020/12/28 15:50
 */
@Component
@Aspect
@Slf4j
public class MemCachedAspect {

    @Autowired
    private MemcachedUtil memcachedUtil;

    @Pointcut("execution(* com.yangzhuo.memcache.service.*.*(..))")
    protected void memServicePointcut() {
    }

    /**
     * 写入或者读取缓存 仅针对有注解的且该包下的方法
     */
    @Around("(@annotation(memcached) && memServicePointcut())")
    public Object doMemcachedAround(ProceedingJoinPoint call, MemCached memcached) {
        String packageName = call.getSignature().getDeclaringTypeName();
        String methodName = call.getSignature().getName();
        log.info("执行方法: {} -> {}", packageName, methodName);
        //返回最终结果
        Object result = null;
        //校验conditions
        if (null != memcached) {
            String key = resolvingKey(call, memcached.prefix(), memcached.key());
            result = memcachedUtil.get(key);
            if (null == result) {
                // memcached中不存在
                try {
                    //执行aop拦截的方法
                    result = call.proceed();
                    //获取注解配置memcached过期时间单位秒
                    int expiration = memcached.expiration();
                    boolean cacheResult = memcachedUtil.set(key, result, new Date(expiration * 1000));
                    if (cacheResult){
                        log.info("【写入Memcached缓存】" + ":key={}" + ",value={}" + ",expiration={}", key,
                                JSON.toJSON(result), expiration);
                    } else {
                        log.info("【写入Memcached失败,请检查memcache服务器状态】" + ":key={}" + ",value={}" + ",expiration={}", key,
                                JSON.toJSON(result), expiration);
                    }
                } catch (Throwable e) {
                    log.error("执行方法失败: {} -> {}", packageName, methodName);
                    log.error("失败原因:{}", e.getMessage(), e);
                }
            } else {
                // memcached中存在 直接返回
                log.info("【读取Memcached缓存】" + ":key={}" + ",value={}", key, JSON.toJSON(result));
            }
        } else {
            try {
                result = call.proceed();
            } catch (Throwable e) {
                log.error("执行方法失败: {} -> {}", packageName, methodName);
                log.error("失败原因:{}", e.getMessage(), e);
            }
        }
        return result;
    }

    /**
     * 获取缓存的key key 定义在注解上，支持SPEL表达式
     */
    private String parseKey(String key, Method method, Object[] args) {

        //获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer u =
                new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = u.getParameterNames(method);
        //使用SPEL进行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        //SPEL上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        //把方法参数放入SPEL上下文中
        for (int i = 0; i < paraNameArr.length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
        }
        return parser.parseExpression(key).getValue(context, String.class);
    }

    /**
     * 获取被拦截方法对象
     *
     * MethodSignature.getMethod() 获取的是顶层接口或者父类的方法对象 而缓存的注解在实现类的方法上 所以应该使用反射获取当前对象的方法对象
     */
    public Method getMethod(ProceedingJoinPoint pjp) {
        //获取参数的类型
        Object[] args = pjp.getArgs();
        Class[] argTypes = new Class[pjp.getArgs().length];
        for (int i = 0; i < args.length; i++) {
            argTypes[i] = args[i].getClass();
        }
        Method method = null;
        try {
            method = pjp.getTarget().getClass().getMethod(pjp.getSignature().getName(), argTypes);
        } catch (NoSuchMethodException e) {
            log.error(e.getMessage());
        } catch (SecurityException e) {
            log.error(e.getMessage());
        }
        return method;
    }

    /**
     * 解析key
     */
    public String resolvingKey(ProceedingJoinPoint call, String prefix, String key) {

        //如果key为空直接将方法名称作为key
        String methodName = call.getSignature().getName();
        if (StringUtils.isEmpty(key)) {
            return prefix + methodName;
        }

        //判断key是否是spel表达式
        if (key.indexOf("#") != -1) {
            Method method = getMethod(call);
            String parsekey = parseKey(key, method, call.getArgs());
            if (StringUtils.isEmpty(parsekey)) {
                parsekey = methodName;
            }
            key = prefix + parsekey;
        } else {
            key = prefix + key;
        }
        return key;
    }

}