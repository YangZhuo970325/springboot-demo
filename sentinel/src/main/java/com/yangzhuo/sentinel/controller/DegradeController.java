package com.yangzhuo.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DegradeController {
    
    public DegradeController() {
        initDegradeRule();
    }
    
    // 定义限流资源和限流降级回调函数
    @SentinelResource(value = "Sentinel_Rule", blockHandler = "exceptionHandler")
    @GetMapping("/degrade")
    public String hello() {
        return "Degrade Hello Sentinel!";
    }

    // blockedHandler函数，原方法调用被限流/降级/系统保护的时候调用
    public String exceptionHandler(BlockException e) {
        e.printStackTrace();
        return "已降级，系统繁忙，请稍后！";
    }
    
    private void initDegradeRule() {
        // 1. 创建存放熔断降级规则的集合
        List<DegradeRule> rules = new ArrayList<>();
        
        // 2. 创建熔断降级规则
        DegradeRule rule = new DegradeRule();
        
        // 定义资源
        rule.setResource("Sentinel_Rule");
        
        // 阈值
        rule.setCount(0.0001);

        /**
         * 定义规则类型，RuleConstant.DEGRADE_GRADE_RT:熔断降级（秒级 RT）类型
         * 当资源的平均响应时间超过阈值（DegradeRule中的count,以ms为单位）之后，资源进入准降级状态
         * 接下来如果持续进入 5 个请求，他们的RT都持续超过这个阈值
         * 那么在接下来的时间窗口（DegradeRule中的timewindow,以s为单位）之内
         * 将会抛出DegradeException
         */
        rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        // 降级的时间，单位为s
        rule.setTimeWindow(10);
        rules.add(rule);

        DegradeRuleManager.loadRules(rules);
    }

}
