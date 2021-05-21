package com.yangzhuo.rocketmq.order;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderStep {
    
    private Long orderId;
    
    private String desc;
    
    @Override
    public String toString() {
        return "OrderStep{" + 
                " orderId=" + orderId +
                ", desc='" + desc + '\'' +
                "}";
    }
    
    public static List<OrderStep> buildOrders() {
        List<OrderStep> orderStepList = new ArrayList<>();
        
        OrderStep orderStep = new OrderStep();
        orderStep.setOrderId(1039L);
        orderStep.setDesc("创建");
        orderStepList.add(orderStep);

        orderStep = new OrderStep();
        orderStep.setOrderId(1065L);
        orderStep.setDesc("创建");
        orderStepList.add(orderStep);

        orderStep = new OrderStep();
        orderStep.setOrderId(1039L);
        orderStep.setDesc("付款");
        orderStepList.add(orderStep);
        
        orderStep = new OrderStep();
        orderStep.setOrderId(7235L);
        orderStep.setDesc("创建");
        orderStepList.add(orderStep);

        orderStep = new OrderStep();
        orderStep.setOrderId(1065L);
        orderStep.setDesc("付款");
        orderStepList.add(orderStep);

        orderStep = new OrderStep();
        orderStep.setOrderId(7235L);
        orderStep.setDesc("付款");
        orderStepList.add(orderStep);

        orderStep = new OrderStep();
        orderStep.setOrderId(1065L);
        orderStep.setDesc("完成");
        orderStepList.add(orderStep);

        orderStep = new OrderStep();
        orderStep.setOrderId(1039L);
        orderStep.setDesc("推送");
        orderStepList.add(orderStep);

        orderStep = new OrderStep();
        orderStep.setOrderId(7235L);
        orderStep.setDesc("完成");
        orderStepList.add(orderStep);

        orderStep = new OrderStep();
        orderStep.setOrderId(1039L);
        orderStep.setDesc("完成");
        orderStepList.add(orderStep);
        
        return orderStepList;
    }
}
