package com.yangzhuo.sentinel.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphO;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.*;

@RestController
public class TestController {
    
    @GetMapping("/hello")
    public String hello() {
        // 1. 定义使用限流规则
        try (Entry entry = SphU.entry("Hello");) { //限流入口
            return "hello world!"; // 被保护的资源
        } catch (BlockException exception) {
            exception.printStackTrace();
            return "系统繁忙!";  //被限流或者被降级的操作
        }
    }


    @GetMapping("/hi")
    public boolean hi() {
        // 1. 定义使用限流规则
        if (SphO.entry("Hello")) {
            try {
                System.out.println("Hello Sentinel!"); // 被保护的资源
                return true;
            } finally {
                SphO.exit(); //限流的出口
            }
            
        } else {
            System.out.println("系统繁忙,请稍后！");
            return false;
        }
    }
    
    
    
    // 定义限流规则
    @PostConstruct
    private void initFlowRules() {
        // 1. 创建存放限流规则的集合
        List<FlowRule> rules = new ArrayList<>();
        // 2. 创建限流规则
        FlowRule rule = new FlowRule();
        // 2-1. 定义资源，表示sentinel会对哪个资源生效
        rule.setResource("Hello");
        // 2-2. 定义限流规则类型 , 这里定义使用QPS限流
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 2-3. 定义QPS每秒能通过的请求个数
        rule.setCount(1);
        // 3. 将限流规则存放到集合中
        rules.add(rule);
        // 4. 加载限流规则
        FlowRuleManager.loadRules(rules);
    }
    
}
